package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityStats;
import com.modding.forge.capability.provider.CapabilityStatsProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityStatsPacket extends PacketHandler<CapabilityStatsPacket>
{
	private NBTTagCompound data;
	private int entityID;
	
	public CapabilityStatsPacket() {}
	public CapabilityStatsPacket(int entityID, NBTTagCompound data)
	{
		this.entityID = entityID;
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityID = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityID);
		ByteBufUtils.writeTag(buf, this.data);
	}

	@Override
	public void handlerClient(CapabilityStatsPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			EntityLivingBase target = (EntityLivingBase)player.world.getEntityByID(message.entityID);
			if(target != null)
			{
				CapabilityStats cap = target.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
				if(cap != null && message.data != null) cap.deserializeNBT(message.data);
			}
		}
	}
	
	@Override
	public void handlerServer(CapabilityStatsPacket message, EntityPlayer player)
	{
		
	}
}
