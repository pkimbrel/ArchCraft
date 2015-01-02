package com.paulkimbrel.archcraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LaserEntity extends Entity {
    @SideOnly(Side.CLIENT)
    public IIcon texture;

    public int builderX = 0;
    public int builderY = 0;
    public int builderZ = 0;
    
    public boolean initialized = false;

    public LaserEntity(World world) {
	super(world);
	if (!world.isRemote) {
	    System.out.println("*********AUTO KILL");
	    isDead = true;
	}
    }

    public LaserEntity(World world, BuilderEntity builderEntity) {
	super(world);
	System.out.println("*********CREATE LEGIT");
	builderX = builderEntity.xCoord;
	builderY = builderEntity.yCoord;
	builderZ = builderEntity.zCoord;
	initialized = true;
	
	System.out.println(builderX + " - " + builderY + " - " + builderZ);
	
	setPosition(builderX + .5, builderY + .5, builderZ + .5);
    }

    @Override
    protected void entityInit() {
	System.out.println("*********I've been created");
	setSize(40, 40);
	
	preventEntitySpawning = false;
	noClip = true;
	isImmuneToFire = true;
	
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
	/*if (!initialized) {
	    builderX = (int)x;
	    builderY = (int)x;
	    builderZ = (int)y;
	    initialized = true;
	}*/
        super.setPosition(x, y, z);
    }
    
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int slartybartfarst)
    {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
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
