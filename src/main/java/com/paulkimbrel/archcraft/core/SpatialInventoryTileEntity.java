package com.paulkimbrel.archcraft.core;

import com.paulkimbrel.archcraft.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public abstract class SpatialInventoryTileEntity extends SpatialTileEntity implements IInventory {
    protected ItemStack[] inventory;
    
    public SpatialInventoryTileEntity() {
	super();
	inventory = new ItemStack[0];
    }

    @Override
    public int getSizeInventory() {
	return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
	return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
	ItemStack stack = getStackInSlot(slot);
	if (stack != null) {
	    if (stack.stackSize <= amount) {
		setInventorySlotContents(slot, null);
	    } else {
		stack = stack.splitStack(amount);
		if (stack.stackSize == 0) {
		    setInventorySlotContents(slot, null);
		}
	    }
	}
	return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
	ItemStack stack = getStackInSlot(slot);
	if (stack != null) {
	    setInventorySlotContents(slot, null);
	}
	return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
	inventory[slot] = itemStack;
	if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
	    itemStack.stackSize = getInventoryStackLimit();
	}
    }

    @Override
    public String getInventoryName() {
	return Main.MODID + ":" + "builder.entity";
    }

    @Override
    public boolean hasCustomInventoryName() {
	return false;
    }

    @Override
    public int getInventoryStackLimit() {
	return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
	return (worldObj.getTileEntity(xCoord, yCoord, zCoord) == this) && (player.getDistanceSq(xCoord + 0.5,
		yCoord + 0.5,
		zCoord + 0.5) < 64);
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
	return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
	super.readFromNBT(tagCompound);

	NBTTagList tagList = tagCompound.getTagList("Inventory", new NBTTagCompound().getId());
	for (int i = 0; i < tagList.tagCount(); i++) {
	    NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
	    byte slot = tag.getByte("Slot");
	    if (slot >= 0 && slot < inventory.length) {
		inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
	    }
	}
	readSyncableDataFromNBT(tagCompound);
    }

    public void readSyncableDataFromNBT(NBTTagCompound tagCompound) {
	int[] dimensions = tagCompound.getIntArray("dimensions");
	if (dimensions != null && dimensions.length == 3) {
	    width = dimensions[0];
	    height = dimensions[1];
	    depth = dimensions[2];
	}
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
	super.writeToNBT(tagCompound);

	NBTTagList itemList = new NBTTagList();
	for (int i = 0; i < inventory.length; i++) {
	    ItemStack stack = inventory[i];
	    if (stack != null) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setByte("Slot", (byte) i);
		stack.writeToNBT(tag);
		itemList.appendTag(tag);
	    }
	}
	tagCompound.setTag("Inventory", itemList);
	writeSyncableDataToNBT(tagCompound);
    }

    public void writeSyncableDataToNBT(NBTTagCompound tagCompound) {
	tagCompound.setIntArray("dimensions", new int[] { width, height, depth });
    }

    @Override
    public Packet getDescriptionPacket() {
	NBTTagCompound syncData = new NBTTagCompound();
	this.writeSyncableDataToNBT(syncData);
	return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	readSyncableDataFromNBT(pkt.func_148857_g());
    }

}
