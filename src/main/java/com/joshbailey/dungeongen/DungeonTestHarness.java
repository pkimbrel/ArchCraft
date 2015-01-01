package com.joshbailey.dungeongen;

import java.util.Random;

public class DungeonTestHarness {

	private static Random rand = new Random();
	
	public static void main(String[] arg){
		/*<><><><><><><><><><><><>
		 * Hard-coded dungeon
		 *<><><><><><><><><><><><>
		 */
//		Dungeon dungeon = new Dungeon(new TwoDimensionalCoordinate(15, 15));
//			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(5, 5),3,3));
//			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(1, 2),2,2));
//			dungeon.getRooms().add(new Room(new TwoDimensionalCoordinate(10, 9),2,3));
		
		
		/*<><><><><><><><><><><><>
		 * Generated dungeon
		 *<><><><><><><><><><><><>
		 */
		
		final int DUNGEON_SIZE_X = 50;
		final int DUNGEON_SIZE_Y = 50;
		
		final int MAX_ROOM_SIZE_X = 5;
		final int MAX_ROOM_SIZE_Y = 5;
		
		Dungeon dungeon = new Dungeon(new TwoDimensionalCoordinate(DUNGEON_SIZE_X, DUNGEON_SIZE_Y));
		int consecutiveFailedPlacements = 0;
		do{
			Room newRoom = new Room(new TwoDimensionalCoordinate(randInt(0,DUNGEON_SIZE_X), randInt(0,DUNGEON_SIZE_Y)),randInt(1,MAX_ROOM_SIZE_X),randInt(1,MAX_ROOM_SIZE_Y));
			if(dungeon.ableToAccomodateNewRoom(newRoom)){
				dungeon.getRooms().add(newRoom);
				consecutiveFailedPlacements = 0;
			}else{
				consecutiveFailedPlacements++;
			}
		}while(consecutiveFailedPlacements < 5000);
			
		System.out.println(dungeon);
		
//		Room r = new Room(new TwoDimensionalCoordinate(2, 2),2,2);
//		r.outerRing();
	}
	
	private static int randInt(int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
