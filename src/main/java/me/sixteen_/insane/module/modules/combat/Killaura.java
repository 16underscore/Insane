package me.sixteen_.insane.module.modules.combat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import me.sixteen_.insane.event.ClientPlayerTickCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import me.sixteen_.insane.value.values.FloatValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Killaura extends Module {

	private final ListValue mode, sort;
	private final FloatValue range;
	private final float pi = 3.14159265F, radiansToDegrees = 180 / pi;

	public Killaura() {
		super(Killaura.class.getSimpleName(), ModuleCategory.COMBAT);
		mode = new ListValue("mode", true, "legit", "single", "multi");
		sort = new ListValue("sort", false, "distance", "health");
		range = new FloatValue("range", true, 3.7F, 3F, 6F, 0.1F);
		addValues(mode);
		addValues(sort);
		addValues(range);
		ClientPlayerTickCallback.EVENT.register(() -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected final void onUpdate() {
		if (mode.is("single")) {
			singleAura();
		} else if (mode.is("multi")) {
			multiAura();
		} else if (mode.is("legit")) {
			legitAura();
		}
	}

	/**
	 * Just a method for attacking that also swings the hand.
	 * 
	 * @param needs the living entity target
	 */
	private final void attack(final LivingEntity target) {
		mc.interactionManager.attackEntity(mc.player, target);
		mc.player.swingHand(Hand.MAIN_HAND);
	}

	/**
	 * Normal killaura
	 */
	private final void singleAura() {
		final LivingEntity target = getTarget();
		if (target == null) {
			return;
		}
		if (mc.player.getAttackCooldownProgress(0F) >= 1F) {
			attack(target);
		}
	}

	/**
	 * Multiaura to destroy everything.
	 */
	private final void multiAura() {
		if (mc.player.getAttackCooldownProgress(0F) >= 1F) {
			for (final LivingEntity target : getTargets()) {
				attack(target);
			}
		}
	}

	/**
	 * Killaura that should be a little bit legit.
	 */
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
		attack(target);
	}

	/**
	 * Get living targets near the player.
	 * 
	 * @return a List with valid targets
	 */
	private final List<LivingEntity> getTargets() {
		final List<LivingEntity> targets = StreamSupport.stream(mc.world.getEntities().spliterator(), false).filter(LivingEntity.class::isInstance).map(entity -> (LivingEntity) entity).collect(Collectors.toList());
		return targets.stream().filter(e -> e != mc.player && e.isInRange(mc.player, range.getValue()) && e.hurtTime <= 0 && !e.isDead()).collect(Collectors.toList());
	}

	/**
	 * Get a specific target.
	 * 
	 * @return a living entity
	 */
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

	/**
	 * Makes the player look at a living entity.
	 * 
	 * @param needs a living entity. For example the target
	 */
	private final void lookAtTarget(final LivingEntity le) {
		final Vec3d playerPos = mc.player.getPos(), entityPos = le.getPos();
		double deltaX, deltaY, deltaZ, distanceXZ;
		deltaX = entityPos.getX() - playerPos.getX();
		deltaY = (entityPos.getY() + le.getEyeHeight(le.getPose())) - (playerPos.getY() + mc.player.getEyeHeight(mc.player.getPose()));
		deltaZ = entityPos.getZ() - playerPos.getZ();
		distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		final float pitch = MathHelper.wrapDegrees(((float) (-MathHelper.atan2(deltaY, distanceXZ))) * radiansToDegrees), yaw = MathHelper.wrapDegrees(((float) MathHelper.atan2(deltaZ, deltaX)) * radiansToDegrees - 90F);
		mc.player.setPitch(pitch);
		mc.player.setYaw(yaw);
		mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, mc.player.isOnGround()));
		mc.cameraEntity.setPitch(mc.player.prevPitch);
		mc.cameraEntity.setYaw(mc.player.prevYaw);
	}
}