package com.modding.forge.blocks.gui;

import com.modding.forge.Reference;
import com.modding.forge.blocks.containers.ContainerFusionFurnace;
import com.modding.forge.blocks.tilentities.TileEntityFusionFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class FusionFurnaceGui extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/fusion_furnace_gui.png");
	private final InventoryPlayer player;
	private final TileEntityFusionFurnace tileEntity;
	
	public FusionFurnaceGui(InventoryPlayer inventory, TileEntityFusionFurnace tileEntity)
	{
		super(new ContainerFusionFurnace(inventory, tileEntity));
		this.player = inventory;
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		int value = this.tileEntity.getField(4);
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 112, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(value + "°C", 45, 72, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(TileEntityFusionFurnace.isHeating(tileEntity))
		{
			int j = this.getHeatingBarScaled(24);
			this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 52, 3, 167, j + 1, 18);
		}
		
		int k = this.getBurnLeftScaled(13);
		this.drawTexturedModalRect(this.guiLeft + 51, this.guiTop + 25 + 13 - k, 176, 13 - k, 14, k + 1);
		
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 93, this.guiTop + 24, 176, 14, l + 1, 16);
	}
	
	private int getHeatingBarScaled(int pixels)
	{
		int i = this.tileEntity.getField(4);
		int j = this.tileEntity.getField(5);
		j = j / 118 * pixels;
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileEntity.getField(3);
		if(i == 0) i = 200;
		return this.tileEntity.getField(1) * pixels / i;
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.tileEntity.getField(0);
		int j = this.tileEntity.getField(2);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
