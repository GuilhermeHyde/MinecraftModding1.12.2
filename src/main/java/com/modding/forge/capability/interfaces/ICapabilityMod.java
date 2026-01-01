package com.modding.forge.capability.interfaces;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICapabilityMod<T extends NBTBase> extends INBTSerializable<T>
{
	void setValue(String id, float value);
	Object getValue(String id);
}
