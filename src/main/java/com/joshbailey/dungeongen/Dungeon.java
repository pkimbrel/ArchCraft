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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();;
		for(int y = upperRightCorner.getY() ; y > -1 ; y--){
			for(int x = 0 ; x < upperRightCorner.getX() ; x++){
				boolean occupied = false;
				for(Room room : rooms){
					if(room.occupies(new TwoDimensionalCoordinate(x, y))){
						occupied = true;
						break;
					}
				}
				sb.append(occupied ? ".":"@");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
