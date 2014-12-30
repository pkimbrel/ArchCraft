package com.joshbailey.dungeongen;

public class DungeonTestHarness {

	public static void main(String[] arg){
		Dungeon dungeon = new Dungeon(new TwoDimensionalCoordinate(15, 15));
			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(5, 5),3,3));
			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(1, 2),2,2));
			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(10, 9),2,3));
		System.out.println(dungeon);
	}
	
}
