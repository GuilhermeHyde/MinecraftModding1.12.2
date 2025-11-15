package com.modding.forge.capability.provider;

import com.modding.forge.capability.CapabilityAccessory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityAccessoryProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityAccessory.class)
	public static final Capability<CapabilityAccessory> INVENTORY_ACCESSORY_CAP = null;
	private CapabilityAccessory instance = new CapabilityAccessory();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == INVENTORY_ACCESSORY_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == INVENTORY_ACCESSORY_CAP ? INVENTORY_ACCESSORY_CAP.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)INVENTORY_ACCESSORY_CAP.getStorage().writeNBT(INVENTORY_ACCESSORY_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		INVENTORY_ACCESSORY_CAP.getStorage().readNBT(INVENTORY_ACCESSORY_CAP, instance, null, nbt);
	}
}
