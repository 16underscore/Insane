package me.sixteen_.insane.module.modules.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Multimap;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class Killaura extends Module implements ClientPlayerTickable {

	private Mode mode;
	private MinecraftClient mc;
	private ClientPlayerEntity player;
	private final float pi = 3.14159265F;
	private final float radiansToDegrees = 180 / pi;
	private float range;

	public Killaura() {
		super("Killaura", ModuleCategory.COMBAT);
		mode = Mode.LEGIT;
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		player = mc.player;
		range = 3.7F;
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
				player.swingHand(Hand.MAIN_HAND);
			}
		}
	}

	private void multiAura() {
		if (player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		for (LivingEntity target : getTargets()) {
			mc.interactionManager.attackEntity(player, target);
			player.swingHand(Hand.MAIN_HAND);
		}
	}

	private void onlyPacketAura() {
		for (LivingEntity target : getTargets()) {
			mc.getNetworkHandler().sendPacket(new PlayerInteractEntityC2SPacket(target, player.isSneaking()));
		}
	}

	private void legitAura() {
		if (player.isDead()) {
			return;
		}
		if (player.isUsingItem()) {
			return;
		}
		if (player.isBlocking()) {
			return;
		}
		if (player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		final LivingEntity target = getTarget(Filter.NEAREST);
		if (target == null) {
			return;
		}
		if (!player.canSee(target)) {
			return;
		}
		if (target.isInvisible() && target.getArmorVisibility() == 0 && target.getMainHandStack().isEmpty()) {
			return;
		}
		lookAtTarget(target);
		mc.interactionManager.attackEntity(player, target);
		player.swingHand(Hand.MAIN_HAND);
	}

	private void lookAtTarget(LivingEntity le) {
		final Vec3d playerPos = mc.player.getPos();
		final Vec3d entityPos = le.getPos();
		final double deltaX = entityPos.getX() - playerPos.getX();
		final double deltaY = (entityPos.getY() + le.getEyeHeight(le.getPose())) - (playerPos.getY() + player.getEyeHeight(player.getPose()));
		final double deltaZ = entityPos.getZ() - playerPos.getZ();
		final double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		final float pitch = MathHelper.wrapDegrees(((float) (-MathHelper.atan2(deltaY, distanceXZ))) * radiansToDegrees);
		final float yaw = MathHelper.wrapDegrees(((float) MathHelper.atan2(deltaZ, deltaX)) * radiansToDegrees - 90F);
		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookOnly(yaw, pitch, player.isOnGround()));
		mc.player.pitch = pitch;
		mc.player.yaw = yaw;
	}

	@Nullable
	private LivingEntity getTarget(Filter f) {
		LivingEntity filteredTarget = null;
		boolean sort = false;
		for (LivingEntity target : getTargets()) {
			if (filteredTarget == null) {
				filteredTarget = target;
			} else {
				switch (f) {
				case NEAREST:
					sort = player.squaredDistanceTo(target) < player.squaredDistanceTo(filteredTarget);
					break;
				case SHORTESTJOBFIRST:
					sort = target.getHealth() < filteredTarget.getHealth();
					break;
				case FIRSTCOMEFIRSTSERVE:
					return filteredTarget;
				}
				if (sort) {
					filteredTarget = target;
				}
			}
		}
		return filteredTarget;
	}

	private List<LivingEntity> getTargets() {
		final Iterator<Entity> it = mc.world.getEntities().iterator();
		final List<LivingEntity> targets = new ArrayList<LivingEntity>();
		while (it.hasNext()) {
			final Entity e = it.next();
			if (e == null) {
				continue;
			}
			if (!(e instanceof LivingEntity)) {
				continue;
			}
			final LivingEntity le = (LivingEntity) e;
			if (le.equals(player)) {
				continue;
			}
			if (!player.isInRange(le, range)) {
				continue;
			}
			if (le.hurtTime > 0) {
				continue;
			}
			if (le.isDead()) {
				continue;
			}
			if (le.isInvulnerable()) {
				continue;
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

	private enum Filter {
		NEAREST, SHORTESTJOBFIRST, FIRSTCOMEFIRSTSERVE;
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
