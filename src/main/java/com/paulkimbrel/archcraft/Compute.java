package com.paulkimbrel.archcraft;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class Compute {
    public static int computeDirection(Entity entity) {
	return MathHelper.floor_double((double) (entity.rotationYaw / 90.0f) + 0.5d) & 3;
    }
}
