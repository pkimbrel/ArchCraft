package com.paulkimbrel.archcraft.proxy;

import com.paulkimbrel.archcraft.ModBlocks;
import com.paulkimbrel.archcraft.ModItems;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
	ModItems.init();
	ModBlocks.init();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
