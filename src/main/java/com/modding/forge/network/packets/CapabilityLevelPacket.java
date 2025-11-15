package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityLevel;
import com.modding.forge.capability.provider.CapabilityLevelProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CapabilityLevelPacket implements IMessage
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
	
	public static class CapabilityLevelHandler implements IMessageHandler<CapabilityLevelPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CapabilityLevelPacket message, MessageContext ctx)
		{
			if(ctx.side.isClient())
			{
				Minecraft.getMinecraft().addScheduledTask(() ->
				{
					Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.id);
					if(entity != null)
					{
						CapabilityLevel capability = entity.getCapability(CapabilityLevelProvider.ENTITY_LEVEL_CAP, null);
						if(capability != null)entity.deserializeNBT(message.data);
					}
				});
			}
			return null;
		}
	}
}
