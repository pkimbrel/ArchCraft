package com.paulkimbrel.archcraft;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class Compute {
    public static int computeDirection(Entity entity) {
	return MathHelper.floor_double((double) (entity.rotationYaw / 90.0f) + 0.5d) & 3;
    }

    public static boolean sameDirection(int side, int direction) {
	return ((side == Main.BLOCK_NORTH) && (direction == Main.META_NORTH))
		|| ((side == Main.BLOCK_SOUTH) && (direction == Main.META_SOUTH))
		|| ((side == Main.BLOCK_EAST) && (direction == Main.META_EAST))
		|| ((side == Main.BLOCK_WEST) && (direction == Main.META_WEST));
    }
}
