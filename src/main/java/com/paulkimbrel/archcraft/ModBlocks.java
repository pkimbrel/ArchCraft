package com.paulkimbrel.archcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.paulkimbrel.archcraft.blocks.Builder;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModBlocks {
    public static Block builder;

    public static final void init() {
	builder = new Builder("builder", Material.cloth);
	GameRegistry.registerBlock(builder, "builder");
    }

}
