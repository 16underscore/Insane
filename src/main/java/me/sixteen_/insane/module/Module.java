package me.sixteen_.insane.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.sixteen_.insane.value.Value;
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
	private final boolean visible;
	private List<Value> values = new ArrayList<Value>();
	private boolean enabled;
	private InputUtil.Key keybind;
	protected final MinecraftClient mc;

	public Module(final String moduleName, final boolean moduleVisible) {
		name = moduleName;
		visible = moduleVisible;
		enabled = false;
		keybind = InputUtil.UNKNOWN_KEY;
		mc = MinecraftClient.getInstance();
	}

	public Module(final String moduleName) {
		this(moduleName, true);
	}

	/**
	 * adds a value.
	 * 
	 * @param needs a value
	 */
	public void addValues(final Value... values) {
		this.values.addAll(Arrays.asList(values));
	}

	/**
	 * @param needs the name of the value
	 * @return the value
	 */
	public final Value getValueByName(final String valueName) {
		for (final Value v : values) {
			if (v.getName().equalsIgnoreCase(valueName)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Toggles the module
	 */
	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	/**
	 * @return the module name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return module name with some values
	 */
	public String getNameWithValue() {
		final StringBuilder build = new StringBuilder();
		final Iterator<Value> it = values.stream().filter(value -> value.isVisibleInArrayList()).iterator();
		while (it.hasNext()) {
			final Value v = it.next();
			if (it.hasNext()) {
				build.append(String.format("%s, ", v.toString()));
			} else {
				build.append(v.toString());
			}
		}
		if (build.isEmpty()) {
			return getName();
		}
		return String.format("%s \u00A77[%s]\u00A7r", getName(), build.toString());
	}

	/**
	 * @return a boolean if it should be displayed in the array list
	 */
	public final boolean isVisible() {
		return visible;
	}

	/**
	 * Checks if module is enabled.
	 * 
	 * @return a boolean if it is enabled.
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the keybind for the module.
	 * 
	 * @param key
	 */
	public final void setKeybind(final InputUtil.Key key) {
		keybind = key;
	}

	/**
	 * @return the bind for the module
	 */
	public final InputUtil.Key getKeybind() {
		return keybind;
	}

	/**
	 * Enables the module.
	 */
	protected final void enable() {
		enabled = true;
		onEnable();
	}

	/**
	 * Disables the module.
	 */
	protected final void disable() {
		enabled = false;
		onDisable();
	}

	/**
	 * Called on update.
	 */
	public void onUpdate() {
	}

	/**
	 * Called when the module is turned on.
	 */
	protected void onEnable() {
	}

	/**
	 * Called when the module is turned off.
	 */
	protected void onDisable() {
	}

	/**
	 * Called when minecraft gets closed.
	 */
	protected void onShutdown() {
	}
}