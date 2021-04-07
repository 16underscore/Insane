package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.event.ClientPlayerApplyDamageEvents;
import me.sixteen_.insane.event.ClientPlayerMoveCallback;
import me.sixteen_.insane.event.ClientPlayerTickCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

	@Inject(method = "tick", at = @At("RETURN"))
	private final void tick(final CallbackInfo info) {
		ClientPlayerTickCallback.EVENT.invoker().tick();
	}

	@Inject(method = "move", at = @At("HEAD"))
	private final void move(MovementType type, Vec3d movement, final CallbackInfo info) {
		ClientPlayerMoveCallback.EVENT.invoker().move(type, movement);
	}

	@Inject(method = "applyDamage", at = @At("HEAD"))
	private final void beforeApplyDamage(DamageSource source, float amount, final CallbackInfo info) {
		ClientPlayerApplyDamageEvents.BEFORE.invoker().beforeApplyDamage(source, amount);
	}
	
	@Inject(method = "applyDamage", at = @At("RETURN"))
	private final void afterApplyDamage(DamageSource source, float amount, final CallbackInfo info) {
		ClientPlayerApplyDamageEvents.AFTER.invoker().afterApplyDamage(source, amount);
	}
}