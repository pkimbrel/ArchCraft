package com.paulkimbrel.archcraft.blocks.builder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.paulkimbrel.archcraft.core.BaseGUIContainer;

public class BuilderGUIContainer extends BaseGUIContainer {
    public BuilderGUIContainer(InventoryPlayer player, IInventory inventory) {
	super(player, inventory);
	
	// The slot constructor takes the inventory and the slot number 
	// and binds them to an x-y coordinates on the screen
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		addSlotToContainer(new Slot(inventory, j + i * 3, 62 + j * 18, 17 + i * 18));
	    }
	}

	// Commonly used vanilla code that adds the player's inventory
	bindPlayerInventory(player);
	
    }
}