package com.modding.forge.network;

import com.modding.forge.Reference;
import com.modding.forge.network.packets.CapabilityAccessoryPacket;
import com.modding.forge.network.packets.CapabilityAccessoryPacket.CapabilityAccessoryHandler;
import com.modding.forge.network.packets.CapabilityLevelPacket;
import com.modding.forge.network.packets.CapabilityLevelPacket.CapabilityLevelHandler;
import com.modding.forge.network.packets.CapabilityStatsPacket;
import com.modding.forge.network.packets.CapabilityStatsPacket.CapabilityStatsHandler;
import com.modding.forge.network.packets.OpenContainerPacket;
import com.modding.forge.network.packets.OpenContainerPacket.OpenContainerHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetworkingManager
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	private static int packetID;
	
	public static void initialization()
	{
		INSTANCE.registerMessage(CapabilityStatsHandler.class, CapabilityStatsPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(CapabilityLevelHandler.class, CapabilityLevelPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(CapabilityAccessoryHandler.class, CapabilityAccessoryPacket.class, packetID++, Side.CLIENT);
		INSTANCE.registerMessage(OpenContainerHandler.class, OpenContainerPacket.class, packetID++, Side.SERVER);
	}
}
