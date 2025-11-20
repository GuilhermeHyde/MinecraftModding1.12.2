package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.provider.CapabilityAccessoryProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityAccessoryPacket extends PacketHandler<CapabilityAccessoryPacket>
{
	private NBTTagCompound data;
	private int id;

	public CapabilityAccessoryPacket(){}
	public CapabilityAccessoryPacket(int id, NBTTagCompound data)
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
	public void handlerClient(CapabilityAccessoryPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			EntityPlayer entity = (EntityPlayer)player.world.getEntityByID(message.id);
			if(entity != null)
			{
				CapabilityAccessory capability = entity.getCapability(CapabilityAccessoryProvider.INVENTORY_ACCESSORY_CAP, null);
				if(capability != null) capability.deserializeNBT(message.data);
			}
		}
	}
	
	@Override
	public void handlerServer(CapabilityAccessoryPacket message, EntityPlayer player)
	{
		
	}
}
