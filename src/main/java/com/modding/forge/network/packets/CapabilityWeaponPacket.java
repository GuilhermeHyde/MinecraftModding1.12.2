package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityWeapon;
import com.modding.forge.capability.provider.CapabilityWeaponProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityWeaponPacket extends PacketHandler<CapabilityWeaponPacket>
{
	private NBTTagCompound data;
	private int index;
	
	public CapabilityWeaponPacket(){}
	public CapabilityWeaponPacket(int index, NBTTagCompound data)
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
	public void handlerClient(CapabilityWeaponPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
	        Container container = player.openContainer;
	        if(container != null && message.index >= 0 && message.index < container.inventorySlots.size())
	        {
	            ItemStack stack = container.getSlot(message.index).getStack();
	            if(!stack.isEmpty() && message.data != null)
	            {
	                CapabilityWeapon cap = stack.getCapability(CapabilityWeaponProvider.WEAPON_ATTRIBUTE_CAP, null);
	                if(cap != null) cap.deserializeNBT(message.data);
	            }
	        }
		}
	}

	@Override
	public void handlerServer(CapabilityWeaponPacket message, EntityPlayer player)
	{
		
	}
}