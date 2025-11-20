package com.modding.forge.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class PacketHandler<Packet extends IMessage> implements IMessage, IMessageHandler<Packet, Packet>
{
	public Packet onMessage(Packet message, MessageContext ctx)
	{
		if(ctx.side == Side.CLIENT) this.handlerClient(message, Minecraft.getMinecraft().player);
		else if (ctx.side == Side.SERVER) this.handlerServer(message, ctx.getServerHandler().player);
		return null;
	}
	
	public abstract void handlerClient(Packet message, EntityPlayer player);
	public abstract void handlerServer(Packet message, EntityPlayer player);
}
