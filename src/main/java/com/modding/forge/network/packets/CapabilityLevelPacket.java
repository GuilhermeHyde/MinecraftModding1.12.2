package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityLevel;
import com.modding.forge.capability.provider.CapabilityLevelProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityLevelPacket extends PacketHandler<CapabilityLevelPacket>
{
	private NBTTagCompound data;
	private int id;
	
	public CapabilityLevelPacket() {}
	public CapabilityLevelPacket(int id, NBTTagCompound data)
	{
		this.data = data;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(id);
		ByteBufUtils.writeTag(buf, data);
	}
	
	@Override
	public void handlerClient(CapabilityLevelPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			Entity entity = player.world.getEntityByID(message.id);
			if(entity != null)
			{
				CapabilityLevel capability = entity.getCapability(CapabilityLevelProvider.ENTITY_LEVEL_CAP, null);
				if(capability != null) entity.deserializeNBT(message.data);
			}
		}
	}
	
	@Override
	public void handlerServer(CapabilityLevelPacket message, EntityPlayer player)
	{
		
	}
}
