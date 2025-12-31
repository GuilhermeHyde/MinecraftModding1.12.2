package com.modding.forge.capability.provider;

import com.modding.forge.capability.CapabilityAttribute;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityAttributeProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityAttribute.class)
	public static final Capability<CapabilityAttribute> ACCESSORY_ATTRIBUTES_CAP = null;
	private CapabilityAttribute instance = new CapabilityAttribute();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == ACCESSORY_ATTRIBUTES_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == ACCESSORY_ATTRIBUTES_CAP ? ACCESSORY_ATTRIBUTES_CAP.cast(this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return this.instance.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		if(nbt != null) this.instance.deserializeNBT(nbt);
	}
}
