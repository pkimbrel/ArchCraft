package com.joshbailey.dungeongen;

import java.util.Collection;
import java.util.LinkedList;

public class Dungeon {

	Collection<Room> rooms;
	private TwoDimensionalCoordinate upperRightCorner;

	public Dungeon(TwoDimensionalCoordinate upperRightCorner) {
		if(upperRightCorner == null)
			throw new IllegalArgumentException("Unable to create Dungeon with null upperRightCorner");
		this.upperRightCorner = upperRightCorner;
		rooms = new LinkedList<Room>();
	}

	public Collection<Room> getRooms() {
		return rooms;
	}
	
	public boolean ableToAccomodateNewRoom(Room candidateNewRoom){
		//Special case: room occupies the dungeon's perimeter (this is not allowed)
		if(candidateNewRoom.getBottomLeftCoordinate().getX() == 0) //Check left border
			return false;
		if(candidateNewRoom.getBottomLeftCoordinate().getY() == 0) //Check bottom border
			return false;
		if(candidateNewRoom.getTopRightCoordinate().getY() >= this.upperRightCorner.getY()) //Check top border
			return false;
		if(candidateNewRoom.getTopRightCoordinate().getX() >= this.upperRightCorner.getX()) //Check right border
			return false;
		
		for(Room room:rooms){
			if(!room.separateFrom(candidateNewRoom)){
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();;
		//Note the selected bounds (y>-1, x<= corner) makes
		//it so that the drawn dungeon does not have rooms "overflowing"
		//the bottom and right edges of the dungeon.
		//this may or may not be an issue to work out when placing the dungeon
		//in a world
		for(int y = upperRightCorner.getY() ; y > -1 ; y--){
			for(int x = 0 ; x <= upperRightCorner.getX() ; x++){
				boolean occupied = false;
				for(Room room : rooms){
					if(room.occupies(new TwoDimensionalCoordinate(x, y))){
						occupied = true;
						break;
					}
				}
				sb.append(occupied ? " ":"@");
			}
			sb.append("\n");
		}
		//Uncomment to include rooms in the printed output
//		sb.append("\n\n");
//		for(Room room: rooms){
//			sb.append(room.toString() +"\n");
//		}
		return sb.toString();
	}
}
