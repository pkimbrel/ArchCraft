package com.joshbailey.dungeongen;

public class RoomSpace extends Space {

	private Room room;
	private RoomSpaceType roomSpaceType;
	
	public RoomSpace(Space space,Room room,RoomSpaceType roomSpaceType) {
		super(space.getCoordinates(), space.getParentDungeon(),true);
		if(roomSpaceType == null)
			throw new IllegalArgumentException("Cannot create RoomSpace with null RoomSpaceType");
		this.room = room;
		this.roomSpaceType = roomSpaceType;
	}
	
	
	
	public RoomSpaceType getRoomSpaceType() {
		return roomSpaceType;
	}



	@Override
	public String toString() {
		return roomSpaceType.getSymbol();
	}

	public enum RoomSpaceType{
		OPEN_AREA("!"),
		DOOR("$"),
		WALL("#");
		
		private RoomSpaceType(String symbol){
			this.symbol = symbol;}
		
		private String symbol;
		
		public String getSymbol(){
			return symbol;
		}
	}

}
