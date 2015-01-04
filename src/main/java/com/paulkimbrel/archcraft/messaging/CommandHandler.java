package com.paulkimbrel.archcraft.messaging;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommandHandler implements IMessageHandler<Command, IMessage> {
    @Override
    public IMessage onMessage(Command message, MessageContext ctx) {
	//System.out.println("Got command at: (" + message.dimension + ") " + message.x + "," + message.y + "," + message.z + " -- Command: " + message.command);
	WorldServer world = DimensionManager.getWorld(message.dimension);
	if (world != null) {
	    TileEntity entity = world.getTileEntity(message.x, message.y, message.z);
	    if ((entity != null) && (entity instanceof ICommandReceiver)) {
		((ICommandReceiver) entity).executeCommand((World) world, message.x, message.y, message.z, message.command);
	    }
	}
	return null; // no response in this case
    }
}
