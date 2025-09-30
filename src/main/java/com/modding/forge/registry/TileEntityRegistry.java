package com.modding.forge.registry;

import com.modding.forge.Reference;
import com.modding.forge.blocks.tilentities.TileEntityFusionFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry
{
	public static void register()
	{
		GameRegistry.registerTileEntity(TileEntityFusionFurnace.class, new ResourceLocation(Reference.MOD_ID + ":fusion_furnace"));
	}
}
