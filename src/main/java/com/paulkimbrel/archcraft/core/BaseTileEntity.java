package com.paulkimbrel.archcraft.core;

import net.minecraft.tileentity.TileEntity;

public class BaseTileEntity extends TileEntity {
    private boolean initialized = false;
    
    @Override
    public void updateEntity() {
	if ((!initialized) && (!isInvalid())) {
	    initialize();
	    initialized = true;
	}
    }
    
    protected void initialize() {
	
    }
    
}
