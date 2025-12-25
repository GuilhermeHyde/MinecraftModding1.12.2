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
	public static final Capability<CapabilityAttribute> ITEM_ATTRIBUTE_CAP = null;
	private CapabilityAttribute instence = new CapabilityAttribute();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == ITEM_ATTRIBUTE_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == ITEM_ATTRIBUTE_CAP ? ITEM_ATTRIBUTE_CAP.cast(instence) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)ITEM_ATTRIBUTE_CAP.getStorage().writeNBT(ITEM_ATTRIBUTE_CAP, instence, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		ITEM_ATTRIBUTE_CAP.getStorage().readNBT(ITEM_ATTRIBUTE_CAP, instence, null, nbt);
	}
}
