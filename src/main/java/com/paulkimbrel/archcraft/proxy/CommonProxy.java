package com.paulkimbrel.archcraft.proxy;

import com.paulkimbrel.archcraft.AllBlocks;
import com.paulkimbrel.archcraft.AllGuis;
import com.paulkimbrel.archcraft.AllItems;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
	AllItems.init();
	AllBlocks.init();
	new AllGuis();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
