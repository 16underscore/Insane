package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class SprintStatus extends Module {

	private MinecraftClient mc;
	private TextRenderer tr;

	public SprintStatus() {
		super("SprintStatus", ModuleCategory.RENDER, false);
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		tr = mc.textRenderer;
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
		tr.draw(matrices, status, 2, 2, -1);
	}
}