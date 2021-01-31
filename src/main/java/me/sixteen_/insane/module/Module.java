package me.sixteen_.insane.module;

import net.minecraft.client.util.InputUtil;

/**
 * @author 16_
 */
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
		enabled = !enabled;
		if (enabled) {
			onEnable();
		} else {
			onDisable();
		}
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
	
	public void onUpdate() {}
	protected void onEnable() {}
	protected void onDisable() {}
	protected void onShutdown() {}
}