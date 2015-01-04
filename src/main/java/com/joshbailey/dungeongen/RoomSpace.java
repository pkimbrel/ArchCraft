package com.joshbailey.dungeongen;

public class RoomSpace extends Space {

	private Room room;
	private RoomSpaceType roomSpaceType;
	
	public RoomSpace(Space space,Room room,RoomSpaceType roomSpaceType) {
		super(space.getCoordinates(), space.getParentDungeon());
		if(roomSpaceType == null)
			throw new IllegalArgumentException("Cannot create RoomSpace with null RoomSpaceType");
		this.setPassable(true);
		this.room = room;
		this.roomSpaceType = roomSpaceType;
	}
	
	@Override
	public String toString() {
		return roomSpaceType.getSymbol();
	}

	public enum RoomSpaceType{
		OPEN_AREA(" "),
		WALL("#");
		
		private RoomSpaceType(String symbol){
			this.symbol = symbol;}
		
		String symbol;
		
		public String getSymbol(){
			return symbol;
		}
	}

}
