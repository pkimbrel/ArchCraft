package com.paulkimbrel.archcraft.proxy;

import com.paulkimbrel.archcraft.entities.ArchitectTableEntity;
import com.paulkimbrel.archcraft.entities.LaserEntity;
import com.paulkimbrel.archcraft.entities.LaserEntityOld;
import com.paulkimbrel.archcraft.renderer.ArchitectTableRenderer;
import com.paulkimbrel.archcraft.renderer.LaserRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ClientRegistry.bindTileEntitySpecialRenderer(ArchitectTableEntity.class, new ArchitectTableRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(LaserEntityOld.class, new LaserRenderer());
        RenderingRegistry.registerEntityRenderingHandler(LaserEntity.class, new LaserRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
}
