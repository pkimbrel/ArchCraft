package com.joshbailey.dungeongen;


/**
 * Each instance of this class represents a single space in the Dungeon.
 * @author jshwa86
 *
 */
public class Space {
	
	public static Space[][] manufacture2dSpaceArray(int startX, int startY, int sizeX, int sizeY, Dungeon parentDungeon){
		Space[][] spaces = new Space[sizeX][sizeY];
		for(int x = startX ; x < sizeX ; x++){
			for(int y = startY ; y < sizeY; y++){
				spaces[x][y] = new Space(new TwoDimensionalCoordinate(x, y),parentDungeon);
			}
		}
		return spaces;
	}
	
	/**
	 * Represents where in the dungeon this Space is.
	 */
	private TwoDimensionalCoordinate coordinates;
	
	private Dungeon parentDungeon;

	/**
	 * True if this Space can be freely moved through.
	 * False if the Space represents a wall / non-passable location
	 */
	private boolean passable;

	public Space(TwoDimensionalCoordinate coordinates, Dungeon parentDungeon) {
		if(coordinates == null)
			throw new IllegalArgumentException("Cannot create com.joshbailey.dungeongen.Space with null coordinates");
		if(parentDungeon == null)
			throw new IllegalArgumentException("Cannot create com.joshbailey.dungeongen.Space with null parentDungeon");

		this.coordinates = coordinates;
		this.parentDungeon = parentDungeon;
		passable = false;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public TwoDimensionalCoordinate getCoordinates() {
		return coordinates;
	}

	public Dungeon getParentDungeon() {
		return parentDungeon;
	}

	@Override
	public String toString() {
		return passable ? " " : "@";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Space other = (Space) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		return true;
	}
	
}
