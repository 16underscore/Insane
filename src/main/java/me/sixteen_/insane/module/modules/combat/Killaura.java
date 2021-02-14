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
public final class Killaura extends Module implements ClientPlayerTickable {

	private Mode mode;
	private float range;
	private List<ClientPlayerTickable> tickables;
	private final float pi = 3.14159265F;
	private final float radiansToDegrees = 180 / pi;

	public Killaura() {
		super("Killaura", ModuleCategory.COMBAT);
		mode = Mode.LEGIT;
		range = 3.7F;
	}

	public void setTickables(List<ClientPlayerTickable> tickables) {
		this.tickables = tickables;
	}

	@Override
	protected void onEnable() {
		tickables.add(this);
	}

	@Override
	protected void onDisable() {
		tickables.remove(this);
	}

	@Override
	public void tick() {
		onUpdate();
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
		for (final LivingEntity target : getTargets()) {
			if (target.getHealth() < attackDamage(target) || mc.player.getAttackCooldownProgress(0F) >= 1F) {
				mc.interactionManager.attackEntity(mc.player, target);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}

	private void multiAura() {
		if (mc.player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		for (final LivingEntity target : getTargets()) {
			mc.interactionManager.attackEntity(mc.player, target);
			mc.player.swingHand(Hand.MAIN_HAND);
		}
	}

	private void onlyPacketAura() {
		for (final LivingEntity target : getTargets()) {
			mc.getNetworkHandler().sendPacket(new PlayerInteractEntityC2SPacket(target, mc.player.isSneaking()));
		}
	}

	private void legitAura() {
		if (mc.player.isDead()) {
			return;
		}
		if (mc.player.isUsingItem()) {
			return;
		}
		if (mc.player.isBlocking()) {
			return;
		}
		if (mc.player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		final LivingEntity target = getTarget(Filter.NEAREST);
		if (target == null) {
			return;
		}
		if (!mc.player.canSee(target)) {
			return;
		}
		if (target.isInvisible() && target.getArmorVisibility() == 0 && target.getMainHandStack().isEmpty()) {
			return;
		}
		lookAtTarget(target);
		mc.interactionManager.attackEntity(mc.player, target);
		mc.player.swingHand(Hand.MAIN_HAND);
	}

	private void lookAtTarget(final LivingEntity le) {
		final Vec3d playerPos = mc.player.getPos();
		final Vec3d entityPos = le.getPos();
		final double deltaX = entityPos.getX() - playerPos.getX();
		final double deltaY = (entityPos.getY() + le.getEyeHeight(le.getPose())) - (playerPos.getY() + mc.player.getEyeHeight(mc.player.getPose()));
		final double deltaZ = entityPos.getZ() - playerPos.getZ();
		final double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		final float pitch = MathHelper.wrapDegrees(((float) (-MathHelper.atan2(deltaY, distanceXZ))) * radiansToDegrees);
		final float yaw = MathHelper.wrapDegrees(((float) MathHelper.atan2(deltaZ, deltaX)) * radiansToDegrees - 90F);
		mc.player.pitch = pitch;
		mc.player.yaw = yaw;
		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookOnly(yaw, pitch, mc.player.isOnGround()));
		mc.cameraEntity.pitch = mc.player.prevPitch;
		mc.cameraEntity.yaw = mc.player.prevYaw;
	}

	@Nullable
	private LivingEntity getTarget(final Filter f) {
		LivingEntity filteredTarget = null;
		boolean sort = false;
		for (final LivingEntity target : getTargets()) {
			if (filteredTarget == null) {
				filteredTarget = target;
			} else {
				switch (f) {
				case NEAREST:
					sort = mc.player.squaredDistanceTo(target) < mc.player.squaredDistanceTo(filteredTarget);
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
			if (le.equals(mc.player)) {
				continue;
			}
			if (!mc.player.isInRange(le, range)) {
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
		ench = EnchantmentHelper.getAttackDamage(mc.player.getMainHandStack(), target.getGroup());
		cool = mc.player.getAttackCooldownProgress(0.5F);
		damage *= 0.2F + cool * cool * 0.8F;
		ench *= cool;
		final boolean crit = (cool > 0.9F && mc.player.fallDistance > 0.0F && !mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater()
				&& !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) && !mc.player.hasVehicle() && target instanceof LivingEntity) && !mc.player.isSprinting();
		if (crit) {
			damage *= 1.5F;
		}
		damage += ench;
		return damage;
	}

	private float attackDamage() {
		final float damageWithHand = (float) mc.player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		final Multimap<EntityAttribute, EntityAttributeModifier> map = mc.player.getMainHandStack().getAttributeModifiers(EquipmentSlot.MAINHAND);
		if (!map.isEmpty()) {
			float damageWithItem = 0F;
			final Iterator<Entry<EntityAttribute, EntityAttributeModifier>> it = map.entries().iterator();
			while (it.hasNext()) {
				final Entry<EntityAttribute, EntityAttributeModifier> entry = it.next();
				final EntityAttribute a = (EntityAttribute) entry.getKey();
				if (a.equals(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
					EntityAttributeModifier m = (EntityAttributeModifier) entry.getValue();
					damageWithItem += (float) m.getValue();
				} else if (a.equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)) {
					damageWithItem--;
				}
			}
			return damageWithHand + damageWithItem;
		}
		return damageWithHand;
	}

	@Override
	public void setMode(final String s) {
		for (final Mode m : Mode.values()) {
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

		private Mode(final String modeName) {
			this.modeName = modeName;
		}

		@Override
		public String toString() {
			return modeName;
		}
	}
}