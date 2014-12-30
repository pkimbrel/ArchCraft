package com.joshbailey.dungeongen;

/**
 * Represents a X,Y coordinate pair, in the traditional Cartesian sense.
 * @author jshwa86
 *
 */
public class TwoDimensionalCoordinate {

	/**
	 * This TwoDimensionalCoordinate's position on the X axis.
	 */
	private int x;
	
	/**
	 * This TwoDimensionalCoordinate's position on the Y axis.
	 */
	private int y;

	public TwoDimensionalCoordinate(int x, int y) {
		if(x < 0) 
			throw new IllegalArgumentException("unable to create TwoDimensionalCoordinate with x < 0");
		if(y < 0) 
			throw new IllegalArgumentException("unable to create TwoDimensionalCoordinate with y < 0");
		this.x = x;
		this.y = y;
	}
	public int getX() {return x;}
	public int getY() {return y;}
	
	@Override
	public String toString() {
		return "TwoDimensionalCoordinate [x=" + x + ", y=" + y + "]";
	}
	
}
