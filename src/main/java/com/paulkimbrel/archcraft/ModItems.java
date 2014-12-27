package com.paulkimbrel.archcraft;

import com.paulkimbrel.archcraft.items.SquareAndCompass;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {
    public static SquareAndCompass squareAndCompass;
    
    public static final void init() {
	squareAndCompass = new SquareAndCompass("squareAndCompass");
	GameRegistry.registerItem(squareAndCompass, "squareAndCompass");
    }
}
