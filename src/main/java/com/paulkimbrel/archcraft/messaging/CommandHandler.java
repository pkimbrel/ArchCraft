package com.paulkimbrel.archcraft.messaging;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommandHandler implements IMessageHandler<Command, IMessage> {
    @Override
    public IMessage onMessage(Command message, MessageContext ctx) {
	System.out.println(String.format("Received %s from %s at %d %d %d",
		message.command,
		ctx.getServerHandler().playerEntity.getDisplayName(),
		message.x,
		message.y,
		message.z));
	//TODO Handle worlds
	TileEntity entity = DimensionManager.getWorld(0).getTileEntity(message.x, message.y, message.z);
	if (entity instanceof ICommand) {
	    ((ICommand)entity).executeCommand(message.command);
	}
	return null; // no response in this case
    }
}
