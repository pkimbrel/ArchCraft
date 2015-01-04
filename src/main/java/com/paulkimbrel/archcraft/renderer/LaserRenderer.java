package com.paulkimbrel.archcraft.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.paulkimbrel.archcraft.blocks.Builder;
import com.paulkimbrel.archcraft.core.SpatialTileEntity;
import com.paulkimbrel.archcraft.entities.BuilderEntity;
import com.paulkimbrel.archcraft.entities.LaserEntity;

public class LaserRenderer extends Render {
    @Override
    public void doRender(Entity entity,
	    double x,
	    double y,
	    double z,
	    float f1,
	    float f2) {

	LaserEntity laserEntity = (LaserEntity) entity;

	SpatialTileEntity builderEntity = (SpatialTileEntity) entity.worldObj.getTileEntity(laserEntity.builderX, laserEntity.builderY, laserEntity.builderZ);
	if (laserEntity.show == false || builderEntity == null) {
	    return;
	}
	
	Tessellator tessellator = Tessellator.instance;
	GL11.glPushMatrix();
	GL11.glTranslated(x - laserEntity.offsetX, y - laserEntity.offsetY, z - laserEntity.offsetZ); // This is necessary to make our rendering

	ResourceLocation textures = (new ResourceLocation("archcraft:textures/render/laser.png"));
	Minecraft.getMinecraft().renderEngine.bindTexture(textures);
	
	int width = builderEntity.width - 1;
	int depth = builderEntity.depth - 1;
	int height = builderEntity.height - 1;

	int meta = entity.worldObj.getBlockMetadata(laserEntity.builderX, laserEntity.builderY, laserEntity.builderZ);
	
	GL11.glRotatef(meta * -90, 0.0f, 1.0f, 0.0f);
	GL11.glTranslated(0.0f, 0.0f, 1.0f); 
	
	// Depth lasers
	renderLaser(tessellator, depth);
	GL11.glTranslated(-1 * width, 0.0f, 0.0f); 
	renderLaser(tessellator, depth);
	GL11.glTranslated(0.0f, height, 0.0f); 
	renderLaser(tessellator, depth);
	GL11.glTranslated(width, 0.0f, 0.0f); 
	renderLaser(tessellator, depth);
	GL11.glTranslated(0.0f, -1 * height, 0.0f);

	// Width lasers
	GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
	renderLaser(tessellator, width);
	GL11.glTranslated(depth, 0.0f, 0.0f); 
	renderLaser(tessellator, width);
	GL11.glTranslated(0.0f, height, 0.0f); 
	renderLaser(tessellator, width);
	GL11.glTranslated(-1 * depth, 0.0f, 0.0f);
	renderLaser(tessellator, width);
	GL11.glTranslated(0.0f, -1 * height, 0.0f);

	// Height Lasers
	GL11.glRotatef(-90, 1.0f, 0.0f, 0.0f);
	renderLaser(tessellator, height);
	GL11.glTranslated(depth, 0.0f, 0.0f);
	renderLaser(tessellator, height);
	GL11.glTranslated(0.0f, -1 * width, 0.0f);
	renderLaser(tessellator, height);
	GL11.glTranslated(-1 * depth, 0.0f, 0.0f);
	renderLaser(tessellator, height);

	GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
	return new ResourceLocation("archcraft:textures/render/laser.png");
    }

    private void renderLaser(Tessellator tessellator, int length) {
	GL11.glPushMatrix();
	tessellator.startDrawingQuads();
	float pw = .04f;
	float nw = -1 * pw;
	tessellator.addVertexWithUV(pw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(pw, pw, 0, 0, 1);
	tessellator.addVertexWithUV(pw, pw, length, length, 1);
	tessellator.addVertexWithUV(pw, nw, length, length, 0);

	tessellator.addVertexWithUV(nw, pw, length, 0, 0);
	tessellator.addVertexWithUV(pw, pw, length, 0, 1);
	tessellator.addVertexWithUV(pw, pw, 0, 20, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 20, 0);

	tessellator.addVertexWithUV(nw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(nw, nw, length, length, 0);
	tessellator.addVertexWithUV(nw, pw, length, length, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 0, 1);

	tessellator.addVertexWithUV(pw, nw, length, length, 0);
	tessellator.addVertexWithUV(nw, nw, length, length, 1);
	tessellator.addVertexWithUV(nw, nw, 0, 1, 1);
	tessellator.addVertexWithUV(pw, nw, 0, 1, 0);

	tessellator.addVertexWithUV(pw, nw, 0, 0, 0);
	tessellator.addVertexWithUV(nw, nw, 0, 0, 1);
	tessellator.addVertexWithUV(nw, pw, 0, 1, 1);
	tessellator.addVertexWithUV(pw, pw, 0, 1, 0);

	tessellator.addVertexWithUV(nw, pw, length, 0, 0);
	tessellator.addVertexWithUV(nw, nw, length, 0, 1);
	tessellator.addVertexWithUV(pw, nw, length, 1, 1);
	tessellator.addVertexWithUV(pw, pw, length, 1, 0);
	tessellator.draw();
	GL11.glPopMatrix();
    }

}
