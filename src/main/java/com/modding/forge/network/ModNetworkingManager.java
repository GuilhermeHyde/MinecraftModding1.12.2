package com.modding.forge.network;

import com.modding.forge.Reference;
import com.modding.forge.network.packets.CapabilityAccessoryPacket;
import com.modding.forge.network.packets.CapabilityLevelPacket;
import com.modding.forge.network.packets.CapabilityStatsPacket;
import com.modding.forge.network.packets.OpenContainerPacket;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetworkingManager
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	private static int packetID;
	
	public static void initialization()
	{
		INSTANCE.registerMessage(CapabilityStatsPacket.class, CapabilityStatsPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(CapabilityLevelPacket.class, CapabilityLevelPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(CapabilityAccessoryPacket.class, CapabilityAccessoryPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(OpenContainerPacket.class, OpenContainerPacket.class, packetID++, Side.SERVER);
	}
}
