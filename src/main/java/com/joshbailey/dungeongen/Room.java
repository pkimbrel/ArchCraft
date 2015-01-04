package com.joshbailey.dungeongen;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Represents a room in a dungeon.
 * @author jshwa86
 *
 */
public class Room {
	
	//<><><><><><><><><><>
	//Initial parameters
	//<><><><><><><><><><>
	/**
	 * The TwoDimensionalCoordinate defining where this room begins.
	 * By convention, this point represents the bottom left corner of
	 * the room (just like a traditional two dimensional graph). This
	 * point is considered to be empty/passable.
	 */
	private TwoDimensionalCoordinate startingLocation;
	
	/**
	 * How big this room is, as expressed in units along the X axis.
	 * Rooms gain size by going "right" along the X-axis.
	 * Minimum value: 1.
	 * Examples: Value 1 = room occupies 1 square along the X axis.
	 *           Value 2 = room occupies 2 squares along the X axis.
	 *           Value n = room occupies n squares along the X axis.
	 */
	private int size_x;
	/**
	 * How big this room is, as expressed in units along the Y axis.
	 * Rooms gain space by going "up" along the Y-axis.
	 * Minimum value: 1.
	 * Examples: Value 1 = room occupies 1 square along the Y axis.
	 *           Value 2 = room occupies 2 squares along the Y axis.
	 *           Value n = room occupies n squares along the Y axis.
	 */
	private int size_y;

	//<><><><><><><><><><>
	//Derived parameters
	//<><><><><><><><><><>
	
	private TwoDimensionalCoordinate topLeftCoordinate = null;
	public  TwoDimensionalCoordinate getTopLeftCoordinate(){
		if(topLeftCoordinate == null){
			topLeftCoordinate = new TwoDimensionalCoordinate(this.getStartingLocation().getX(), this.getStartingLocation().getY()+(this.getSize_y()-1));
		}
		return topLeftCoordinate;
	}
	private TwoDimensionalCoordinate bottomLeftCoordinate = null;
	public  TwoDimensionalCoordinate getBottomLeftCoordinate(){
		if(bottomLeftCoordinate == null){
			bottomLeftCoordinate = new TwoDimensionalCoordinate(this.getStartingLocation().getX(), this.getStartingLocation().getY());
		}
		return bottomLeftCoordinate;
	}
	private TwoDimensionalCoordinate topRightCoordinate = null;
	public  TwoDimensionalCoordinate getTopRightCoordinate(){
		if(topRightCoordinate == null){
			topRightCoordinate = new TwoDimensionalCoordinate(this.getStartingLocation().getX()+(this.getSize_x()-1), this.getStartingLocation().getY()+(this.getSize_y()-1));
		}
		return topRightCoordinate;
	}
	private TwoDimensionalCoordinate bottomRightCoordinate = null;
	public  TwoDimensionalCoordinate getBottomRightCoordinate(){
		if(bottomRightCoordinate == null){
			bottomRightCoordinate = new TwoDimensionalCoordinate(this.getStartingLocation().getX()+(this.getSize_x()-1), this.getStartingLocation().getY());
		}
		return bottomRightCoordinate;
	}
	
	public Room(TwoDimensionalCoordinate startingLocation, int size_x, int size_y) {
		
		if(startingLocation == null) 
			throw new IllegalArgumentException("Cannot create Room with null startingLocation");
		if(size_x < 1) 
			throw new IllegalArgumentException("Cannot create Room with size_x less than 1");
		if(size_y < 1) 
			throw new IllegalArgumentException("Cannot create Room with size_y less than 1");
		
		this.startingLocation = startingLocation;
		this.size_x = size_x;
		this.size_y = size_y;
	}
	
	/**
	 * Intended to be called after the Dungeon decides this Room may be added to the Dungeon.
	 * This would traditionally belong in the constructor, but because it mutates objects which are not
	 * part of its own scope (i.e., the 'Spaces' really belong to the Dungeon), this method is provided seperately.
	 * This way, you can create a Room to check if the Room "fits" without also mutating the actual Dungeon.
	 */
	public void informOfDungeonMembership(Dungeon parentDungeon){
		
		for(int x = this.getBottomLeftCoordinate().getX() ; x < this.getTopRightCoordinate().getX() ; x++){
			for(int y = this.getBottomLeftCoordinate().getY() ; y < this.getTopRightCoordinate().getY() ; y++){
				RoomSpace roomSpace = new RoomSpace(parentDungeon.getSpaces()[x][y],this);
				parentDungeon.getSpaces()[x][y] = roomSpace; 
			}
		}
	}
	
