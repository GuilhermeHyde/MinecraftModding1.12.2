package com.modding.forge.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.modding.forge.items.interfaces.IAccessory;
import net.minecraft.item.Item;

public class ItemAccessory extends Item implements IAccessory
{
	private EnumAccessoryType accessoryType;
	private float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughness;
	public Map<String, Float> attributes = new HashMap<>();
	
	public ItemAccessory(EnumAccessoryType type, AccessoryAttribute stats)
	{
		this.setAccessoryType(type);
		this.attackDamage = stats.getAttackDamage();
		this.criticalDamage = stats.getCriticalDamage();
		this.moveSpeed = stats.getMoveSpeed();
		this.attackSpeed = stats.getAttackSpeed();
		this.armorDefense = stats.getArmorDefense();
		this.armorToughness = stats.getArmorToughness();
	}
	
	@Override
	public EnumAccessoryType getAccessoryType()
	{
		return this.accessoryType;
	}

	@Override
	public void setAccessoryType(EnumAccessoryType type)
	{
		this.accessoryType = type;
	}
	
	public Map<String, Float> getAttributes()
	{
		Map<String, Float> stats = new HashMap<>();
		stats.put("AttackDamage", this.attackDamage);
		stats.put("CriticalDamage", this.criticalDamage);
		stats.put("MoveSpeed", this.moveSpeed);
		stats.put("AttackSpeed", this.attackSpeed);
		stats.put("ArmorDefense", this.armorDefense);
		stats.put("ArmorToughness", this.armorToughness);
		return stats;
	}
	
	public void incrementAttribute(Map<String, Float> map)
	{
		for(Entry<String, Float> entry : map.entrySet())
		{
			this.attributes.merge(entry.getKey(), entry.getValue(), Float::sum);
		}
	}
	
	public static enum AccessoryAttribute
	{
		IRON_NECKLACE("iron_necklece", 0, 0, 0, 0, 15, 5),
		IRON_RING("iron_ring", 0, 0, 0, 0, 5, 5),
		GOLD_NECKLACE("gold_necklace", 0, 0, 15, 5, 0, 0),
		GOLD_RING("gold_ring", 0, 0, 5, 5, 0, 0),
		DIAMOND_NECKLACE("diamond_necklace", 15, 5, 0, 0, 0, 0),
		DIAMOND_RING("diamond_ring", 5, 5, 0, 0, 0, 0);
		
		private final String name;
		private final float attackDamage, criticalDamage, moveSpeed, attackSpeed, armorDefense, armorToughness;
		
		private AccessoryAttribute(String name, float attackDamage, float criticalDamage, float moveSpeed, float attackSpeed, float armorDefense, float armorToughness)
		{
			this.name = name;
			this.attackDamage = attackDamage;
			this.criticalDamage = criticalDamage;
			this.moveSpeed = moveSpeed;
			this.attackSpeed = attackSpeed;
			this.armorDefense = armorDefense;
			this.armorToughness = armorToughness;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public float getAttackDamage()
		{
			return this.attackDamage;
		}
		
		public float getCriticalDamage()
		{
			return this.criticalDamage;
		}
		
		public float getMoveSpeed()
		{
			return this.moveSpeed;
		}
		
		public float getAttackSpeed()
		{
			return this.attackSpeed;
		}
		
		public float getArmorDefense()
		{
			return this.armorDefense;
		}
		
		public float getArmorToughness()
		{
			return this.armorToughness;
		}
	}
}
