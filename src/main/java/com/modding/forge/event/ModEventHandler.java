package com.modding.forge.event;

import java.util.UUID;

import com.modding.forge.Reference;
import com.modding.forge.capability.EntityStats;
import com.modding.forge.capability.provider.EntityStatsProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.CombatRules;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler
{
	private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	private static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	
	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Reference.modID(), "entity_stats"), new EntityStatsProvider());
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		Entity damage = event.getSource().getTrueSource();
		if(damage instanceof EntityLivingBase)
		{
			EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
			EntityStats stats = attacker.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				float value = event.getAmount() + stats.getValue(0);
				event.setAmount(value);
			}
		}
		
		Entity defense = event.getEntityLiving();
		if(defense instanceof EntityLivingBase)
		{
			EntityLivingBase target = (EntityLivingBase)event.getEntityLiving();
			EntityStats stats = target.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				float armorDefense = target.getTotalArmorValue() + stats.getValue(4);
				float armorTorghness = (float)target.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() + stats.getValue(5);
				float value = CombatRules.getDamageAfterAbsorb(event.getAmount(), armorDefense, armorTorghness);
				event.setAmount(value);
			}
		}
	}
	
	@SubscribeEvent
	public void onCriticalHit(CriticalHitEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		EntityStats stats = player.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
		
		if(stats != null)
		{
			if(event.isVanillaCritical()) event.setDamageModifier(1.5F + stats.getValue(1));
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = (EntityLivingBase)event.getEntityLiving();
		EntityStats stats = entity.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
		
		if(stats != null)
		{
			double moveSpeed = stats.getValue(2);
			IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			AttributeModifier speedModifier = speedAttribute.getModifier(SPEED_MODIFIER_UUID);
			
			if(speedModifier == null)speedAttribute.applyModifier(new AttributeModifier(SPEED_MODIFIER_UUID, "CustomSpeed", moveSpeed, 2));
			else if(speedModifier.getAmount() != moveSpeed) speedAttribute.removeModifier(SPEED_MODIFIER_UUID);
			
			if(entity instanceof EntityPlayer)
			{
				double attackSpeed = stats.getValue(3);
				IAttributeInstance attackAttribute = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
				AttributeModifier attackModifier = attackAttribute.getModifier(ATTACK_SPEED_MODIFIER_UUID);
				
				if(attackModifier == null)attackAttribute.applyModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "CustomAttack", attackSpeed, 2));
				else if(attackModifier.getAmount() != attackSpeed) attackAttribute.removeModifier(ATTACK_SPEED_MODIFIER_UUID);
			}
		}
	}
}
