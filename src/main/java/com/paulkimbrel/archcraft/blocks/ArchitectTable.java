package com.paulkimbrel.archcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Compute;
import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.entities.ArchitectTableEntity;
import com.paulkimbrel.archcraft.entities.BuilderEntity;
import com.paulkimbrel.archcraft.entities.LaserEntity;

public class ArchitectTable extends BlockContainer {

    private IIcon icon;
    
    public LaserEntity laser;

    public ArchitectTable(String unlocalizedName, Material material) {
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

    // Make sure you set this as your TileEntity class relevant for the block!
    @Override
    public TileEntity createNewTileEntity(World world, int id) {
	return new ArchitectTableEntity();
    }

    // You don't want the normal render type, or it wont render properly.
    @Override
    public int getRenderType() {
	return -1;
    }

    // It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube() {
	return false;
    }

    // It's not a normal block, so you need this too.
    public boolean renderAsNormalBlock() {
	return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
	icon = reg.registerIcon("archcraft:architectTableIcon");
	System.out.println(icon);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
	return icon;
    }

    @Override
    public void onBlockPlacedBy(World world,
	    int x,
	    int y,
	    int z,
	    EntityLivingBase entity,
	    ItemStack itemStack) {
	world.setBlockMetadataWithNotify(x, y, z, Compute.computeDirection(entity), 2);

    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int mysteryParameter) {
	TileEntity tileEntity = world.getTileEntity(x, y, z);
	if (!(tileEntity instanceof ArchitectTableEntity)) {
	    return;
	}
	
	//ArchitectTableEntity builder = (ArchitectTableEntity) tileEntity;
	//if (!world.isRemote) {
	    //builder.removeLasers();
	//}

	super.breakBlock(world, x, y, z, block, mysteryParameter);
    }
}
