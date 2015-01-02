package com.paulkimbrel.archcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.paulkimbrel.archcraft.blocks.ArchitectTable;
import com.paulkimbrel.archcraft.blocks.Builder;
import com.paulkimbrel.archcraft.blocks.LaserOld;
import com.paulkimbrel.archcraft.entities.ArchitectTableEntity;
import com.paulkimbrel.archcraft.entities.BuilderEntity;
import com.paulkimbrel.archcraft.entities.LaserEntityOld;

import cpw.mods.fml.common.registry.GameRegistry;

public class AllBlocks {
    public static Block builder;
    public static Block architectTable;
    public static Block laser;
    
    public static final void init() {
	GameRegistry.registerTileEntity(BuilderEntity.class, "builderEntity");
	GameRegistry.registerTileEntity(ArchitectTableEntity.class, "tileEntityArchitectTable");
	
	builder = new Builder("builder", Material.iron);
	GameRegistry.registerBlock(builder, "builder");
	
	architectTable = new ArchitectTable("architectTable",Material.wood);
	GameRegistry.registerBlock(architectTable, "architectTable");
	
	//laser = new LaserOld("laser", Material.wood);
	//GameRegistry.registerBlock(laser,  "laser");
	//GameRegistry.registerTileEntity(LaserEntityOld.class, "tileEntityLaser");
	
    }

}
