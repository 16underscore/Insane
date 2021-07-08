package me.sixteen_.insane.module.modules;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.value.values.DoubleValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Fullbright extends Module {

	private final DoubleValue gamma;
	private double previousGamma;

	public Fullbright() {
		super(Fullbright.class.getSimpleName());
		gamma = new DoubleValue("gamma", true, 69.0D, 0.0D, 1023.0D, 0.1D);
		addValues(gamma);
	}

	@Override
	public final void onUpdateValue() {
		mc.options.gamma = gamma.getValue();
	}

	@Override
	protected final void onEnable() {
		previousGamma = mc.options.gamma;
		onUpdateValue();
	}

	@Override
	protected final void onDisable() {
		mc.options.gamma = previousGamma;
	}

	@Override
	protected final void onShutdown() {
		mc.options.gamma = previousGamma;
	}
}