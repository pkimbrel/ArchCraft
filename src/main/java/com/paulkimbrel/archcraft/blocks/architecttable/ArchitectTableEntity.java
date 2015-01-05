package com.paulkimbrel.archcraft.blocks.architecttable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.paulkimbrel.archcraft.core.SpatialInventoryTileEntity;
import com.paulkimbrel.archcraft.core.SpatialTileEntity;

public class ArchitectTableEntity extends SpatialInventoryTileEntity {

    public ArchitectTableEntity() {
	super();
	this.inventory = new ItemStack[1];
    }

}
