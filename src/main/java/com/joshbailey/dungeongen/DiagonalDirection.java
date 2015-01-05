package com.joshbailey.dungeongen;


public enum DiagonalDirection {

	NORTHWEST(-1,1),
	NORTHEAST(1,1),
	SOUTHWEST(-1,-1),
	SOUTHEAST(1,-1);
	
	private int relative_x;
	private int relative_y;
	
	private DiagonalDirection(int relative_x, int relative_y){
		this.relative_x = relative_x;
		this.relative_y = relative_y;
	}
	
	public TwoDimensionalCoordinate relativeTo(TwoDimensionalCoordinate startingCoordinate){
		return new TwoDimensionalCoordinate(startingCoordinate.getX() + relative_x, startingCoordinate.getY() + relative_y);
	}
	
}
