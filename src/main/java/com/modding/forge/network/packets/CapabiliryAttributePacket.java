package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityAttribute;
import com.modding.forge.capability.provider.CapabilityAttributeProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabiliryAttributePacket extends PacketHandler<CapabiliryAttributePacket>
{
	private NBTTagCompound data;
	private int index;
	
	public CapabiliryAttributePacket() {}
	public CapabiliryAttributePacket(int index, NBTTagCompound data)
	{
		this.data = data;
		this.index = index;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.index = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(index);
		ByteBufUtils.writeTag(buf, data);
	}

	@Override
	public void handlerClient(CapabiliryAttributePacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			ItemStack stack = player.inventory.getStackInSlot(message.index);
			if(!stack.isEmpty())
			{
				CapabilityAttribute cap = stack.getCapability(CapabilityAttributeProvider.ACCESSORY_ATTRIBUTES_CAP, null);
				if(cap != null) cap.deserializeNBT(message.data);
			}
		}
	}

	@Override
	public void handlerServer(CapabiliryAttributePacket message, EntityPlayer player)
	{
		
	}
}
