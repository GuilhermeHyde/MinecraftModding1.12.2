package com.modding.forge.capability.provider;

import com.modding.forge.capability.CapabilityLevel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityLevelProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityLevel.class)
	public static final Capability<CapabilityLevel> ENTITY_LEVEL_CAP = null;
	private CapabilityLevel instance = new CapabilityLevel();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == ENTITY_LEVEL_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == ENTITY_LEVEL_CAP ? ENTITY_LEVEL_CAP.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)ENTITY_LEVEL_CAP.getStorage().writeNBT(ENTITY_LEVEL_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		ENTITY_LEVEL_CAP.getStorage().readNBT(ENTITY_LEVEL_CAP, instance, null, nbt);
	}
}
