package com.paulkimbrel.archcraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.paulkimbrel.archcraft.messaging.Command;
import com.paulkimbrel.archcraft.messaging.CommandHandler;
import com.paulkimbrel.archcraft.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "archcraft";
    public static final String NAME = "ArchCraft";
    public static final String VERSION = "1.0";

    public static final String CLIENT_PROXY = "com.paulkimbrel.archcraft.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.paulkimbrel.archcraft.proxy.ServerProxy";

    public static final int GUI_BUILDER = 0;
    public static final int GUI_ARCHITECTTABLE = 1;
    
    public static final int MSG_COMMAND = 0;

    public static final int META_SOUTH = 0;
    public static final int META_WEST = 1;
    public static final int META_NORTH = 2;
    public static final int META_EAST = 3;

    public static final int BLOCK_SOUTH = 3;
    public static final int BLOCK_WEST = 4;
    public static final int BLOCK_NORTH = 2;
    public static final int BLOCK_EAST = 5;

    @Instance
    public static Main instance = new Main();

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    /**
     * Read your config, create blocks or items, register them with the
     * GameRegistry.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
	proxy.preInit(event);
	
	network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	network.registerMessage(CommandHandler.class, Command.class, Main.MSG_COMMAND, Side.SERVER);
    }


    /**
     * Setup mod here. Build data structures and register recipes.
     * 
     * @param event
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
	proxy.init(event);
    }

    /**
     * Interaction with other mods and complete your setup here.
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
	proxy.postInit(event);
    }

    public static CreativeTabs creativeTab = new CreativeTabs("archcraftTab") {
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
	    return proxy.squareAndCompass;
	}
    };

}
