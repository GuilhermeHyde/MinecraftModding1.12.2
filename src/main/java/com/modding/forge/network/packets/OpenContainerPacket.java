package com.modding.forge.network.packets;

import com.modding.forge.Main;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class OpenContainerPacket extends PacketHandler<OpenContainerPacket>
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

	@Override
	public void handlerClient(OpenContainerPacket message, EntityPlayer player)
	{
		
	}

	@Override
	public void handlerServer(OpenContainerPacket message, EntityPlayer player)
	{
		if(message.id == -1)
		{
			player.openContainer.onContainerClosed(player);
			player.openContainer = player.inventoryContainer;
		}
		
		player.openGui(Main.instance, message.id, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
	}
}
