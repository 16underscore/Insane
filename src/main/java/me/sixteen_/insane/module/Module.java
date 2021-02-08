package me.sixteen_.insane.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.InputUtil;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public abstract class Module {

	private final ModuleCategory category;
	private final String name;
	private final boolean visible;
	private InputUtil.Key keybind;
	private boolean enabled;

	public Module(final String moduleName, final ModuleCategory moduleCategory) {
		visible = true;
		enabled = false;
		category = moduleCategory;
		name = moduleName;
		keybind = InputUtil.UNKNOWN_KEY;
	}

	public Module(final String moduleName, final ModuleCategory moduleCategory, final boolean moduleVisible) {
		visible = moduleVisible;
		enabled = false;
		category = moduleCategory;
		name = moduleName;
		keybind = InputUtil.UNKNOWN_KEY;
	}

	public void toggle() {
		if (enabled) {
			onDisable();
		} else {
			onEnable();
		}
		enabled = !enabled;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public String getName() {
		return name;
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

	public boolean isVisible() {
		return visible;
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
	
	public String getMode() {
		return null;
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