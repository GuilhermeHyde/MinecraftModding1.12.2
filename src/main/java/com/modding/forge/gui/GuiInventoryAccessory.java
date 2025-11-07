package com.modding.forge.gui;

import org.lwjgl.opengl.GL11;

import com.modding.forge.containers.ContainerInventoryAccessory;
import com.modding.forge.containers.inventory.IInventoryAccessory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryAccessory extends GuiContainer
{
	public static final ResourceLocation TEXTURE = new ResourceLocation("elders_reborn", "textures/gui/player_inventory.png");
	
	public GuiInventoryAccessory(IInventoryAccessory inventory, EntityPlayer player)
	{
		super(new ContainerInventoryAccessory(inventory, player));
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor3d(1.0D, 1.0D, 1.0D);
		this.mc.renderEngine.bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		GuiInventory.drawEntityOnScreen(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - mouseX, (float)(this.guiTop + 75 - 50) - mouseY, this.mc.player);
	}
}
