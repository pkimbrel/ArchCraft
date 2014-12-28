package com.paulkimbrel.archcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Compute;
import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.AllItems;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class Builder extends BlockContainer {
    public IIcon[] icons = new IIcon[6];

    public Builder(String unlocalizedName, Material material) {
	super(material);

	this.setBlockName(unlocalizedName);
	this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
	this.setCreativeTab(Main.creativeTab);
	this.setHardness(3.5F);
	this.setResistance(6.0F);
	this.setLightLevel(0.5F);
	this.setHarvestLevel("pickaxe", 3);
	this.setStepSound(soundTypeMetal);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
	for (int i = 0; i < 6; i++) {
	    switch (i) {
		case 0:
		    this.icons[i] = reg.registerIcon(this.textureName + "-bottom");
		    break;
		case 1:
		    this.icons[i] = reg.registerIcon(this.textureName + "-top");
		    break;
		case 2:
		case 3:
		    this.icons[i] = reg.registerIcon(this.textureName + "-side1");
		    break;
		case 4:
		case 5:
		    this.icons[i] = reg.registerIcon(this.textureName + "-side2");
		    break;
	    }
	}
    }

    @Override
    public IIcon getIcon(int side, int meta) {
	return this.icons[side];
    }

    @Override
    public boolean onBlockActivated(World world,
	    int x,
	    int y,
	    int z,
	    EntityPlayer player,
	    int side,
	    float hitX,
	    float hitY,
	    float hitZ) {
	TileEntity tileEntity = world.getTileEntity(x, y, z);
	if (tileEntity == null || player.isSneaking()) {
	    return false;
	} else {
	    player.openGui(Main.instance, Main.GUI_BUILDER, world, x, y, z);
	    return true;
	}
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int mysteryParameter) {
	dropItems(world, x, y, z);
	super.breakBlock(world, x, y, z, block, mysteryParameter);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
	world.setBlockMetadataWithNotify(x, y, z, Compute.computeDirection(entity), 2);
	if (itemStack.hasDisplayName()) {
	    ((BuilderEntity) world.getTileEntity(x, y, z)).setGuiDisplayName(itemStack.getDisplayName());
	}
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
	return new BuilderEntity(world, metadata);
    }

    private void dropItems(World world, int x, int y, int z) {
	Random rand = new Random();

	TileEntity tileEntity = world.getTileEntity(x, y, z);
	if (!(tileEntity instanceof IInventory)) {
	    return;
	}
	
	IInventory inventory = (IInventory) tileEntity;

	for (int i = 0; i < inventory.getSizeInventory(); i++) {
	    ItemStack item = inventory.getStackInSlot(i);

	    if (item != null && item.stackSize > 0) {
		float rx = rand.nextFloat() * 0.8F + 0.1F;
		float ry = rand.nextFloat() * 0.8F + 0.1F;
		float rz = rand.nextFloat() * 0.8F + 0.1F;

		EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(),
			item.stackSize,
			item.getItemDamage()));

		if (item.hasTagCompound()) {
		    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
		}

		float factor = 0.05F;
		entityItem.motionX = rand.nextGaussian() * factor;
		entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
		entityItem.motionZ = rand.nextGaussian() * factor;
		world.spawnEntityInWorld(entityItem);
		item.stackSize = 0;
	    }
	}
    }
}
