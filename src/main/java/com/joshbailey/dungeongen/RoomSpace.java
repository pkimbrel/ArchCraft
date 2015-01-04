package com.joshbailey.dungeongen;

public class RoomSpace extends Space {

	private Room room;
	
	public RoomSpace(Space space,Room room) {
		super(space.getCoordinates(), space.getParentDungeon());
		this.setPassable(true);
		this.room = room;
	}

}
