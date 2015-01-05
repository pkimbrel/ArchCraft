package com.paulkimbrel.archcraft.proxy;

import com.paulkimbrel.archcraft.blocks.architecttable.ArchitectTableEntity;
import com.paulkimbrel.archcraft.blocks.architecttable.ArchitectTableRender;
import com.paulkimbrel.archcraft.entities.LaserEntity;
import com.paulkimbrel.archcraft.entities.LaserRenderer;

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
        ClientRegistry.bindTileEntitySpecialRenderer(ArchitectTableEntity.class, new ArchitectTableRender());
        RenderingRegistry.registerEntityRenderingHandler(LaserEntity.class, new LaserRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
}
