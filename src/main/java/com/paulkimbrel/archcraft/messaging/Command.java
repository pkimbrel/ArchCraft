package com.paulkimbrel.archcraft.messaging;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Command implements IMessage {
    public String command = "";
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public Command() {
    }

    public Command(int x, int y, int z, String command) {
	this.command = command;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
	String rawCommand = ByteBufUtils.readUTF8String(buf);
	System.out.println("rawCommand: " + rawCommand);
	String[] parse = rawCommand.split(":");
	if (parse.length == 4) {
	    x = Integer.parseInt(parse[0]);
	    y = Integer.parseInt(parse[1]);
	    z = Integer.parseInt(parse[2]);
	    command = parse[3];
	} else {
	    command = parse[0];
	}
    }

    @Override
    public void toBytes(ByteBuf buf) {
	ByteBufUtils.writeUTF8String(buf, x + ":" + y + ":" + z + ":" + command);
    }

}
