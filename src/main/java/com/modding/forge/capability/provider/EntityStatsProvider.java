package com.modding.forge.capability.provider;

import com.modding.forge.capability.EntityStats;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntityStatsProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(EntityStats.class)
	public static final Capability<EntityStats> ENTITY_STATS_CAP = null;
	
	private EntityStats instance = new EntityStats();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == ENTITY_STATS_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == ENTITY_STATS_CAP ? ENTITY_STATS_CAP.cast(this.instance) : null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return (NBTTagCompound)ENTITY_STATS_CAP.getStorage().writeNBT(ENTITY_STATS_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		ENTITY_STATS_CAP.getStorage().readNBT(ENTITY_STATS_CAP, instance, null, nbt);
	}
	
}
