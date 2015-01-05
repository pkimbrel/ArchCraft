package com.paulkimbrel.archcraft.blocks.architecttable;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.paulkimbrel.archcraft.core.BaseGUIContainer;

public class ArchitectTableGUIContainer extends BaseGUIContainer {

    public ArchitectTableGUIContainer(InventoryPlayer player, IInventory inventory) {
	super(player, inventory);
	
	addSlotToContainer(new Slot(inventory, 0, 50, 50));
    }

}
