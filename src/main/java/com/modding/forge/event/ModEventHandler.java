package com.modding.forge.event;

import java.util.Map.Entry;
import java.util.UUID;

import com.modding.forge.Reference;
import com.modding.forge.capability.CapabilityAccessory;
import com.modding.forge.capability.CapabilityEquipment;
import com.modding.forge.capability.CapabilityStats;
import com.modding.forge.capability.CapabilityWeapon;
import com.modding.forge.capability.provider.CapabilityAccessoryProvider;
import com.modding.forge.capability.provider.CapabilityEquipmentProvider;
import com.modding.forge.capability.provider.CapabilityLevelProvider;
import com.modding.forge.capability.provider.CapabilityStatsProvider;
import com.modding.forge.capability.provider.CapabilityWeaponProvider;
import com.modding.forge.items.ItemAccessory;
import com.modding.forge.network.ModNetworkingManager;
import com.modding.forge.network.packets.OpenContainerPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.CombatRules;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ModEventHandler
{
	private static final UUID MOVESPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	private static final UUID ATTACKSPEED_MODIFIER_UUID = UUID.fromString("6E941920-1F2F-4A9B-B1D6-11B5A4B29E30");
	
	@SubscribeEvent
	public void onCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityLivingBase)
		{
			event.addCapability(new ResourceLocation(Reference.modID(), "entity_stats"), new CapabilityStatsProvider());
			event.addCapability(new ResourceLocation(Reference.modID(), "entity_level"), new CapabilityLevelProvider());
		}
		
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(Reference.modID(), "inventory_accessory"), new CapabilityAccessoryProvider());
		}
	}
	
	@SubscribeEvent
	public void onCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event)
	{
		boolean isWeapon = event.getObject().getItem() instanceof ItemSword || event.getObject().getItem() instanceof ItemBow;
		if(isWeapon) event.addCapability(new ResourceLocation(Reference.modID(), "attribute_weapon"), new CapabilityWeaponProvider());
		if(event.getObject().getItem() instanceof ItemArmor) event.addCapability(new ResourceLocation(Reference.modID(), "attribute_equipment"), new CapabilityEquipmentProvider());
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase)event.getSource().getTrueSource();
			CapabilityStats stats = entity.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				float statsDamage = stats.getValue("AttackDamage");
				float damage = event.getAmount() + statsDamage;
				event.setAmount(damage);
			}
		}
		
		if(event.getEntityLiving() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase)event.getEntityLiving();
			CapabilityStats stats = entity.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
			
			if(stats != null)
			{
				float statsDefense = stats.getValue("ArmorDefense");
				float armorDefense = entity.getTotalArmorValue() + statsDefense;
				
				float statsToughness = stats.getValue("ArmorToughness");
				float armorToughness = (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() + statsToughness;
				
				float value = CombatRules.getDamageAfterAbsorb(event.getAmount(), armorDefense, armorToughness);
				event.setAmount(value);
			}	
		}
	}
	
	@SubscribeEvent
	public void onCriticalHit(CriticalHitEvent event)
	{
		CapabilityStats stats = event.getEntityPlayer().getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
		
		if(stats != null)
		{
			float statsCritical = stats.getValue("CriticalDamage");
			if(event.isVanillaCritical()) event.setDamageModifier(1.5F + statsCritical);
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = (EntityLivingBase)event.getEntityLiving();
		CapabilityStats stats = entity.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
		
		if(stats != null)
		{
			double moveSpeed = stats.getValue("MoveSpeed") / 100F;
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
		CapabilityStats stats = event.player.getCapability(CapabilityStatsProvider.ENTITY_STATS_CAP, null);
		CapabilityAccessory accessorySlots = event.player.getCapability(CapabilityAccessoryProvider.INVENTORY_ACCESSORY_CAP, null);
		
		double attackSpeed = stats.getValue("AttackSpeed") / 100F;
		IAttributeInstance attackAttribute = event.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier attackModifier = attackAttribute.getModifier(ATTACKSPEED_MODIFIER_UUID);
		
		if(stats != null)
		{
			if(accessorySlots != null)
			{
				for(int i = 0; i < accessorySlots.getSlots(); i++)
				{
					String slotKey = "BufferSlot" + i;
					ItemStack stack = accessorySlots.getStackInSlot(i);
					boolean isCount = stats.isContain(slotKey);
					boolean isChange = accessorySlots.compareItemStack(stack, i);
					
					if(!stack.isEmpty() && stack.getItem() instanceof ItemAccessory)
					{
						ItemAccessory buffer = (ItemAccessory)stack.getItem();
						if(isChange && isCount) stats.removeBuffer(slotKey);
						else if(!isCount) stats.applyBuffer(slotKey, buffer.getAttributes());
					}
					else if(isCount) stats.removeBuffer(slotKey);
				}
			}
			
			if(attackModifier == null || attackModifier.getAmount() != attackSpeed)
			{
				if(attackModifier != null)attackAttribute.removeModifier(ATTACKSPEED_MODIFIER_UUID);
				attackAttribute.applyModifier(new AttributeModifier(ATTACKSPEED_MODIFIER_UUID, "CustomPlayerAttack", attackSpeed, 2));
			}
		}
	}
    
	@SubscribeEvent
	public void onScreenOpened(GuiScreenEvent.InitGuiEvent.Post event)
	{
		if (event.getGui() instanceof GuiContainer)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			Class<?> clazz = event.getGui().getClass();
            
			if(player.capabilities.isCreativeMode)
			{
				if(clazz == GuiContainerCreative.class) ModNetworkingManager.INSTANCE.sendToServer(new OpenContainerPacket(-1));
			}
            else if(clazz == GuiInventory.class) ModNetworkingManager.INSTANCE.sendToServer(new OpenContainerPacket(Reference.INVENTORY_ACCESSORY));
		}
	}
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		CapabilityWeapon cap_weapon = stack.getCapability(CapabilityWeaponProvider.WEAPON_ATTRIBUTE_CAP, null);
		CapabilityEquipment cap_equipment = stack.getCapability(CapabilityEquipmentProvider.EQUIPMENT_ATTRIBUTE_CAP, null);
		
		if(cap_weapon != null)
		{
			if(item instanceof ItemSword || item instanceof ItemBow)
			{
				if(!cap_weapon.isEmpty())
				{
					event.getToolTip().add("");
					event.getToolTip().add("Quality Weapon:" + cap_weapon.getColorText(cap_weapon.getQuality()) + " " + cap_weapon.getQuality());
					
					for(int i = 0; i < cap_weapon.getSize(); i++)
					{
						String value = "" + cap_weapon.getAttributeValue(i);
						if(cap_weapon.getAttributeValue(i) > 0) value = "+" + value;
						event.getToolTip().add(cap_weapon.getColorValue(cap_weapon.getAttributeValue(i)) + "" + value + " " + cap_weapon.getAttributeName(i));
					}
				}
			}
		}
		
		if(cap_equipment != null)
		{
			if(item instanceof ItemArmor)
			{
				event.getToolTip().add(TextFormatting.BLUE + " Attribute Armor");
			}
		}
	}
}
