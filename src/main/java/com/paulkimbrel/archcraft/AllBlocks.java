package com.paulkimbrel.archcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.paulkimbrel.archcraft.blocks.ArchitectTable;
import com.paulkimbrel.archcraft.blocks.ArchitectTableEntity;
import com.paulkimbrel.archcraft.blocks.Builder;
import com.paulkimbrel.archcraft.blocks.BuilderEntity;
import com.paulkimbrel.archcraft.blocks.Laser;
import com.paulkimbrel.archcraft.blocks.LaserEntity;

import cpw.mods.fml.common.registry.GameRegistry;

public class AllBlocks {
    public static Block builder;
    public static Block architectTable;
    public static Block laser;
    
    public static final void init() {
	builder = new Builder("builder", Material.iron);
	GameRegistry.registerBlock(builder, "builder");
	GameRegistry.registerTileEntity(BuilderEntity.class, "builderEntity");
	
	architectTable = new ArchitectTable("architectTable",Material.wood);
	GameRegistry.registerBlock(architectTable, "architectTable");
	GameRegistry.registerTileEntity(ArchitectTableEntity.class, "tileEntityArchitectTable");
	
	laser = new Laser("laser", Material.wood);
	GameRegistry.registerBlock(laser,  "laser");
	GameRegistry.registerTileEntity(LaserEntity.class, "tileEntityLaser");
    }

}
