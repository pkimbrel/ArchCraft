package com.joshbailey.dungeongen;

import java.util.ArrayList;
import java.util.Collection;


public class DungeonSpace extends Space{
	
	private DungeonSpaceType dungeonSpaceType;
	private Dungeon dungeon;
	
	public DungeonSpace(Space space,DungeonSpaceType dungeonSpaceType, Dungeon dungeon) {
		super(space.getCoordinates(), space.getParentDungeon(),dungeonSpaceType.isPassable());
		this.dungeonSpaceType = dungeonSpaceType;
		this.dungeon = dungeon;
	}
	
	public CardinalDirection[] identifyDirectionsAvailableForCarvingHallway(){
		Collection<CardinalDirection> availableDirections = new ArrayList<CardinalDirection>();
		
		CardinalDirection[] allDirections = CardinalDirection.values();
		for(CardinalDirection direction : allDirections){
			TwoDimensionalCoordinate candidateCoordinates = direction.relativeTo(this.getCoordinates());
			if((dungeon.getSpaces()[candidateCoordinates.getX()][candidateCoordinates.getY()].getClass() == Space.class)){
				if(dungeon.numberOfHallwaySpacesAdjacentToGivenCoordinate(candidateCoordinates) == 1){
					availableDirections.add(direction);
				}
			}
		}
		return availableDirections.toArray(new CardinalDirection[availableDirections.size()]);
	}
	

	public DungeonSpaceType getDungeonSpaceType() {
		return dungeonSpaceType;
	}

	@Override
	public String toString() {
		return dungeonSpaceType.getSymbol();
	}
	
	public enum DungeonSpaceType{
		EDGE("X",false),
		HALLWAY(" ",true);
		
		private DungeonSpaceType(String symbol,
								 boolean passable){
			this.symbol = symbol;
			this.passable = passable;}
		
		private String symbol;
		private boolean passable;
		
		public String getSymbol(){
			return symbol;
		}
		public boolean isPassable() {
			return passable;
		}
		
	}
}
