package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.provider.CapabilityAccessoryProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityAccessoryPacket implements IMessage
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
	
	public static class CapabilityAccessoryHandler implements IMessageHandler<CapabilityAccessoryPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CapabilityAccessoryPacket message, MessageContext ctx)
		{
			if(ctx.side.isClient())
			{
				Minecraft.getMinecraft().addScheduledTask(() -> 
				{
					EntityPlayerSP player = Minecraft.getMinecraft().player;
					if(player != null)
					{
						CapabilityAccessory capability = player.getCapability(CapabilityAccessoryProvider.INVENTORY_ACCESSORY_CAP, null);
						if(capability != null) capability.deserializeNBT(message.data);
					}
				});
			}
			return null;
		}
	}
}
