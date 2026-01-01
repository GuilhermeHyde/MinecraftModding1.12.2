package com.modding.forge.capability;

import com.modding.forge.capability.interfaces.ICapabilityMod;

import net.minecraft.nbt.NBTTagCompound;

public class CapabilityLevel implements ICapabilityMod<NBTTagCompound>
{
	private float level, points, exp, maxExp, strength, resistance, agility;
	
	@Override
	public void setValue(String name, float value)
	{
		switch(name)
		{
		case "Level":
			this.level = value;
			break;
		case "Points":
			this.points = value;
			break;
		case "Exp":
			this.exp = value;
			break;
		case "MaxExp":
			this.maxExp = value;
			break;
		case "Strength":
			this.strength = value;
			break;
		case "Resistance":
			this.resistance = value;
			break;
		case "Agility":
			this.agility = value;
			break;
			default:
				return;
		}
	}

	@Override
	public Object getValue(String name)
	{
		switch(name)
		{
		case "Level":
			return this.level;
		case "Points":
			return this.points;
		case "Exp":
			return this.exp;
		case "MaxExp":
			return this.maxExp;
		case "Strength":
			return this.strength;
		case "Resistance":
			return this.resistance;
		case "Agility":
			return this.agility;
			default:
				return 0;
		}
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("Level", (float)this.getValue("Level"));
		tag.setFloat("Points", (float)this.getValue("Points"));
		tag.setFloat("Exp", (float)this.getValue("Exp"));
		tag.setFloat("MaxExp", (float)this.getValue("MaxExp"));
		tag.setFloat("Strength", (float)this.getValue("Strength"));
		tag.setFloat("Resistance", (float)this.getValue("Resistance"));
		tag.setFloat("Agility", (float)this.getValue("Agility"));
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.setValue("Level", nbt.getFloat("Level"));
		this.setValue("Points", nbt.getFloat("Points"));
		this.setValue("Exp", nbt.getFloat("Exp"));
		this.setValue("MaxExp", nbt.getFloat("MaxExp"));
		this.setValue("Strength", nbt.getFloat("Strength"));
		this.setValue("Resistance", nbt.getFloat("Resistance"));
		this.setValue("Agility", nbt.getFloat("Agility"));
	}
}
