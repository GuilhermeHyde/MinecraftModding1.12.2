package com.modding.forge.gui;

import com.modding.forge.Reference;
import com.modding.forge.containers.ContainerAccessory;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAccessory extends GuiInventory
{
    public static final ResourceLocation INVENTORY_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/player_inventory.png");

    public GuiAccessory(EntityPlayer player)
    {
        super(player);
        this.inventorySlots = new ContainerAccessory(player);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(INVENTORY_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.mc.player);
    }
}
