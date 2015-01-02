package com.paulkimbrel.archcraft.proxy;

import com.paulkimbrel.archcraft.AllBlocks;
import com.paulkimbrel.archcraft.AllGuis;
import com.paulkimbrel.archcraft.AllItems;
import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.entities.LaserEntity;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
	AllItems.init();
	AllBlocks.init();
	new AllGuis();
	
	EntityRegistry.registerModEntity(LaserEntity.class, "laser", 0, Main.instance, 80, 1, false);
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
