package com.paulkimbrel.archcraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.DimensionManager;

import org.lwjgl.opengl.GL11;

import com.paulkimbrel.archcraft.Main;
import com.paulkimbrel.archcraft.blocks.BuilderContainer;
import com.paulkimbrel.archcraft.entities.BuilderEntity;
import com.paulkimbrel.archcraft.messaging.Command;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderGUI extends GuiContainer {
    private int rootX;
    private int rootY;
    private BuilderEntity tileEntity;

    public BuilderGUI(InventoryPlayer inventoryPlayer, BuilderEntity tileEntity) {
	super(new BuilderContainer(inventoryPlayer, tileEntity));
	this.tileEntity = tileEntity;
    }

    @Override
    public void initGui() {
	super.initGui();
	rootX = (width - xSize) / 2;
	rootY = (height - ySize) / 2;
	buttonList.add(new GuiButton(1, rootX + 12, rootY + 23, 40, 20, "Test"));
	buttonList.add(new GuiButton(2, rootX + 12, rootY + 42, 40, 20, "Clear"));
	buttonList.add(new GuiButton(3, rootX + xSize - 51, rootY + 14, 20, 20, "-"));
	buttonList.add(new GuiButton(4, rootX + xSize - 32, rootY + 14, 20, 20, "+"));
	buttonList.add(new GuiButton(5, rootX + xSize - 51, rootY + 33, 20, 20, "-"));
	buttonList.add(new GuiButton(6, rootX + xSize - 32, rootY + 33, 20, 20, "+"));
	buttonList.add(new GuiButton(7, rootX + xSize - 51, rootY + 52, 20, 20, "-"));
	buttonList.add(new GuiButton(8, rootX + xSize - 32, rootY + 52, 20, 20, "+"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
	String command = "";
	switch (button.id) {
	    case 1:
		command = "testPattern1";
		break;
	    case 2:
		command = "clearTestPattern1";
		break;
	    case 3:
		command = "widthDown";
		break;
	    case 4:
		command = "widthUp";
		break;
	    case 5:
		command = "heightDown";
		break;
	    case 6:
		command = "heightUp";
		break;
	    case 7:
		command = "depthDown";
		break;
	    case 8:
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
