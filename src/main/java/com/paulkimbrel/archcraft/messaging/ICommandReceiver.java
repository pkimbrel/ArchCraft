package com.paulkimbrel.archcraft.messaging;

import net.minecraft.world.World;

public interface ICommandReceiver {
    public void executeCommand(World world, int x, int y, int z, String command);
}
