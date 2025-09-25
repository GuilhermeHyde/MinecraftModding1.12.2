package com.modding.forge;

import net.minecraft.util.ResourceLocation;

public class Reference
{
	public static final String MOD_ID = "elders_reborn";
	public static final String NAME = "Elders Reborn";
	public static final String VERSION = "1.0";
	public static final String CLIENT_PROXY = "com.modding.forge.proxy.ClientProxy";
	public static final String COMMON_PROXY = "com.modding.forge.proxy.CommonProxy";
	public static final int FUSION_FURNACE_GUI = 1;
	
	public static ResourceLocation location(String location)
	{
		return new ResourceLocation(Reference.MOD_ID, location);
	}
	
	public static String modID()
	{
		return Reference.MOD_ID + ":";
	}
}
