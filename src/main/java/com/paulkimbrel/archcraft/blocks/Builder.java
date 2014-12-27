package com.paulkimbrel.archcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.paulkimbrel.archcraft.Main;

public class Builder extends Block {
    public IIcon[] icons = new IIcon[6];

    public Builder(String unlocalizedName, Material material) {
	super(material);
	
	this.setBlockName(unlocalizedName);
        this.setBlockTextureName(Main.MODID + ":" + unlocalizedName);
        this.setCreativeTab(Main.creativeTab);
        this.setHardness(2.0F);
        this.setResistance(6.0F);
        this.setLightLevel(0.2F);
        this.setHarvestLevel("pickaxe", 3);
        this.setStepSound(soundTypeMetal);
    }
    
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < 6; i ++) {
            switch (i) {
            case 0: 
                this.icons[i] = reg.registerIcon(this.textureName + "-bottom");
        	break;
            case 1:
                this.icons[i] = reg.registerIcon(this.textureName + "-top");
        	break;
            case 2:
            case 3:
                this.icons[i] = reg.registerIcon(this.textureName + "-side1");
        	break;
            case 4:
            case 5:
                this.icons[i] = reg.registerIcon(this.textureName + "-side2");
        	break;
            }
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return this.icons[side];
    }
}
