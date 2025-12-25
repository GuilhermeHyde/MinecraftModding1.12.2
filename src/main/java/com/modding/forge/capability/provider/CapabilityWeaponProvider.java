package com.modding.forge.capability.provider;

import com.modding.forge.capability.CapabilityWeapon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityWeaponProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(CapabilityWeapon.class)
	public static final Capability<CapabilityWeapon> WEAPON_ATTRIBUTE_CAP = null;
	private CapabilityWeapon instance = new CapabilityWeapon();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == WEAPON_ATTRIBUTE_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == WEAPON_ATTRIBUTE_CAP ? WEAPON_ATTRIBUTE_CAP.cast(instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)WEAPON_ATTRIBUTE_CAP.getStorage().writeNBT(WEAPON_ATTRIBUTE_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		WEAPON_ATTRIBUTE_CAP.getStorage().readNBT(WEAPON_ATTRIBUTE_CAP, instance, null, nbt);
	}
}
