package com.paulkimbrel.archcraft.messaging;

import net.minecraft.block.Block;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommandHandler implements IMessageHandler<Command, IMessage> {
    @Override
    public IMessage onMessage(Command message, MessageContext ctx) {
	System.out.println(String.format("Received %s from %s at %d %d %d %d",
		message.command,
		ctx.getServerHandler().playerEntity.getDisplayName(),
		message.dimension,
		message.x,
		message.y,
		message.z));
	WorldServer world = DimensionManager.getWorld(message.dimension);
	if (world != null) {
	    Block block = world.getBlock(message.x, message.y, message.z);
	    if ((block != null) && block instanceof ICommand) {
		((ICommand) block).executeCommand(message.command);
	    }
	}
	return null; // no response in this case
    }
}
