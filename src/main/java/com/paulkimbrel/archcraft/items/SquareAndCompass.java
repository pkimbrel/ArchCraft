package com.paulkimbrel.archcraft.items;

import com.paulkimbrel.archcraft.Main;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class SquareAndCompass extends Item {
    public SquareAndCompass(String unlocalizedName) {
	this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(Main.creativeTab);
        this.setTextureName(Main.MODID + ":squareAndCompass");
    }
}
