package com.modding.forge.capability;

import com.modding.forge.capability.interfaces.ICapabilityEntityStats;

public class EntityLevel implements ICapabilityEntityStats
{
	private float level, points, exp, maxExp, strength, resistance, agility;
	
	@Override
	public void setValue(int id, float value)
	{
		switch(id)
		{
		case 0:
			this.level = value;
			break;
		case 1:
			this.points = value;
			break;
		case 2:
			this.exp = value;
			break;
		case 3:
			this.maxExp = value;
			break;
		case 4:
			this.strength = value;
			break;
		case 5:
			this.resistance = value;
			break;
		case 6:
			this.agility = value;
			break;
			default:
				return;
		}
	}

	@Override
	public float getValue(int id)
	{
		switch(id)
		{
		case 0:
			return this.level;
		case 1:
			return this.points;
		case 2:
			return this.exp;
		case 3:
			return this.maxExp;
		case 4:
			return this.strength;
		case 5:
			return this.resistance;
		case 6:
			return this.agility;
			default:
				return 0;
		}
	}
}
