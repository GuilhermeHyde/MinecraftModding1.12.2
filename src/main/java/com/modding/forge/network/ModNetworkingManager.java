package com.modding.forge.network;

import com.modding.forge.Reference;
import com.modding.forge.network.packets.EntityStatsPacket;
import com.modding.forge.network.packets.EntityStatsPacket.EntityStatsHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetworkingManager
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	private static int packetID;
	
	public static void initialization()
	{
		INSTANCE.registerMessage(EntityStatsHandler.class, EntityStatsPacket.class, packetID++, Side.CLIENT);
	}
}
