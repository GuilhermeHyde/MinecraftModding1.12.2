package com.modding.forge.gui;

import com.modding.forge.Reference;
import com.modding.forge.blocks.containers.ContainerFusionFurnace;
import com.modding.forge.blocks.gui.FusionFurnaceGui;
import com.modding.forge.blocks.tilentities.TileEntityFusionFurnace;
import com.modding.forge.capability.provider.CapabilityAccessoryProvider;
import com.modding.forge.containers.ContainerAccessory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.FUSION_FURNACE_GUI) return new ContainerFusionFurnace(player.inventory, (TileEntityFusionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == Reference.INVENTORY_ACCESSORY) return new ContainerAccessory(player);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == Reference.FUSION_FURNACE_GUI) return new FusionFurnaceGui(player.inventory, (TileEntityFusionFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		if(ID == Reference.INVENTORY_ACCESSORY) return new GuiAccessory(player);
		return null;
	}
}
