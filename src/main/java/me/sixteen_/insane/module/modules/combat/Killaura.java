package me.sixteen_.insane.module.modules.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
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
		case LEGIT:
			legitAura();
			break;
		}
	}

	private void fastAura() {
		for (LivingEntity target : getTargets()) {
			if (target.getHealth() < attackDamage(target) || player.getAttackCooldownProgress(0F) >= 1F) {
				mc.interactionManager.attackEntity(player, target);
			}
		}
	}

	private void multiAura() {
		if (player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		for (LivingEntity target : getTargets()) {
			mc.interactionManager.attackEntity(player, target);
		}
	}

	private void onlyPacketAura() {
		for (LivingEntity target : getTargets()) {
			mc.getNetworkHandler().sendPacket(new PlayerInteractEntityC2SPacket(target, player.isSneaking()));
		}
	}

	private void legitAura() {
		if (player.isUsingItem()) {
			return;
		}
		if (player.isBlocking()) {
			return;
		}
		if (player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		LivingEntity nearestTarget = null;
		for (LivingEntity target : getTargets()) {
			if (nearestTarget == null) {
				nearestTarget = target;
			} else if (player.distanceTo(target) < player.distanceTo(nearestTarget)) {
				nearestTarget = target;
			}
		}
		if (nearestTarget == null) {
			return;
		}
		if (!mc.player.canSee(nearestTarget)) {
			return;
		}
		mc.interactionManager.attackEntity(player, nearestTarget);
	}

	private List<LivingEntity> getTargets() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		final List<LivingEntity> targets = new ArrayList<LivingEntity>();
		label: while (it.hasNext()) {
			final Entity e = it.next();
			if (e == null) {
				continue label;
			}
			if (!(e instanceof LivingEntity)) {
				continue label;
			}
			final LivingEntity le = (LivingEntity) e;
			if (le.equals(player)) {
				continue label;
			}
			if (player.distanceTo(le) > range) {
				continue label;
			}
			if (le.hurtTime > 0) {
				continue label;
			}
			if (le.isDead()) {
				continue label;
			}
			targets.add(le);
		}
		return targets;
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

	@Override
	public void setMode(String s) {
		for (Mode m : Mode.values()) {
			if (m.toString().equalsIgnoreCase(s)) {
				mode = m;
				return;
			}
		}
	}

	private enum Mode {

		FAST("Fast"), MULTI("Multi"), ONLYPACKET("OnlyPacket"), LEGIT("Legit");

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
