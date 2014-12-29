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
import com.paulkimbrel.archcraft.blocks.BuilderEntity;
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
	buttonList.add(new GuiButton(1, rootX + 12, rootY + 33, 40, 20, "Go"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
	switch (button.id) {
	    case 1:
		Main.network.sendToServer(new Command(tileEntity.getWorldObj().provider.dimensionId,
			tileEntity.xCoord,
			tileEntity.yCoord,
			tileEntity.zCoord,
			"foobar"));

		break;
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