	public TwoDimensionalCoordinate getStartingLocation() {return startingLocation;}
	public int getSize_x() {return size_x;}
	public int getSize_y() {return size_y;}

	/**
	 * Determines whether or not this Room shares any space (is 'separate' from) 
	 * another Room.
	 * 
	 * @param otherRoom the other Room to check for overlaps/shared space.
	 * @return true if there is no overlap. Overlap is defined as:
	 * 1. One room contains the other room
	 * 2. One room is accessible (i.e., you can walk from one room to the other)
	 * from the other.
	 * 
	 * Else, false.
	 */
	public boolean separateFrom(Room otherRoom){
	  return Collections.disjoint(this.outerRing(), otherRoom.outerRing());
	}
	
	/**
	 * Returns true if this room occupies the provided coordinate.
	 * @param coordinate The coordinate in question.
	 * @return True if this room occupies the given coordinate. Else false.
	 */
	public boolean occupies(TwoDimensionalCoordinate coordinate){
		
		if(this.getBottomLeftCoordinate().getX() <= coordinate.getX()
			&&
		   this.getBottomLeftCoordinate().getY() <= coordinate.getY()
		    &&
		   this.getTopRightCoordinate().getX() >= coordinate.getX()
		    &&
		   this.getTopLeftCoordinate().getY() >= coordinate.getY()){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a collection of TwoDimensionalCoordinate points that represents the 'outer ring'
	 * of this Room.
	 * 
	 * For ease of use, this method returns the outer ring of the Room's *walls*. This is intended to make
	 * it easy to figure out the boundaries of the Room when comparing it to other things.
	 * 
	 * Example - For this Room:
	 * @@@@
	 * @..@
	 * @@@@
	 * 
	 * Where: 
	 * @ is a wall / solid space
	 * . is open space
	 * 
	 * This method will return a collection that describes the solid space (the '@' symbols).
	 * 
	 * @return
	 */
	public Collection<TwoDimensionalCoordinate> outerRing(){
		Collection<TwoDimensionalCoordinate> outerRing = new LinkedList<TwoDimensionalCoordinate>();
		
		outerRing.add(new TwoDimensionalCoordinate(this.getTopLeftCoordinate().getX()-1, this.getTopLeftCoordinate().getY()+1)); //Top left corner
		outerRing.add(new TwoDimensionalCoordinate(this.getTopRightCoordinate().getX()+1, this.getTopRightCoordinate().getY()+1)); //Top right corner
		outerRing.add(new TwoDimensionalCoordinate(this.getBottomRightCoordinate().getX()-1, this.getBottomRightCoordinate().getY()-1)); //Bottom left corner
		outerRing.add(new TwoDimensionalCoordinate(this.getBottomLeftCoordinate().getX()+1, this.getBottomLeftCoordinate().getY()-1)); //Bottom right corner
		
		for(int i = this.getTopLeftCoordinate().getX() ; i <= this.getTopRightCoordinate().getX() ; i++){ 
			outerRing.add(new TwoDimensionalCoordinate(i, this.getTopLeftCoordinate().getY()+1)); //top wall
			outerRing.add(new TwoDimensionalCoordinate(i, this.getBottomLeftCoordinate().getY()-1)); //bottom wall
		}
		for(int i = this.getBottomRightCoordinate().getY() ; i <= this.getTopLeftCoordinate().getY() ; i++){
			outerRing.add(new TwoDimensionalCoordinate(this.getTopLeftCoordinate().getX()-1, i)); //left wall
			outerRing.add(new TwoDimensionalCoordinate(this.getTopRightCoordinate().getX()+1, i)); //right wall
		}
		return outerRing;
	}

	@Override
	public String toString() {
		return "Room [startingLocation=" + startingLocation + ", size_x="
				+ size_x + ", size_y=" + size_y + "]";
	}
	
}