package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityStats;
import com.modding.forge.capability.provider.CapabilityStatsProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityStatsPacket extends PacketHandler<CapabilityStatsPacket>
{
	private NBTTagCompound data;
	//private int entityID;
	
	public CapabilityStatsPacket() {}
	public CapabilityStatsPacket(NBTTagCompound data)
	{
		this.data = data;
		//this.entityID = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//this.entityID = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		//buf.writeInt(entityID);
		ByteBufUtils.writeTag(buf, this.data);
	}

	@Override
	public void handlerClient(CapabilityStatsPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			CapabilityStats cap = player.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
			if(cap != null) cap.deserializeNBT(message.data);
		}
	}
	
	@Override
	public void handlerServer(CapabilityStatsPacket message, EntityPlayer player)
	{
		
	}
}
