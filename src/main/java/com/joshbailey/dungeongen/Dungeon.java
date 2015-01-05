package com.joshbailey.dungeongen;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.joshbailey.dungeongen.DungeonSpace.DungeonSpaceType;

public class Dungeon {

	private static Random rand = new Random();
	
	private Collection<Room> rooms;
    private Space[][] spaces;
    private TwoDimensionalCoordinate upperRightCorner;
    
    public Dungeon(TwoDimensionalCoordinate upperRightCorner,
    			   int minRoomSizeX,
    			   int maxRoomSizeX, 
    			   int minRoomSizeY,
    			   int maxRoomSizeY,
    			   int allowedConsecutiveFailedPlacements){
    	
    	if (upperRightCorner == null)
		    throw new IllegalArgumentException("Unable to create Dungeon with null upperRightCorner");
    	if(minRoomSizeX < 1)
    		throw new IllegalArgumentException("Unable to create Dungeon with minRoomSizeX < 1");
    	if(maxRoomSizeX < 1)
    		throw new IllegalArgumentException("Unable to create Dungeon with minRoomSizeX < 1");
		if(minRoomSizeY < 1)
    		throw new IllegalArgumentException("Unable to create Dungeon with minRoomSizeY < 1");
    	if(maxRoomSizeY < 1)
    		throw new IllegalArgumentException("Unable to create Dungeon with maxRoomSizeY < 1");
    	if(allowedConsecutiveFailedPlacements < 0)
    		throw new IllegalArgumentException("Unable to create Dungeon with allowedConsecutiveFailedPlacements < 0");
    	
    	this.upperRightCorner = upperRightCorner;
		rooms = new LinkedList<Room>();
		spaces = Space.manufacture2dSpaceArray(0,0,upperRightCorner.getX(),upperRightCorner.getY(),this);
		
		//Mark the outer perimeter of the dungeon as the edge
		for(int x = 0 ; x < this.upperRightCorner.getX() ; x++){ 
			spaces[x][this.upperRightCorner.getY()-1] = new DungeonSpace(spaces[x][this.upperRightCorner.getY()-1],DungeonSpaceType.EDGE,this);//top wall
			spaces[x][0] = new DungeonSpace(spaces[x][0],DungeonSpaceType.EDGE,this); //bottom wall
		}
		for(int y = 0 ; y < this.upperRightCorner.getY() ; y++){
			spaces[0][y] = new DungeonSpace(spaces[0][y],DungeonSpaceType.EDGE,this); //left wall
			spaces[this.upperRightCorner.getX()-1][y] = new DungeonSpace(spaces[this.upperRightCorner.getX()-1][y],DungeonSpaceType.EDGE,this); //right wall
		}
		
    	
    	int consecutiveFailedPlacements = 0;
    	do {
    	    Room newRoom = new Room(new TwoDimensionalCoordinate(randInt(0, upperRightCorner.getX()), randInt(0, upperRightCorner.getY())),
					    		    randInt(minRoomSizeX, maxRoomSizeX),
					    		    randInt(minRoomSizeY, maxRoomSizeY));
    	    if (this.ableToAccomodateNewRoom(newRoom)) {
	    		this.getRooms().add(newRoom);
	    		newRoom.informOfDungeonMembership(this);
	    		consecutiveFailedPlacements = 0;
    	    } else {
    	    	consecutiveFailedPlacements++;
    	    }
    	} while (consecutiveFailedPlacements < allowedConsecutiveFailedPlacements);
    	
		
		//carving path
		
		int midX = this.upperRightCorner.getX()/2;
		int midY = this.upperRightCorner.getY()/2;
		
		Stack<DungeonSpace> hallwayStack = new Stack<DungeonSpace>();
		LinkedHashSet<DungeonSpace> hallwaySet = new LinkedHashSet<DungeonSpace>();
		
		TwoDimensionalCoordinate currentCoordinate = new TwoDimensionalCoordinate(midX, midY);
		createNewHallwaySpace(hallwayStack,hallwaySet,currentCoordinate);
		
		//Carve the entire thing
		do{
			DungeonSpace ds = hallwayStack.peek();
			CardinalDirection[] availableDirections = ds.identifyDirectionsAvailableForCarvingHallway();
			if(availableDirections.length > 0){
				currentCoordinate = CardinalDirection.randomDirectionOfThese(availableDirections).relativeTo(ds.getCoordinates());
				createNewHallwaySpace(hallwayStack,hallwaySet,currentCoordinate);
			}else{
				hallwayStack.pop();
			}
		}while(!hallwayStack.isEmpty());
		
		//Unwind as described by the unwindPercentage (optional: listed here for reference. Only use if you end up carving prior to room placement)
//		double unwindPercentage = 0.8; //How much (as a percentage) of the carving to unwind
//		List<DungeonSpace> hallwaySpaces = new LinkedList<DungeonSpace>(hallwaySet);
//		for(int i = hallwaySet.size()-1 ; i > ((1 - unwindPercentage)* hallwaySet.size()) ; i--){
//			spaces[hallwaySpaces.get(i).getCoordinates().getX()][hallwaySpaces.get(i).getCoordinates().getY()] = new Space(new TwoDimensionalCoordinate(hallwaySpaces.get(i).getCoordinates().getX(),hallwaySpaces.get(i).getCoordinates().getY()),this,false);
//		}
		
		//Visit each room and carve out one of the walls. This makes it so each room is connected to the dungeon.
		for(Room room: rooms){
			List<TwoDimensionalCoordinate> potentialDoorways = room.getCandidateDoorways(this);
			TwoDimensionalCoordinate doorway = potentialDoorways.get(rand.nextInt((potentialDoorways.size())));
			spaces[doorway.getX()][doorway.getY()] = new DungeonSpace(spaces[doorway.getX()][doorway.getY()],DungeonSpaceType.DOORWAY,this);
		}
    }
    
