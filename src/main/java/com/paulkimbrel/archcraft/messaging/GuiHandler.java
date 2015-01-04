package com.paulkimbrel.archcraft.messaging;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.blocks.Builder;
import com.paulkimbrel.archcraft.blocks.BuilderContainer;
import com.paulkimbrel.archcraft.blocks.BuilderEntity;
import com.paulkimbrel.archcraft.gui.BuilderGUI;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {
    public GuiHandler() {
	NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	TileEntity entity = world.getTileEntity(x, y, z);
	if (entity instanceof BuilderEntity) {
	    return new BuilderContainer(player.inventory, (BuilderEntity) entity);
	}

	return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	TileEntity entity = world.getTileEntity(x, y, z);
	if (entity instanceof BuilderEntity) {
	    return new BuilderGUI(player.inventory, (BuilderEntity) entity);
	}

	return null;
    }

}
