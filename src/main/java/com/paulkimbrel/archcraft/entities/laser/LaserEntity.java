package com.paulkimbrel.archcraft.entities.laser;

import java.util.List;

import org.lwjgl.Sys;

import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher.WatchableObject;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LaserEntity extends Entity {
    @SideOnly(Side.CLIENT)
    public IIcon texture;

    public float offsetX = 0;
    public float offsetY = 0;
    public float offsetZ = 0;

    public int builderX = 0;
    public int builderY = 0;
    public int builderZ = 0;
    
    public boolean show = false;

    // Called by the client only.
    // If the server calls this, it's during world load - blocks will rebuild lasers so kill this instance.
    public LaserEntity(World world) {
	super(world);
	if (!world.isRemote) {
	    // Auto Kill (we shant leave any lasers behind)
	    isDead = true;
	}
    }

    // Called by the server only.
    public LaserEntity(World world, int x, int y, int z) {
	super(world);
	builderX = x;
	builderY = y;
	builderZ = z;
	posX = x;
	posY = y;
	posZ = z;
    }

    @Override
    public void setSize(float width, float height) {
	super.setSize(width, height);
	if (!worldObj.isRemote) {
	    dataWatcher.updateObject(2, builderX);
	    dataWatcher.updateObject(3, builderY);
	    dataWatcher.updateObject(4, builderZ);
	    dataWatcher.updateObject(5, Integer.valueOf((int) width));
	    dataWatcher.updateObject(6, Integer.valueOf((int) height));
	}

	int direction = worldObj.getBlockMetadata(builderX, builderY, builderZ);
	offsetY = 0.0f;
	float hw = (width - 1.0f) / 2.0f;
	switch (direction) {
	    case Main.META_SOUTH:
		offsetX = -1.0f * hw;
		offsetZ = hw + 1.0f;
		break;
	    case Main.META_WEST:
		offsetX = -1.0f * hw - 1.0f;
		offsetZ = -1.0f * hw;
		break;
	    case Main.META_NORTH:
		offsetX = hw;
		offsetZ = -1.0f * hw - 1.0f;
		break;
	    case Main.META_EAST:
		offsetX = hw + 1.0f;
		offsetZ = hw;
		break;
	}
	setPosition(builderX + offsetX + .5, builderY + offsetY + .5, builderZ + offsetZ + .5);
	show = true;
    }

    // Client wants to move the entity to avoid touching other blocks. Stupid client. "No clip" means no moving!!
    @Override
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {
    }

    @Override
    public void onUpdate() {
	super.onUpdate();
	if (worldObj.isRemote) {
	    boolean sizeChange = false;
	    if (width != dataWatcher.getWatchableObjectInt(5)) {
		width = dataWatcher.getWatchableObjectInt(5);
		sizeChange = true;
	    }

	    if (height != dataWatcher.getWatchableObjectInt(6)) {
		height = dataWatcher.getWatchableObjectInt(6);
		sizeChange = true;
	    }

	    if (sizeChange) {
		setSize(width, height);
	    }

	    List<WatchableObject> changed = dataWatcher.getChanged();
	    if (changed == null) {
		return;
	    }
	    for (WatchableObject obj : changed) {
		switch (obj.getDataValueId()) {
		    case 2:
			builderX = ((Integer) obj.getObject()).intValue();
			break;
		    case 3:
			builderY = ((Integer) obj.getObject()).intValue();
			break;
		    case 4:
			builderZ = ((Integer) obj.getObject()).intValue();
			break;
		    case 5:
			width = ((Integer) obj.getObject()).intValue();
			break;
		    case 6:
			height = ((Integer) obj.getObject()).intValue();
			setSize(width, height);
			break;
		}
	    }
	}
    }

    @Override
    protected void entityInit() {
	preventEntitySpawning = false;
	noClip = true;
	isImmuneToFire = true;
	dataWatcher.addObject(2, Integer.valueOf(0));
	dataWatcher.addObject(3, Integer.valueOf(0));
	dataWatcher.addObject(4, Integer.valueOf(builderX));
	dataWatcher.addObject(5, Integer.valueOf(builderY));
	dataWatcher.addObject(6, Integer.valueOf(builderZ));
	// setSize(64, 64);
    }

    @Override
    public void setPosition(double x, double y, double z) {
	super.setPosition(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int slartybartfarst)
    {
	// this.setPosition(x, y, z);
	// this.setRotation(yaw, pitch);
    }

    @Override
    public boolean isInsideOfMaterial(Material p_70055_1_) {
	return false;
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
	return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound data) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound data) {
    }

}
