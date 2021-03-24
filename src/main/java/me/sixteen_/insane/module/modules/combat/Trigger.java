package me.sixteen_.insane.module.modules.combat;

import me.sixteen_.insane.event.ClientPlayerTickCallback;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Trigger extends Module {

	public Trigger() {
		super(Trigger.class.getSimpleName());
		ClientPlayerTickCallback.EVENT.register(() -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected final void onUpdate() {
		if (mc.player.getAttackCooldownProgress(0F) < 1F) {
			return;
		}
		if (mc.player.isDead()) {
			return;
		}
		if (!(mc.crosshairTarget.getType() == Type.ENTITY)) {
			return;
		}
		final Entity e = ((EntityHitResult) mc.crosshairTarget).getEntity();
		if (!(e instanceof LivingEntity)) {
			return;
		}
		final LivingEntity le = (LivingEntity) e;
		if (le.hurtTime > 0) {
			return;
		}
		if (le.isDead()) {
			return;
		}
		mc.interactionManager.attackEntity(mc.player, le);
		mc.player.swingHand(Hand.MAIN_HAND);
	}
}