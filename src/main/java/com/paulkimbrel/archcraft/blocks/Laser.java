package com.paulkimbrel.archcraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Main;

public class Laser extends BlockContainer {

    public Laser(String unlocalizedName, Material material) {
	super(material);
	this.setBlockName(unlocalizedName);
	this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
	this.setCreativeTab(Main.creativeTab);
    }

    //Make sure you set this as your TileEntity class relevant for the block!
    @Override
    public TileEntity createNewTileEntity(World world, int id) {
            return new LaserEntity();
    }
    
    //You don't want the normal render type, or it wont render properly.
    @Override
    public int getRenderType() {
            return -1;
    }
    
    //It's not an opaque cube, so you need this.
    @Override
    public boolean isOpaqueCube() {
            return false;
    }
    
    //It's not a normal block, so you need this too.
    public boolean renderAsNormalBlock() {
            return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
	this.setBlockBounds(0, 0, 0, 1, 1, 20);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_,
            int p_149668_2_,
            int p_149668_3_,
            int p_149668_4_) {
        // TODO Auto-generated method stub
        return null;
    }
}
