package com.paulkimbrel.archcraft.messaging;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Command implements IMessage {
    public String command = "";
    public int dimension = 0;
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public Command() {
    }

    public Command(int dimension, int x, int y, int z, String command) {
	this.command = command;
	this.dimension = dimension;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
	String rawCommand = ByteBufUtils.readUTF8String(buf);
	String[] parse = rawCommand.split(":");
	if (parse.length == 5) {
	    dimension = Integer.parseInt(parse[0]);
	    x = Integer.parseInt(parse[1]);
	    y = Integer.parseInt(parse[2]);
	    z = Integer.parseInt(parse[3]);
	    command = parse[4];
	} else {
	    command = parse[0];
	}
    }

    @Override
    public void toBytes(ByteBuf buf) {
	ByteBufUtils.writeUTF8String(buf, dimension + ":" + x + ":" + y + ":" + z + ":" + command);
    }

}
