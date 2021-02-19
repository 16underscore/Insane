package me.sixteen_.insane.module.modules.combat;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.Multimap;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import me.sixteen_.insane.value.ranges.IntegerRange;
import me.sixteen_.insane.value.values.FloatValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.enchantment.EnchantmentHelper;
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

	private List<ClientPlayerTickable> tickables;
	private final ListValue mode, sort;
	private final FloatValue range;
	private final IntegerRange cps;
	private final float pi = 3.14159265F, radiansToDegrees = 180 / pi;

	public Killaura() {
		super("Killaura", ModuleCategory.COMBAT);
		mode = new ListValue("mode", true, "legit", "fast", "multi");
		sort = new ListValue("sort", false, "distance", "health");
		range = new FloatValue("range", true, 3.7F, 3F, 6F, 0.1F);
		cps = new IntegerRange("cps", false, 8, 12, 0, 20);
		this.addValues(mode);
		this.addValues(sort);
		this.addValues(range);
		this.addValues(cps);
	}

	public final void setTickables(List<ClientPlayerTickable> tickables) {
		this.tickables = tickables;
	}

	@Override
	protected final void onEnable() {
		tickables.add(this);
	}

	@Override
	protected final void onDisable() {
		tickables.remove(this);
	}

	@Override
	public final void tick() {
		onUpdate();
	}

	@Override
	public final void onUpdate() {
		if (mode.is("legit")) {
			legitAura();
		} else if (mode.is("fast")) {
			fastAura();
		} else if (mode.is("multi")) {
			multiAura();
		}
	}

	private final void fastAura() {
		for (final LivingEntity target : getTargets()) {
			if (target.getHealth() < attackDamage(target) || mc.player.getAttackCooldownProgress(0F) >= 1F) {
				mc.interactionManager.attackEntity(mc.player, target);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}

	private final void multiAura() {
		if (mc.player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		for (final LivingEntity target : getTargets()) {
			mc.interactionManager.attackEntity(mc.player, target);
			mc.player.swingHand(Hand.MAIN_HAND);
		}
	}

	private final void legitAura() {
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
		final LivingEntity target = getTarget();
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

	private final void lookAtTarget(final LivingEntity le) {
		final Vec3d playerPos = mc.player.getPos(), entityPos = le.getPos();
		final double deltaX = entityPos.getX() - playerPos.getX(), deltaY = (entityPos.getY() + le.getEyeHeight(le.getPose())) - (playerPos.getY() + mc.player.getEyeHeight(mc.player.getPose())), deltaZ = entityPos.getZ() - playerPos.getZ(),
				distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		final float pitch = MathHelper.wrapDegrees(((float) (-MathHelper.atan2(deltaY, distanceXZ))) * radiansToDegrees), yaw = MathHelper.wrapDegrees(((float) MathHelper.atan2(deltaZ, deltaX)) * radiansToDegrees - 90F);
		mc.player.pitch = pitch;
		mc.player.yaw = yaw;
		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookOnly(yaw, pitch, mc.player.isOnGround()));
		mc.cameraEntity.pitch = mc.player.prevPitch;
		mc.cameraEntity.yaw = mc.player.prevYaw;
	}

	private final LivingEntity getTarget() {
		LivingEntity filteredTarget = null;
		boolean swap = false;
		for (final LivingEntity target : getTargets()) {
			if (filteredTarget == null) {
				filteredTarget = target;
			} else {
				if (sort.is("distance")) {
					swap = mc.player.squaredDistanceTo(target) < mc.player.squaredDistanceTo(filteredTarget);
				} else if (sort.is("health")) {
					swap = target.getHealth() < filteredTarget.getHealth();
				}
				if (swap) {
					filteredTarget = target;
				}
			}
		}
		return filteredTarget;
	}

	private final List<LivingEntity> getTargets() {
		final List<LivingEntity> targets = StreamSupport.stream(mc.world.getEntities().spliterator(), false).filter(LivingEntity.class::isInstance).map(entity -> (LivingEntity) entity).collect(Collectors.toList());
		return targets.stream().filter(e -> e != mc.player && e.isInRange(mc.player, range.getValue()) && e.hurtTime <= 0 && !e.isDead()).collect(Collectors.toList());
	}

	private final float attackDamage(final LivingEntity target) {
		float damage, ench, cool;
		damage = attackDamage();
		ench = EnchantmentHelper.getAttackDamage(mc.player.getMainHandStack(), target.getGroup());
		cool = mc.player.getAttackCooldownProgress(0.5F);
		damage *= 0.2F + cool * cool * 0.8F;
		ench *= cool;
		final boolean crit = (cool > 0.9F && mc.player.fallDistance > 0.0F && !mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater() && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) && !mc.player.hasVehicle()
				&& target instanceof LivingEntity) && !mc.player.isSprinting();
		if (crit) {
			damage *= 1.5F;
		}
		damage += ench;
		return damage;
	}

	private final float attackDamage() {
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
}