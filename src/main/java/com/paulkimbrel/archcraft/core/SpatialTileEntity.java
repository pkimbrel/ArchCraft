package com.paulkimbrel.archcraft.core;

import com.paulkimbrel.archcraft.entities.LaserEntity;

public class SpatialTileEntity extends BaseTileEntity {
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

}
