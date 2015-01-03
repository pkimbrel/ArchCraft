package com.paulkimbrel.archcraft.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.core.BaseTileEntity;
import com.paulkimbrel.archcraft.messaging.Command;
import com.paulkimbrel.archcraft.messaging.ICommand;

public class BuilderEntity extends BaseTileEntity implements IInventory, ICommand {
    private ItemStack[] inventory;

    public int width = 9;
    public int depth = 9;
    public int height = 6;

    public static final int MAX_SIZE = 64;

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

    public LaserEntity laser;

    public BuilderEntity() {
	super();
	this.inventory = new ItemStack[9];
    }

    @Override
    protected void initialize() {
	if (!worldObj.isRemote) {
	    addLasers();
	}
    }

    public void addLasers() {
	System.out.println("Adding lasers");
	laser = new LaserEntity(getWorldObj(), xCoord, yCoord, zCoord);
	getWorldObj().spawnEntityInWorld(laser);
    }

    public void removeLasers() {
	getWorldObj().removeEntity(laser);
    }

    @Override
    public void executeCommand(World world, int x, int y, int z, String command) {
	System.out.println(command);
	if (command.equals("testPattern1")) {
	    buildTestPattern1(world, x, y, z);
	} else if (command.equals("clearTestPattern1")) {
	    clearTestPattern1(world, x, y, z);
	} else if (command.equals("widthDown")) {
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
	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); 
	markDirty();
    }

    private void clearTestPattern1(World world, int x, int y, int z) {
	int direction = world.getBlockMetadata(x, y, z);

	for (int py = 0; py < testPattern1.length; py++) {
	    String[] level = testPattern1[py];
	    for (int pz = 0; pz < level.length; pz++) {
		String row = testPattern1[py][pz];
		for (int px = 0; px < row.length(); px++) {
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
			block = Blocks.gold_block;
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
	    System.out.println("Read width: " + width);

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
	System.out.println("Writing width: " + width);
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
