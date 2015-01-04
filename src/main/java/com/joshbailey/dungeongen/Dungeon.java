package com.joshbailey.dungeongen;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

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
    	
    }

    public Collection<Room> getRooms() {
    	return rooms;
    }

	public Space[][] getSpaces() {
		return spaces;
	}

	private boolean ableToAccomodateNewRoom(Room candidateNewRoom) {
	// Special case: room occupies the dungeon's perimeter (this is not allowed)
	if (candidateNewRoom.getBottomLeftCoordinate().getX() == 0) // Check left border
	    return false;
	if (candidateNewRoom.getBottomLeftCoordinate().getY() == 0) // Check bottom border
	    return false;
	if (candidateNewRoom.getTopRightCoordinate().getY() >= this.upperRightCorner.getY()-1) // Check top border
	    return false;
	if (candidateNewRoom.getTopRightCoordinate().getX() >= this.upperRightCorner.getX()-1) // Check right border
	    return false;

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
	    	sb.append(spaces[y][x]);
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
