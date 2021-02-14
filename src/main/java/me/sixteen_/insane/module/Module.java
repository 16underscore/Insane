package me.sixteen_.insane.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Module {

	private final String name;
	private final ModuleCategory category;
	private final boolean visible;
	private boolean enabled;
	private InputUtil.Key keybind;
	protected final MinecraftClient mc;

	public Module(final String moduleName, final ModuleCategory moduleCategory, final boolean moduleVisible) {
		name = moduleName;
		category = moduleCategory;
		visible = moduleVisible;
		enabled = false;
		keybind = InputUtil.UNKNOWN_KEY;
		mc = MinecraftClient.getInstance();
	}

	public Module(final String moduleName, final ModuleCategory moduleCategory) {
		this(moduleName, moduleCategory, true);
	}

	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public String getName() {
		return name;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setKeybind(final InputUtil.Key key) {
		keybind = key;
	}

	public InputUtil.Key getKeybind() {
		return keybind;
	}

	protected void enable() {
		enabled = true;
		onEnable();
	}

	protected void disable() {
		enabled = false;
		onDisable();
	}

	public void setMode(final String s) {
	}

	public void onUpdate() {
	}

	protected void onEnable() {
	}

	protected void onDisable() {
	}

	protected void onShutdown() {
	}
}