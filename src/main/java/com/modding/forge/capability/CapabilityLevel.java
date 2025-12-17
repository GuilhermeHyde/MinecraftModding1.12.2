package com.modding.forge.capability;

import com.modding.forge.capability.interfaces.ICapabilityMod;

public class CapabilityLevel implements ICapabilityMod
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
	public float getValue(String name)
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
}
