package me.sixteen_.insane.module.modules.combat;

import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import me.sixteen_.insane.utils.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;

/**
 * @author 16_
 */
public class Killaura extends Module implements ClientPlayerTickable {

	private MinecraftClient mc;
	private ClientPlayerEntity player;
	private boolean pause;
	private float range;

	public Killaura() {
		super("Killaura", ModuleCategory.COMBAT);
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		player = mc.player;
		range = 6F;
		pause = false;
	}

	@Override
	public void tick() {
		if (isEnabled()) {
			onUpdate();
		}
	}

	@Override
	public void onUpdate() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		label: while (it.hasNext()) {
			final Entity entity = it.next();
			if (!(entity instanceof LivingEntity)) {
				continue label;
			}
			final LivingEntity le = (LivingEntity) entity;
			if (le.equals(player)) {
				continue label;
			}
			if (player.distanceTo(le) > range) {
				continue label;
			}
			if (le.isDead()) {
				continue label;
			}
			if (le.hurtTime > 0) {
				continue label;
			}
			if (le.getHealth() < attackDamage(le)) {
				mc.interactionManager.attackEntity(player, le);
			}
			if (player.getAttackCooldownProgress(0) == 1) {
				if (pause) {
					mc.interactionManager.attackEntity(player, le);
					pause = false;
					continue label;
				}
			}
			pause = true;
		}
	}

	private float attackDamage(final LivingEntity target) {
		float damage;
		float ench;
		float cool;
		damage = attackDamage();
		Logger.getLogger().addChatMessage(damage + "", true);
		ench = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), target.getGroup());
		cool = player.getAttackCooldownProgress(0.5F);
		damage *= 0.2F + cool * cool * 0.8F;
		ench *= cool;
		final boolean crit = (cool > 0.9F && player.fallDistance > 0.0F && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater()
				&& !player.hasStatusEffect(StatusEffects.BLINDNESS) && !player.hasVehicle() && target instanceof LivingEntity) && !player.isSprinting();
		if (crit) {
			damage *= 1.5F;
		}
		damage += ench;
		return damage;
	}

	private float attackDamage() {
		final float damageWithHand = (float) player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		Multimap<EntityAttribute, EntityAttributeModifier> map = player.getMainHandStack().getAttributeModifiers(EquipmentSlot.MAINHAND);
		if (!map.isEmpty()) {
			float damageWithItem = 0F;
			Iterator<Entry<EntityAttribute, EntityAttributeModifier>> it = map.entries().iterator();
			while (it.hasNext()) {
				Entry<EntityAttribute, EntityAttributeModifier> entry = it.next();
				EntityAttribute a = (EntityAttribute) entry.getKey();
				if (a.getTranslationKey().equals("attribute.name.generic.attack_damage")) {
					EntityAttributeModifier m = (EntityAttributeModifier) entry.getValue();
					damageWithItem += (float) m.getValue();
				} else if (a.getTranslationKey().equals("attribute.name.generic.knockback_resistance")) {
					damageWithItem--;
				}
			}
			return damageWithHand + damageWithItem;
		}
		return damageWithHand;
	}
	/*
	 * Buggy version
	 * 
	 * private float genericAttackDamage() { float f = 1F; Iterator<Text> it = player.getMainHandStack().getTooltip(player,
	 * TooltipContext.Default.NORMAL).iterator(); while (it.hasNext()) { final String text = it.next().getString(); if
	 * (text.contains("Attack Damage")) { f = Float.parseFloat(text.split(" ")[1]); } } return f; }
	 */
}