    private void createNewHallwaySpace(Stack<DungeonSpace> hallwayStack,LinkedHashSet<DungeonSpace> hallwaySet, TwoDimensionalCoordinate location){
    	spaces[location.getX()][location.getY()] = new DungeonSpace(spaces[location.getX()][location.getY()],DungeonSpaceType.HALLWAY,this);
    	hallwayStack.push((DungeonSpace)spaces[location.getX()][location.getY()]);
    	hallwaySet.add((DungeonSpace)spaces[location.getX()][location.getY()]);
    }
    
    public int numberOfHallwaySpacesAdjacentToGivenCoordinate(TwoDimensionalCoordinate givenCoordinate){
    	int result = 0;
    	
		for(CardinalDirection direction : CardinalDirection.values()){
			if(isHallwaySpace(spaces[direction.relativeTo(givenCoordinate).getX()][direction.relativeTo(givenCoordinate).getY()])){
				result++;
			}
		}
    	
    	return result;
    }
    
    private boolean isHallwaySpace(Space space){
    	if(space instanceof DungeonSpace){
    		if((((DungeonSpace) space).getDungeonSpaceType()) == DungeonSpaceType.HALLWAY){
    			return true;
    		}
    	}
    	return false;
    }

    public Collection<Room> getRooms() {
    	return rooms;
    }

	public Space[][] getSpaces() {
		return spaces;
	}

	private boolean ableToAccomodateNewRoom(Room candidateNewRoom) {
	// Special case: room occupies the dungeon's perimeter (this is not allowed)
	if (candidateNewRoom.getBottomLeftCoordinate().getX() < 2) // Check left border
	    return false;
	if (candidateNewRoom.getBottomLeftCoordinate().getY() < 2) // Check bottom border
	    return false;
	if (candidateNewRoom.getTopRightCoordinate().getY() > this.upperRightCorner.getY()-2) // Check top border
	    return false;
	if (candidateNewRoom.getTopRightCoordinate().getX() > this.upperRightCorner.getX()-2) // Check right border
	    return false;
	
	//TODO The follow checks are a 'shim' to make sure a special case is not encountered:
	//Current hallway carving begins from the center of the dungeon.
	//This happens after rooms are placed in the dungeon.
	//This means there can be a problem if a room ends up right in the center of the dungeon.
	//So, to prevent that, the following check is here.
	//This should be temporary.. This check really won't work for dungeons that are too small.
	//So be wary.
	Room fakeRoomInTheMiddle = new Room(new TwoDimensionalCoordinate(( (this.upperRightCorner.getX()/2)-5), (this.upperRightCorner.getY()/2)-5),5,5);
	if(!candidateNewRoom.separateFrom(fakeRoomInTheMiddle)){
		return false;
	}
	
	

	for (Room room : rooms) {
	    if (!room.separateFrom(candidateNewRoom)) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	;
	// Note the selected bounds (y>-1, x<= corner) makes
	// it so that the drawn dungeon does not have rooms "overflowing"
	// the bottom and right edges of the dungeon.
	// this may or may not be an issue to work out when placing the dungeon
	// in a world
	for (int y = (spaces.length -1) ; y >= 0 ; y--) {
	    for (int x = 0; x < spaces[y].length; x++) {
	    	sb.append(spaces[x][y]);
	    }
	    sb.append("\n");
	}
	// Uncomment to include rooms in the printed output
	// sb.append("\n\n");
	// for(Room room: rooms){
	// sb.append(room.toString() +"\n");
	// }
	return sb.toString();
    }
    
    private static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
    }
}
