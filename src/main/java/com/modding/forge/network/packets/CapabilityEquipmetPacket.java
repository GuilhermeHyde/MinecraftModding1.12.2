package com.modding.forge.network.packets;

import com.modding.forge.capability.CapabilityEquipment;
import com.modding.forge.capability.provider.CapabilityEquipmentProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CapabilityEquipmetPacket extends PacketHandler<CapabilityEquipmetPacket>
{
	private NBTTagCompound data;
	private int index;
	
	public CapabilityEquipmetPacket(){}
	public CapabilityEquipmetPacket(int index, NBTTagCompound data)
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
	public void handlerClient(CapabilityEquipmetPacket message, EntityPlayer player)
	{
		if(player != null && player.world != null)
		{
			ItemStack stack = player.inventory.getStackInSlot(message.index);
			if(!stack.isEmpty())
			{
				CapabilityEquipment capability = stack.getCapability(CapabilityEquipmentProvider.EQUIPMENT_ATTRIBUTE_CAP, null);
				if(capability != null) CapabilityEquipmentProvider.EQUIPMENT_ATTRIBUTE_CAP.getStorage().readNBT(CapabilityEquipmentProvider.EQUIPMENT_ATTRIBUTE_CAP, capability, null, message.data);
			}
		}
	}

	@Override
	public void handlerServer(CapabilityEquipmetPacket message, EntityPlayer player)
	{
		
	}
}