package me.sixteen_.insane.module.modules;

import me.sixteen_.insane.event.HeldItemRendererFirstPersonItemCallback;
import me.sixteen_.insane.event.ItemRendererItemCallback;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Inspect extends Module {

	private float distance, move;

	public Inspect() {
		super(Inspect.class.getSimpleName(), false);
		ItemRendererItemCallback.EVENT.register((stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model) -> {
			if (isEnabled() && renderMode.isFirstPerson()) {
				onUpdate(matrices);
			}
		});
		HeldItemRendererFirstPersonItemCallback.EVENT.register((player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light) -> {
			if (isEnabled()) {
				disable(swingProgress, equipProgress);
			}
		});
	}

	@Override
	public final void toggle() {
		enable();
	}

	@Override
	protected final void onEnable() {
		distance = 0.0F;
		move = 0.0F;
	}

	private final void onUpdate(final MatrixStack matrices) {
		if (distance < 30.0F) {
			distance += 0.005F;
			final float f = MathHelper.sin((3.1415F / 2) * distance);
			if (move < 22.5F) {
				move = f * 22.5F;
			}
			final float pivotX = 1.0F, pivotY = 1.0F, pivotZ = 0.5F;
			// Destination
			matrices.translate(0F, 0F, 0F);
			matrices.multiply(new Quaternion(0F, 0F, move, true));
			// Pivot
			matrices.translate(pivotX, pivotY, pivotZ);
			// Rotate
			matrices.multiply(new Quaternion(new Vec3f(1F, 1F, 0F), 30F * f, true));
			// -Pivot
			matrices.translate(-pivotX, -pivotY, -pivotZ);
		} else {
			disable();
		}
	}

	/**
	 * Disables module when player swings hand or player equips another item.
	 * 
	 * @param swingProgress
	 * @param equipProgress
	 */
	private final void disable(final float swingProgress, final float equipProgress) {
		if (swingProgress != 0F || equipProgress != 0F) {
			disable();
		}
	}
}