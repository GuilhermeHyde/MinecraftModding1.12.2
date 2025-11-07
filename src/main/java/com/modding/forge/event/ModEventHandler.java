package com.modding.forge.event;

import java.util.UUID;

import com.modding.forge.Reference;
import com.modding.forge.capability.EntityStats;
import com.modding.forge.capability.provider.EntityStatsProvider;
import com.modding.forge.containers.inventory.InventoryAccessory;
import com.modding.forge.gui.GuiInventoryAccessory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.CombatRules;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ModEventHandler
{
	private static final UUID MOVESPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	private static final UUID ATTACKSPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	
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
		Entity entity = event.getSource().getTrueSource();
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase)event.getSource().getTrueSource();
			EntityStats stats = entityLiving.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				float statsDamage = 1 + stats.getValue(0) / 100F;
				float damage = event.getAmount() * statsDamage;
				
				float statsDefense = 1 + stats.getValue(4) / 100F;
				float armorDefense = entityLiving.getTotalArmorValue() * statsDefense;
				
				float statsTorghness = 1 + stats.getValue(5) / 100F;
				float armorTorghness = (float)entityLiving.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() * statsTorghness;
				
				float value = CombatRules.getDamageAfterAbsorb(damage, armorDefense, armorTorghness);
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
			float statsCritical = 1 + stats.getValue(1) / 100F;
			if(event.isVanillaCritical()) event.setDamageModifier(1.5F * statsCritical);
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = (EntityLivingBase)event.getEntityLiving();
		EntityStats stats = entity.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
		
		if(stats != null)
		{
			double moveSpeed = stats.getValue(2) / 100F;
			IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			AttributeModifier speedModifier = speedAttribute.getModifier(MOVESPEED_MODIFIER_UUID);
			
			if(speedModifier == null || speedModifier.getAmount() != moveSpeed)
			{
				if(speedModifier != null)speedAttribute.removeModifier(MOVESPEED_MODIFIER_UUID);
				speedAttribute.applyModifier(new AttributeModifier(MOVESPEED_MODIFIER_UUID, "CustomSpeed", moveSpeed, 2));
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event)
	{
		EntityStats stats = event.player.getCapability(EntityStatsProvider.ENTITY_STATS_CAP, null);
		
		double attackSpeed = stats.getValue(3) / 100F;
		IAttributeInstance attackAttribute = event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier attackModifier = attackAttribute.getModifier(ATTACKSPEED_MODIFIER_UUID);
		
		double moveSpeed = stats.getValue(2) / 100F;
		IAttributeInstance speedAttribute = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		AttributeModifier speedModifier = speedAttribute.getModifier(MOVESPEED_MODIFIER_UUID);
		
		if(stats != null)
		{
			if(speedModifier == null || speedModifier.getAmount() != moveSpeed)
			{
				if(speedModifier != null)speedAttribute.removeModifier(MOVESPEED_MODIFIER_UUID);
				speedAttribute.applyModifier(new AttributeModifier(MOVESPEED_MODIFIER_UUID, "CustomPlayerSpeed", moveSpeed, 2));
			}
			
			if(attackModifier == null || attackModifier.getAmount() != attackSpeed)
			{
				if(attackModifier != null)attackAttribute.removeModifier(ATTACKSPEED_MODIFIER_UUID);
				attackAttribute.applyModifier(new AttributeModifier(ATTACKSPEED_MODIFIER_UUID, "CustomPlayerAttack", attackSpeed, 2));
			}
		}
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
	    if (event.getGui() instanceof GuiInventory)
	    {
	        EntityPlayer player = Minecraft.getMinecraft().player;
	        if(!player.isCreative())event.setGui(new GuiInventoryAccessory(new InventoryAccessory(), player)); 
	    }
	}
}
