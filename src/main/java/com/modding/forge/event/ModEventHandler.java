package com.modding.forge.event;

import com.modding.forge.Reference;
import com.modding.forge.capability.EntityStats;
import com.modding.forge.capability.provider.EntityStatsProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
		Entity source = event.getSource().getTrueSource();
		if(source instanceof EntityLivingBase)
		{
			EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
			EntityStats stats = attacker.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				stats.setValue(0, 1);
				float damage = stats.getValue(0);
				event.setAmount(event.getAmount() + damage);
			}
		}
	}
}
