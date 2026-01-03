package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityLevel;
import com.modding.forge.capability.provider.CapabilityLevelProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityLevelPacket extends PacketHandler<CapabilityLevelPacket>
{
	private NBTTagCompound data;
	private int entityID;
	
	public CapabilityLevelPacket() {}
	public CapabilityLevelPacket(int entityID, NBTTagCompound data)
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
		buf.writeInt(entityID);
		ByteBufUtils.writeTag(buf, data);
	}
	
	@Override
	public void handlerClient(CapabilityLevelPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			EntityLivingBase entity = (EntityLivingBase)player.world.getEntityByID(message.entityID);
			if(entity != null)
			{
				CapabilityLevel capability = entity.getCapability(CapabilityLevelProvider.ENTITY_LEVEL_CAP, null);
				if(capability != null && message.data != null) entity.deserializeNBT(message.data);
			}
		}
	}
	
	@Override
	public void handlerServer(CapabilityLevelPacket message, EntityPlayer player)
	{
		
	}
}
