package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityAttribute;
import com.modding.forge.capability.provider.CapabilityAttributeProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityAttributePacket extends PacketHandler<CapabilityAttributePacket>
{
	private NBTTagCompound data;
	private int index;
	
	public CapabilityAttributePacket() {}
	public CapabilityAttributePacket(int index, NBTTagCompound data)
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
		buf.writeInt(index);
		ByteBufUtils.writeTag(buf, data);
	}

	@Override
	public void handlerClient(CapabilityAttributePacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
	        Container container = player.openContainer;
	        if(container != null && message.index >= 0 && message.index < container.inventorySlots.size())
	        {
	            ItemStack stack = container.getSlot(message.index).getStack();
	            if(!stack.isEmpty() && message.data != null)
	            {
	                CapabilityAttribute cap = stack.getCapability(CapabilityAttributeProvider.ACCESSORY_ATTRIBUTES_CAP, null);
	                if(cap != null) cap.deserializeNBT(message.data);
	            }
	        }
		}
	}

	@Override
	public void handlerServer(CapabilityAttributePacket message, EntityPlayer player)
	{
		
	}
}
