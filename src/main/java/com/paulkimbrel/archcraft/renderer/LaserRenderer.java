package com.paulkimbrel.archcraft.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LaserRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
	Tessellator tessellator = Tessellator.instance;
	// Using glPushMatrix before doing our rendering, and then using
	// glPopMatrix at the end means any "transformation"
	// that we do (glTranslated, glRotated, et c.) does not screw up
	// rendering in an unrelated part of the game.
	GL11.glPushMatrix();
	GL11.glTranslated(x, y, z); // This is necessary to make our rendering
				    // happen in the right place.
	ResourceLocation textures = (new ResourceLocation("archcraft:textures/render/laser.png"));
	// the ':' is very important
	// binding the textures
	Minecraft.getMinecraft().renderEngine.bindTexture(textures);

	tessellator.startDrawingQuads();
	tessellator.addVertexWithUV(.56, .44, 0, 0, 0);
	tessellator.addVertexWithUV(.56, .56, 0, 0, 1);
	tessellator.addVertexWithUV(.56, .56, 20, 1, 1);
	tessellator.addVertexWithUV(.56, .44, 20, 1, 0);

	tessellator.addVertexWithUV(.44, .56, 20, 0, 0);
	tessellator.addVertexWithUV(.56, .56, 20, 0, 1);
	tessellator.addVertexWithUV(.56, .56, 0, 1, 1);
	tessellator.addVertexWithUV(.44, .56, 0, 1, 0);
	
	tessellator.addVertexWithUV(.44, .44, 0, 0, 0);
	tessellator.addVertexWithUV(.44, .44, 20, 1, 0);
	tessellator.addVertexWithUV(.44, .56, 20, 1, 1);
	tessellator.addVertexWithUV(.44, .56, 0, 0, 1);

	tessellator.addVertexWithUV(.56, .44, 20, 0, 0);
	tessellator.addVertexWithUV(.44, .44, 20, 0, 1);
	tessellator.addVertexWithUV(.44, .44, 0, 1, 1);
	tessellator.addVertexWithUV(.56, .44, 0, 1, 0);
	
	tessellator.addVertexWithUV(.56, .44, 0, 0, 0);
	tessellator.addVertexWithUV(.44, .44, 0, 0, 1);
	tessellator.addVertexWithUV(.44, .56, 0, 1, 1);
	tessellator.addVertexWithUV(.56, .56, 0, 1, 0);
	
	tessellator.addVertexWithUV(.44, .56, 20, 0, 0);
	tessellator.addVertexWithUV(.44, .44, 20, 0, 1);
	tessellator.addVertexWithUV(.56, .44, 20, 1, 1);
	tessellator.addVertexWithUV(.56, .56, 20, 1, 0);
	
	tessellator.draw();
	GL11.glPopMatrix();
    }

}
