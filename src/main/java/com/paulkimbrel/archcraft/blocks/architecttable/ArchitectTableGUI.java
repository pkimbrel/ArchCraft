package com.paulkimbrel.archcraft.blocks.architecttable;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;

import org.lwjgl.opengl.GL11;

import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.blocks.builder.BuilderEntity;
import com.paulkimbrel.archcraft.blocks.builder.BuilderGUIContainer;
import com.paulkimbrel.archcraft.messaging.Command;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ArchitectTableGUI extends GuiContainer {
    private int rootX;
    private int rootY;
    private ArchitectTableEntity tileEntity;

    public ArchitectTableGUI(InventoryPlayer inventoryPlayer, ArchitectTableEntity tileEntity) {
	super(new BuilderGUIContainer(inventoryPlayer, tileEntity));
	this.tileEntity = tileEntity;
    }

    @Override
    public void initGui() {
	super.initGui();
	rootX = (width - xSize) / 2;
	rootY = (height - ySize) / 2;
	buttonList.add(new GuiButton(1, rootX + 12, rootY + 14, 40, 20, "Test"));
	buttonList.add(new GuiButton(2, rootX + 12, rootY + 33, 50, 20, "Dungeon"));
	buttonList.add(new GuiButton(3, rootX + 12, rootY + 52, 40, 20, "Clear"));
	buttonList.add(new GuiButton(11, rootX + xSize - 51, rootY + 14, 20, 20, "-"));
	buttonList.add(new GuiButton(12, rootX + xSize - 32, rootY + 14, 20, 20, "+"));
	buttonList.add(new GuiButton(13, rootX + xSize - 51, rootY + 33, 20, 20, "-"));
	buttonList.add(new GuiButton(14, rootX + xSize - 32, rootY + 33, 20, 20, "+"));
	buttonList.add(new GuiButton(15, rootX + xSize - 51, rootY + 52, 20, 20, "-"));
	buttonList.add(new GuiButton(16, rootX + xSize - 32, rootY + 52, 20, 20, "+"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
	String command = "";
	switch (button.id) {
	    case 1:
		command = "testPattern1";
		break;
	    case 2:
		command = "testDungeon1";
		break;
	    case 3:
		command = "clearTestPattern1";
		break;
	    case 11:
		command = "widthDown";
		break;
	    case 12:
		command = "widthUp";
		break;
	    case 13:
		command = "heightDown";
		break;
	    case 14:
		command = "heightUp";
		break;
	    case 15:
		command = "depthDown";
		break;
	    case 16:
		command = "depthUp";
		break;
	}
	if (!"".equals(command)) {
		Main.network.sendToServer(new Command(tileEntity.getWorldObj().provider.dimensionId,
			tileEntity.xCoord,
			tileEntity.yCoord,
			tileEntity.zCoord,
			command));
	}
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	// draw text and stuff here
	// the parameters for drawString are: string, x, y, color
	fontRendererObj.drawString(StatCollector.translateToLocal("tile.builder.name"), 8, 6, 4210752);
	// draws "Inventory" or your regional equivalent
	fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	// draw your Gui here, only thing you need to change is the path
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	this.mc.renderEngine.bindTexture(new ResourceLocation(Main.MODID, "textures/gui/stuff.png"));
	this.drawTexturedModalRect(rootX, rootY, 0, 0, xSize, ySize);
    }
}
