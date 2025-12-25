package com.modding.forge.capability.provider;

import com.modding.forge.capability.CapabilityEquipment;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityEquipmentProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityEquipment.class)
	public static final Capability<CapabilityEquipment> EQUIPMENT_ATTRIBUTE_CAP = null;
	private CapabilityEquipment instance = new CapabilityEquipment();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == EQUIPMENT_ATTRIBUTE_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == EQUIPMENT_ATTRIBUTE_CAP ? EQUIPMENT_ATTRIBUTE_CAP.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)EQUIPMENT_ATTRIBUTE_CAP.getStorage().writeNBT(EQUIPMENT_ATTRIBUTE_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		EQUIPMENT_ATTRIBUTE_CAP.getStorage().readNBT(EQUIPMENT_ATTRIBUTE_CAP, instance, null, nbt);
	}
}
