package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import me.sixteen_.insane.utils.Logger;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
public class Fullbright extends Module {

	private Mode mode;
	private MinecraftClient mc;
	private double previousGamma;

	public Fullbright() {
		super("Fullbright", ModuleCategory.RENDER);
		mode = Mode.GAMMA;
	}

	@Override
	protected void onEnable() {
		switch (mode) {
		case GAMMA:
			gamma();
			break;
		case NIGHT_VISION:
			night();
			break;
		}
	}

	private void gamma() {
		mc = MinecraftClient.getInstance();
		previousGamma = mc.options.gamma;
		mc.options.gamma = 69.0D;
	}

	private void night() {
	}

	@Override
	protected void onDisable() {
		mc.options.gamma = previousGamma;
	}

	@Override
	protected void onShutdown() {
		mc.options.gamma = previousGamma;
	}

	@Override
	public void setMode(String s) {
		for (Mode m : Mode.values()) {
			if (m.toString().equalsIgnoreCase(s)) {
				onDisable();
				mode = m;
				Logger.getLogger().addChatMessage(String.format("Mode %s activated", s));
				onEnable();
				return;
			}
		}
		Logger.getLogger().addChatMessage(String.format("Mode %s not found!", s));
	}

	private enum Mode {

		GAMMA("Gamma"), NIGHT_VISION("Nightvision");

		private final String modeName;

		private Mode(String modeName) {
			this.modeName = modeName;
		}

		@Override
		public String toString() {
			return modeName;
		}
	}
}