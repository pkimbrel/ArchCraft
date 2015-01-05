package com.paulkimbrel.archcraft.core;

import net.minecraft.world.World;

import com.paulkimbrel.archcraft.entities.laser.LaserEntity;
import com.paulkimbrel.archcraft.messaging.ICommandReceiver;

public class SpatialTileEntity extends BaseTileEntity implements ICommandReceiver {
    public int width = 9;
    public int depth = 9;
    public int height = 6;

    public static final int MAX_SIZE = 64;

    public LaserEntity laser;

    @Override
    protected void initialize() {
	if (!worldObj.isRemote) {
	    laser = new LaserEntity(getWorldObj(), xCoord, yCoord, zCoord);
	    getWorldObj().spawnEntityInWorld(laser);
	    laser.setSize(Math.max(width, depth), height);
	}
	super.initialize();
    }

    @Override
    public void invalidate() {
	if (!getWorldObj().isRemote) {
	    getWorldObj().removeEntity(laser);
	}
    }

    public void executeCommand(World world, int x, int y, int z, String command) {
	    if (command.equals("widthDown")) {
	    if (width > 1) {
		width--;
	    }
	} else if (command.equals("widthUp")) {
	    if (width < MAX_SIZE) {
		width++;
	    }
	} else if (command.equals("depthDown")) {
	    if (depth > 1) {
		depth--;
	    }
	} else if (command.equals("depthUp")) {
	    if (depth < MAX_SIZE) {
		depth++;
	    }
	} else if (command.equals("heightDown")) {
	    if (height > 1) {
		height--;
	    }
	} else if (command.equals("heightUp")) {
	    if (height < MAX_SIZE) {
		height++;
	    }
	}

	laser.setSize(Math.max(width, depth), height);
	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	markDirty();

    }
}
