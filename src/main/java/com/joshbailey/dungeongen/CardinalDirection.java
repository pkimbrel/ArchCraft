package com.joshbailey.dungeongen;

import java.util.Random;

public enum CardinalDirection {
	
	NORTH(0,1),
	SOUTH(0,-1),
	WEST(-1,0),
	EAST(1,0);
	
	public static CardinalDirection randomDirection(){
		CardinalDirection[] directions = CardinalDirection.values();
		return directions[rand.nextInt((directions.length))];
	}
	
	public static CardinalDirection randomDirectionOfThese(CardinalDirection[] available){
		return available[rand.nextInt((available.length))];
	}
	
	private static Random rand = new Random();
	
	private int relative_x;
	private int relative_y;
	
	private CardinalDirection(int relative_x, int relative_y){
		this.relative_x = relative_x;
		this.relative_y = relative_y;
	}
	
	public TwoDimensionalCoordinate relativeTo(TwoDimensionalCoordinate startingCoordinate){
		return new TwoDimensionalCoordinate(startingCoordinate.getX() + relative_x, startingCoordinate.getY() + relative_y);
	}
	
}
