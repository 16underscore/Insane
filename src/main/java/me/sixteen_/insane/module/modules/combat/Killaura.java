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
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

/**
 * @author 16_
 */
public class Killaura extends Module implements ClientPlayerTickable {

	private Mode mode;
	private MinecraftClient mc;
	private ClientPlayerEntity player;
	private float range;

	public Killaura() {
		super("Killaura", ModuleCategory.COMBAT);
		mode = Mode.FAST;
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		player = mc.player;
		range = 6F;
	}

	@Override
	public void tick() {
		if (isEnabled()) {
			onUpdate();
		}
	}

	@Override
	public void onUpdate() {
		switch (mode) {
		case FAST:
			fastAura();
			break;
		case MULTI:
			multiAura();
			break;
		case ONLYPACKET:
			onlyPacketAura();
			break;
		}
	}

	private void fastAura() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		while (it.hasNext()) {
			final Entity entity = it.next();
			if (entity instanceof LivingEntity) {
				final LivingEntity le = (LivingEntity) entity;
				if (filter(le)) {
					if (le.getHealth() < attackDamage(le)) {
						mc.interactionManager.attackEntity(player, le);
						Logger.getLogger().addChatMessage(attackDamage(le) + "");
					}
					if (player.getAttackCooldownProgress(0F) >= 1F) {
						mc.interactionManager.attackEntity(player, le);
					}
				}
			}
		}
	}

	private void multiAura() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		if (player.getAttackCooldownProgress(0F) >= 1F) {
			while (it.hasNext()) {
				final Entity entity = it.next();
				if (entity instanceof LivingEntity) {
					final LivingEntity le = (LivingEntity) entity;
					if (filter(le)) {
						mc.interactionManager.attackEntity(player, le);
					}
				}
			}
		}
	}

	private void onlyPacketAura() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		while (it.hasNext()) {
			final Entity entity = it.next();
			if (entity instanceof LivingEntity) {
				final LivingEntity le = (LivingEntity) entity;
				if (filter(le)) {
					mc.getNetworkHandler().sendPacket(new PlayerInteractEntityC2SPacket(le, player.isSneaking()));
				}
			}
		}
	}

	private boolean filter(LivingEntity le) {
		if (le.equals(player)) {
			return false;
		}
		if (player.distanceTo(le) > range) {
			return false;
		}
		if (le.hurtTime > 0) {
			return false;
		}
		if (le.isDead()) {
			return false;
		}
		return true;
	}

	private float attackDamage(final LivingEntity target) {
		float damage;
		float ench;
		float cool;
		damage = attackDamage();
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

	@Override
	public void setMode(String s) {
		for (Mode m : Mode.values()) {
			if (m.toString().equalsIgnoreCase(s)) {
				mode = m;
				Logger.getLogger().addChatMessage(String.format("Mode %s activated", s));
				return;
			}
		}
		Logger.getLogger().addChatMessage(String.format("Mode %s not found!", s));
	}

	private enum Mode {

		FAST("Fast"), MULTI("Multi"), ONLYPACKET("OnlyPacket");

		private final String modeName;

		private Mode(String modeName) {
			this.modeName = modeName;
		}

		@Override
		public String toString() {
			return modeName;
		}
	}
}
