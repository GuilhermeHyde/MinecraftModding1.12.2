package com.modding.forge.capability;

import com.modding.forge.capability.interfaces.ICapabilityMod;

public class CapabilityStats implements ICapabilityMod
{
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorTougthness;
	
	@Override
	public void setValue(int id, float value)
	{
		switch(id)
		{
		case 0:
			this.attackDamage = value;
			break;
		case 1:
			this.criticalDamage = value;
			break;
		case 2:
			this.moveSpeed = value;
			break;
		case 3:
			this.attackSpeed = value;
			break;
		case 4:
			this.armorDefense = value;
			break;
		case 5:
			this.armorTougthness = value;
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
			return this.attackDamage;
		case 1:
			return this.criticalDamage;
		case 2:
			return this.moveSpeed;
		case 3:
			return this.attackSpeed;
		case 4:
			return this.armorDefense;
		case 5:
			return this.armorTougthness;
			default:
				return 0.0F;
		}
	}
}
