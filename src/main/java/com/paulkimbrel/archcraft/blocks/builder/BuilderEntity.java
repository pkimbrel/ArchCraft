package com.paulkimbrel.archcraft.blocks.builder;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.joshbailey.dungeongen.DungeonGenerator;
import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.core.SpatialInventoryTileEntity;
import com.paulkimbrel.archcraft.messaging.ICommandReceiver;

public class BuilderEntity extends SpatialInventoryTileEntity {
    public static String[][] testPattern1 = new String[][] {
	    { "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@",
		    "@@@@@@@@@" },
	    { "*********",
		    "*       *",
		    "*       *",
		    "*       *",
		    "        *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*********" },

	    { "*********",
		    "*       *",
		    "*       *",
		    "*       *",
		    "        *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*********" },
	    { "*********",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*********" },

	    { "*********",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*********" },
	    { "*********",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*       *",
		    "*********" },

	    { "*********",
		    "*********",
		    "**##*##**",
		    "**##*##**",
		    "*********",
		    "**##*##**",
		    "**##*##**",
		    "*********",
		    "*********" },
    };

    public BuilderEntity() {
	super();
	this.inventory = new ItemStack[9];
    }
    
    @Override
    public void executeCommand(World world, int x, int y, int z, String command) {
	if (command.equals("testPattern1")) {
	    buildTestPattern1(world, x, y, z);
	} else if (command.equals("clearTestPattern1")) {
	    clearTestPattern1(world, x, y, z);
	} else if (command.equals("testDungeon1")) {
	    buildTestDungeon1(world, x, y, z);
	} else {
	    super.executeCommand(world, x, y, z, command);
	}
    }

    private void clearTestPattern1(World world, int x, int y, int z) {
	int direction = world.getBlockMetadata(x, y, z);

	for (int py = 0; py < height + 1; py++) {
	    for (int pz = 0; pz < width; pz++) {
		for (int px = 0; px < depth; px++) {
		    Block block;
		    if (py == 0) {
			block = Blocks.dirt;
		    } else {
			block = Blocks.air;
		    }

		    if (direction == Main.META_EAST) {
			world.setBlock(x + 1 + px, y + py - 1, z + pz, block);
		    } else if (direction == Main.META_SOUTH) {
			world.setBlock(x - pz, y + py - 1, z + px + 1, block);
		    } else if (direction == Main.META_WEST) {
			world.setBlock(x - px - 1, y + py - 1, z - pz, block);
		    } else {
			world.setBlock(x + pz, y + py - 1, z - px - 1, block);
		    }
		}
	    }
	}
    }

    private void buildTestDungeon1(World world, int x, int y, int z) {
	int direction = world.getBlockMetadata(x, y, z);

	String dungeon = DungeonGenerator.generateDungeon();
	String[] lines = dungeon.split("\n");

	int height = 7;

	for (int pz = 0; pz < lines.length; pz++) {
	    String row = lines[pz];
	    for (int px = 0; px < row.length(); px++) {
		char blockIndicator = row.charAt(px);
		Block floor = Blocks.stone;
		Block ceiling = Blocks.glowstone;
		Block walls = Blocks.cobblestone;
		Block air = Blocks.air;
		Block glass = Blocks.glass;
		if (blockIndicator == ' ') {
		    walls = air;
		}
		if(blockIndicator == '#'){
			walls = glass;
		}
		if(blockIndicator == '@'){ //Temporary switch: '@' should really be solid, but this makes it easy to see the rooms
			walls = air;
		}
		for (int py = -1; py < height; py++) {
		    Block block;
		    if (py < 0) {
			block = floor;
		    } else if (py == height - 1) {
			block = ceiling;
		    } else {
			block = walls;
		    }
		    int bx, by, bz;
		    if (direction == Main.META_EAST) {
			bx = x + 1 + px;
			by = y + py;
			bz = z + pz;
		    } else if (direction == Main.META_SOUTH) {
			bx = x - pz;
			by = y + py;
			bz = z + px + 1;
		    } else if (direction == Main.META_WEST) {
			bx = x - px - 1;
			by = y + py;
			bz = z - pz;
		    } else {
			bx = x + pz;
			by = y + py;
			bz = z - px - 1;
		    }
		    world.setBlock(bx, by, bz, block);
		}
	    }
	}
    }

    private void buildTestPattern1(World world, int x, int y, int z) {
	int direction = world.getBlockMetadata(x, y, z);

	for (int py = 0; py < testPattern1.length; py++) {
	    String[] level = testPattern1[py];
	    for (int pz = 0; pz < level.length; pz++) {
		String row = testPattern1[py][pz];
		for (int px = 0; px < row.length(); px++) {
		    Block block;
		    int metaData = -1;
		    if (testPattern1[py][pz].charAt(px) == '*') {
			block = Blocks.cobblestone;
		    } else if (testPattern1[py][pz].charAt(px) == '#') {
			block = Blocks.glass;
		    } else if (testPattern1[py][pz].charAt(px) == '@') {
			block = Blocks.air;
		    } else if (testPattern1[py][pz].charAt(px) == '!') {
			block = Blocks.wooden_door;
			metaData = 3;
		    } else if (testPattern1[py][pz].charAt(px) == '$') {
			block = Blocks.wooden_door;
			metaData = 8;
		    } else {
			block = Blocks.air;
		    }
		    int bx, by, bz;
		    if (direction == Main.META_EAST) {
			bx = x + 1 + px;
			by = y + py - 1;
			bz = z + pz;
		    } else if (direction == Main.META_SOUTH) {
			bx = x - pz;
			by = y + py - 1;
			bz = z + px + 1;
		    } else if (direction == Main.META_WEST) {
			bx = x - px - 1;
			by = y + py - 1;
			bz = z - pz;
		    } else {
			bx = x + pz;
			by = y + py - 1;
			bz = z - px - 1;
		    }
		    if (metaData >= 0) {
			world.setBlock(bx, by, bz, block, metaData, 2);
		    } else {
			world.setBlock(bx, by, bz, block);
		    }
		}
	    }
	}
    }


}
