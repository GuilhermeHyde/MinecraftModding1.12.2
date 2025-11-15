package com.modding.forge.network.packets;

import com.modding.forge.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenContainerPacket implements IMessage
{
	public int id;

	public OpenContainerPacket()
	{
		
	}

	public OpenContainerPacket(int id)
	{
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(id);
	}
	
	public static class OpenContainerHandler implements IMessageHandler<OpenContainerPacket, IMessage>
	{
		@Override
		public IMessage onMessage(OpenContainerPacket message, MessageContext ctx)
		{
			if(ctx.side.isServer())
			{
				Minecraft.getMinecraft().addScheduledTask(() -> 
				{
					final EntityPlayerMP player = ctx.getServerHandler().player;
					if(player != null)
					{
						if(message.id == -1)
						{
							player.openContainer.onContainerClosed(player);
							player.openContainer = player.inventoryContainer;
						}
						
						player.openGui(Main.instance, message.id, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
					}
				});
			}
			return null;
		}
	}
}
