package com.modding.forge.network.packets;

import com.modding.forge.capability.provider.EntityStatsProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EntityLevelPacket implements IMessage
{
	private NBTTagCompound data;
	private int id;
	
	public EntityLevelPacket() {}
	public EntityLevelPacket(int id, NBTTagCompound nbt)
	{
		this.data = nbt;
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
	
	public static class EntityLevelHandler implements IMessageHandler<EntityLevelPacket, IMessage>
	{
		@Override
		public IMessage onMessage(EntityLevelPacket message, MessageContext ctx)
		{
			if(ctx.side.isClient())
			{
				Minecraft.getMinecraft().addScheduledTask(() ->
				{
					Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.id);
					if(entity != null)
					{
						entity.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
						entity.deserializeNBT(message.data);
					}
				});
			}
			return null;
		}
	}
}
