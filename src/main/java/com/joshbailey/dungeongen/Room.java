package com.joshbailey.dungeongen;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.joshbailey.dungeongen.RoomSpace.RoomSpaceType;

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
	private Collection<TwoDimensionalCoordinate> outerRing = null;
	private Collection<TwoDimensionalCoordinate> getOuterRing(){
		if(outerRing==null){
			outerRing = new LinkedList<TwoDimensionalCoordinate>();
			
			for(int i = this.getTopLeftCoordinate().getX() ; i <= this.getTopRightCoordinate().getX() ; i++){ 
				outerRing.add(new TwoDimensionalCoordinate(i, this.getTopLeftCoordinate().getY())); //top wall
				outerRing.add(new TwoDimensionalCoordinate(i, this.getBottomLeftCoordinate().getY())); //bottom wall
			}
			for(int i = this.getBottomRightCoordinate().getY() ; i <= this.getTopLeftCoordinate().getY() ; i++){
				outerRing.add(new TwoDimensionalCoordinate(this.getTopLeftCoordinate().getX(), i)); //left wall
				outerRing.add(new TwoDimensionalCoordinate(this.getTopRightCoordinate().getX(), i)); //right wall
			}
		}
		return outerRing;
	}
	
	private Collection<TwoDimensionalCoordinate> occupiedCoordinates = null;
	private Collection<TwoDimensionalCoordinate> getOccupiedCoordinates(){
		if(occupiedCoordinates == null){
		
			occupiedCoordinates = new LinkedList<TwoDimensionalCoordinate>();
			
			for(int x = this.getTopLeftCoordinate().getX() ; x <= this.getTopRightCoordinate().getX() ; x++){
				for(int y = this.getBottomRightCoordinate().getY() ; y <= this.getTopLeftCoordinate().getY() ; y++){
					occupiedCoordinates.add(new TwoDimensionalCoordinate(x, y)); //top wall	
				}
			}
		}
		
		return occupiedCoordinates;
	}
	
	public Room(TwoDimensionalCoordinate startingLocation, int size_x, int size_y) {
		
		if(startingLocation == null) 
			throw new IllegalArgumentException("Cannot create Room with null startingLocation");
		if(size_x < 3) 
			throw new IllegalArgumentException("Cannot create Room with size_x less than 3");
		if(size_y < 3) 
			throw new IllegalArgumentException("Cannot create Room with size_y less than 3");
		
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
		
		//Mark passable rooms as passable (by converting them to a RoomSpace)
		for(int x = this.getBottomLeftCoordinate().getX() ; x < this.getTopRightCoordinate().getX() ; x++){
			for(int y = this.getBottomLeftCoordinate().getY() ; y < this.getTopRightCoordinate().getY() ; y++){
				RoomSpace roomSpace = new RoomSpace(parentDungeon.getSpaces()[x][y],this,RoomSpaceType.OPEN_AREA);
				parentDungeon.getSpaces()[x][y] = roomSpace; 
			}
		}
		
		//Mark the walls of the room
		Collection<TwoDimensionalCoordinate> wallCoordinates = this.getOuterRing();
		for(TwoDimensionalCoordinate wallCoordinate : wallCoordinates){
			RoomSpace roomSpace = new RoomSpace(parentDungeon.getSpaces()[wallCoordinate.getX()][wallCoordinate.getY()],this,RoomSpaceType.WALL);
			parentDungeon.getSpaces()[wallCoordinate.getX()][wallCoordinate.getY()] = roomSpace; 
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
		return Collections.disjoint(this.getOccupiedCoordinates(), otherRoom.getOccupiedCoordinates());
	}
	

	@Override
	public String toString() {
		return "Room [startingLocation=" + startingLocation + ", size_x="
				+ size_x + ", size_y=" + size_y + "]";
	}
	
}