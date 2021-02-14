package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Fullbright extends Module {

	private double previousGamma;

	public Fullbright() {
		super("Fullbright", ModuleCategory.RENDER);
	}

	@Override
	protected void onEnable() {
		previousGamma = mc.options.gamma;
		mc.options.gamma = 69.0D;
	}

	@Override
	protected void onDisable() {
		mc.options.gamma = previousGamma;
	}

	@Override
	protected void onShutdown() {
		mc.options.gamma = previousGamma;
	}
}