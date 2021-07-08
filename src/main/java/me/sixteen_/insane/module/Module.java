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

	protected final MinecraftClient mc;
	private final String name;
	private final boolean visible;
	private List<Value> values = new ArrayList<Value>();
	private String nameWithValue;
	private boolean enabled;
	private InputUtil.Key keybind;

	public Module(final String moduleName, final boolean moduleVisible) {
		name = moduleName;
		nameWithValue = name;
		visible = moduleVisible;
		enabled = false;
		keybind = null;
		mc = MinecraftClient.getInstance();
	}

	public Module(final String name) {
		this(name, true);
	}

	/**
	 * adds a value.
	 * 
	 * @param needs a value
	 */
	public final void addValues(final Value... values) {
		this.values.addAll(Arrays.asList(values));
	}

	/**
	 * @return a list with all values the module has.
	 */
	public final List<Value> getValues() {
		return values;
	}

	/**
	 * @return a boolean if it has values
	 */
	public final boolean hasValues() {
		return !values.isEmpty();
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
	 * 
	 */
	public final void updateNameWithValue() {
		if (hasValues()) {
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
			nameWithValue = build.length() == 0 ? getName() : String.format("%s \u00A77[%s]\u00A7r", getName(), build.toString());
		}
	}

	/**
	 * @return module name with some values
	 */
	public final String getNameWithValue() {
		return nameWithValue;
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
	 * @return a boolean if it has a keybind
	 */
	public final boolean hasKeybind() {
		return keybind != null;
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
	public final void enable() {
		enabled = true;
		onEnable();
	}

	/**
	 * Disables the module.
	 */
	public final void disable() {
		enabled = false;
		onDisable();
	}

	/**
	 * Called on update.
	 */
	protected void onUpdate() {
	}

	/**
	 * Called on update value.
	 */
	public void onUpdateValue() {
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