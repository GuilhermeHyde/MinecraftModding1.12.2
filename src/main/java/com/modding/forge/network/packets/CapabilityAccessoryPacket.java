package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.provider.CapabilityAccessoryProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityAccessoryPacket extends PacketHandler<CapabilityAccessoryPacket>
{
	private NBTTagCompound data;
	
	public CapabilityAccessoryPacket(){}
	public CapabilityAccessoryPacket(NBTTagCompound data)
	{
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, data);
	}

	@Override
	public void handlerClient(CapabilityAccessoryPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			CapabilityAccessory cap = player.getCapability(CapabilityAccessoryProvider.INVENTORY_ACCESSORY_CAP, null);
			if(cap != null && message.data != null) cap.deserializeNBT(message.data);
		}
	}
	
	@Override
	public void handlerServer(CapabilityAccessoryPacket message, EntityPlayer player)
	{
		
	}
}
