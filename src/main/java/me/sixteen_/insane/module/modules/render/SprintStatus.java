package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class SprintStatus extends Module {

	public SprintStatus() {
		super("SprintStatus", ModuleCategory.RENDER, false);
	}

	public void onUpdate(final MatrixStack matrices) {
		String status;
		if (mc.options.sprintToggled && mc.options.keySprint.isPressed()) {
			status = "Toggled";
		} else if (mc.options.keySprint.isPressed()) {
			status = "Key Held";
		} else if (mc.player.isSprinting()) {
			status = "Vanilla";
		} else {
			status = "Disabled";
		}
		status = String.format("[Sprinting (%s)]", status);
		mc.textRenderer.draw(matrices, status, 2, 2, -1);
	}
}