package com.paulkimbrel.archcraft.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.blocks.architecttable.ArchitectTable;
import com.paulkimbrel.archcraft.blocks.architecttable.ArchitectTableEntity;
import com.paulkimbrel.archcraft.blocks.builder.Builder;
import com.paulkimbrel.archcraft.blocks.builder.BuilderEntity;
import com.paulkimbrel.archcraft.entities.laser.LaserEntity;
import com.paulkimbrel.archcraft.items.SquareAndCompass;
import com.paulkimbrel.archcraft.messaging.GuiHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public static SquareAndCompass squareAndCompass;

    public static Block builder;
    public static Block architectTable;

    public void preInit(FMLPreInitializationEvent e) {
	registerEntities();
	registerBlocks();
	new GuiHandler();

	EntityRegistry.registerModEntity(LaserEntity.class, "laser", 0, Main.instance, 80, 1, false);
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
    
    public void registerEntities() {
	squareAndCompass = new SquareAndCompass("squareAndCompass");
	GameRegistry.registerItem(squareAndCompass, "squareAndCompass");
    }
    
    public void registerBlocks() {
	GameRegistry.registerTileEntity(BuilderEntity.class, "builderEntity");
	GameRegistry.registerTileEntity(ArchitectTableEntity.class, "tileEntityArchitectTable");
	
	builder = new Builder("builder", Material.iron);
	GameRegistry.registerBlock(builder, "builder");
	
	architectTable = new ArchitectTable("architectTable",Material.wood);
	GameRegistry.registerBlock(architectTable, "architectTable");

    }
}
