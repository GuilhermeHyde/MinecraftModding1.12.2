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

public class EntityStatsPacket implements IMessage
{
	private NBTTagCompound data;
	private int entityID;
	
	public EntityStatsPacket() {}
	public EntityStatsPacket(int id, NBTTagCompound nbt)
	{
		this.data = nbt;
		this.entityID = id;
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
	
	public static class EntityStatsHandler implements IMessageHandler<EntityStatsPacket, IMessage>
	{
		@Override
		public IMessage onMessage(EntityStatsPacket message, MessageContext ctx)
		{
			if(ctx.side.isClient())
			{
				Minecraft.getMinecraft().addScheduledTask(() ->
				{
					Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
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
