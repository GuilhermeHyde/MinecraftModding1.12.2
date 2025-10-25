package com.modding.forge.event;

import com.modding.forge.Reference;
import com.modding.forge.capability.EntityStats;
import com.modding.forge.capability.provider.EntityStatsProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.CombatRules;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler
{
	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Reference.modID(), "entity_stats"), new EntityStatsProvider());
		}
	}
	
	@SubscribeEvent
	public void onLivingAttack(LivingHurtEvent event)
	{
		Entity damage = event.getSource().getTrueSource();
		if(damage instanceof EntityLivingBase)
		{
			EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
			EntityStats stats = attacker.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				stats.setValue(0, 1);
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
				stats.setValue(4, 0);
				stats.setValue(5, 0);
				float armorDefense = target.getTotalArmorValue() + stats.getValue(5);
				float armorTorghness = (float)target.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() + stats.getValue(6);
				float value = CombatRules.getDamageAfterAbsorb(event.getAmount(), armorDefense, armorTorghness);
				event.setAmount(value);
			}
		}
	}
}
