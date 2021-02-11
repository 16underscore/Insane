package me.sixteen_.insane.module.modules.combat;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Trigger extends Module implements ClientPlayerTickable {

	private MinecraftClient mc;
	private ClientPlayerEntity player;

	public Trigger() {
		super("Trigger", ModuleCategory.COMBAT);
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		player = mc.player;
	}

	@Override
	public void tick() {
		if (isEnabled()) {
			onUpdate();
		}
	}

	@Override
	public void onUpdate() {
		if (player.getAttackCooldownProgress(0F) < 1F) {
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
		mc.interactionManager.attackEntity(player, le);
	}
}