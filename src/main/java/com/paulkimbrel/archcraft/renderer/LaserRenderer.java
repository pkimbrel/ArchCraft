package com.paulkimbrel.archcraft.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class LaserRenderer extends TileEntitySpecialRenderer {
    float angle = 0;
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
	
	Tessellator tessellator = Tessellator.instance;

	GL11.glPushMatrix();
	GL11.glTranslated(x + .5f, y + .5f, z + .5f); // This is necessary to make our rendering
	ResourceLocation textures = (new ResourceLocation("archcraft:textures/render/laser.png"));
	Minecraft.getMinecraft().renderEngine.bindTexture(textures);
	
	int meta = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
	GL11.glRotatef(meta * -90, 0.0f, 1.0f, 0.0f);
	GL11.glTranslated(0.0f, 0.0f, 1.0f); // This is necessary to make our rendering
	renderLaser(tessellator);
	
	GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
	renderLaser(tessellator);

	GL11.glRotatef(-90, 1.0f, 0.0f, 0.0f);
	renderLaser(tessellator);
	
	GL11.glPopMatrix();
    }

    private void renderLaser(Tessellator tessellator) {
	GL11.glPushMatrix();
	tessellator.startDrawingQuads();
	float pw = .04f;
	float nw = -1 * pw;
	tessellator.addVertexWithUV(pw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(pw, pw, 0, 0, 1);
	tessellator.addVertexWithUV(pw, pw, 19, 20, 1);
	tessellator.addVertexWithUV(pw, nw, 19, 20, 0);

	tessellator.addVertexWithUV(nw, pw, 19, 0, 0);
	tessellator.addVertexWithUV(pw, pw, 19, 0, 1);
	tessellator.addVertexWithUV(pw, pw, 0, 20, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 20, 0);
	
	tessellator.addVertexWithUV(nw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(nw, nw, 19, 20, 0);
	tessellator.addVertexWithUV(nw, pw, 19, 20, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 0, 1);

	tessellator.addVertexWithUV(pw, nw, 19, 20, 0);
	tessellator.addVertexWithUV(nw, nw, 19, 20, 1);
	tessellator.addVertexWithUV(nw, nw, 0, 1, 1);
	tessellator.addVertexWithUV(pw, nw, 0, 1, 0);
	
	tessellator.addVertexWithUV(pw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(nw, nw, 0, 0, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 1, 1);
	tessellator.addVertexWithUV(pw, pw, 0, 1, 0);
	
	tessellator.addVertexWithUV(nw, pw, 19, 0, 0);
	tessellator.addVertexWithUV(nw, nw, 19, 0, 1);
	tessellator.addVertexWithUV(pw, nw, 19, 1, 1);
	tessellator.addVertexWithUV(pw, pw, 19, 1, 0);
	tessellator.draw();
	GL11.glPopMatrix();
    }
}
