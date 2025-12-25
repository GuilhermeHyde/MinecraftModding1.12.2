package com.modding.forge.gui;

import com.modding.forge.Reference;
import com.modding.forge.blocks.containers.ContainerFusionFurnace;
import com.modding.forge.blocks.gui.FusionFurnaceGui;
import com.modding.forge.blocks.tilentities.TileEntityFusionFurnace;
import com.modding.forge.containers.ContainerAccessory;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.FUSION_FURNACE_GUI) return new ContainerFusionFurnace(player.inventory, (TileEntityFusionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == Reference.INVENTORY_ACCESSORY) return new ContainerAccessory(player);
		if(ID == Reference.INVENTORY_CREATIVE) return new GuiContainerCreative.ContainerCreative(player);
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.FUSION_FURNACE_GUI) return new FusionFurnaceGui(player.inventory, (TileEntityFusionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == Reference.INVENTORY_ACCESSORY) return new GuiAccessory(player);
		if(ID == Reference.INVENTORY_CREATIVE) return new GuiContainerCreative(player);
		return null;
	}
}
