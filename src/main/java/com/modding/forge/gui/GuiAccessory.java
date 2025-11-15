package com.modding.forge.gui;

import org.lwjgl.opengl.GL11;

import com.modding.forge.Reference;
import com.modding.forge.containers.ContainerAccessory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAccessory extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/player_inventory.png");
	
	public GuiAccessory(EntityPlayer player)
	{
		super(new ContainerAccessory(player));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor3d(1.0D, 1.0D, 1.0D);
		this.mc.renderEngine.bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.width / 2 - 88, this.height / 2 - 166 / 2, 0, 0, 176, 166);
		GuiInventory.drawEntityOnScreen(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - mouseX, (float)(this.guiTop + 75 - 50) - mouseY, this.mc.player);
	}
}
