package com.joshbailey.dungeongen;


public class DungeonGenerator {

    public static String generateDungeon() {


	final int DUNGEON_SIZE_X = 64;
	final int DUNGEON_SIZE_Y = 64;

	final int MIN_ROOM_SIZE_X = 5;
	final int MIN_ROOM_SIZE_Y = 5;
	final int MAX_ROOM_SIZE_X = 13;
	final int MAX_ROOM_SIZE_Y = 13;

	Dungeon dungeon = new Dungeon(new TwoDimensionalCoordinate(DUNGEON_SIZE_X, DUNGEON_SIZE_Y), 
			  MIN_ROOM_SIZE_X, 
			  MAX_ROOM_SIZE_X, 
			  MIN_ROOM_SIZE_Y, 
			  MAX_ROOM_SIZE_Y, 
			  5000);

		return dungeon.toString();
    }

}
