package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Inspect extends Module {

	private float distance;
	private float move;

	public Inspect() {
		super("Inspect", ModuleCategory.RENDER, false);
	}

	@Override
	protected void onEnable() {
		distance = 0.0F;
		move = 0.0F;
	}

	@Override
	public void toggle() {
		enable();
	}

	public void onUpdate(final MatrixStack matrices) {
		if (distance < 30.0F) {
			distance += 0.005F;
			final float f = MathHelper.sin((3.1415F / 2) * distance);
			if (move < 22.5F) {
				move = f * 22.5F;
			}
			final float pivotX = 1.0F;
			final float pivotY = 1.0F;
			final float pivotZ = 0.5F;
			// Destination
			matrices.translate(0F, 0F, 0F);
			matrices.multiply(new Quaternion(0F, 0F, move, true));
			// Pivot
			matrices.translate(pivotX, pivotY, pivotZ);
			// Rotate
			matrices.multiply(new Quaternion(new Vector3f(1F, 1F, 0F), 30F * f, true));
			// -Pivot
			matrices.translate(-pivotX, -pivotY, -pivotZ);
		} else {
			disable();
		}
	}

	public void disable(final float swingProgress, final float equipProgress) {
		if (swingProgress != 0F || equipProgress != 0F) {
			disable();
		}
	}
}