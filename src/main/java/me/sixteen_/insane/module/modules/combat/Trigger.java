package me.sixteen_.insane.module.modules.combat;

import java.util.List;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Trigger extends Module implements ClientPlayerTickable {

	private List<ClientPlayerTickable> tickables;

	public Trigger() {
		super("Trigger", ModuleCategory.COMBAT);
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