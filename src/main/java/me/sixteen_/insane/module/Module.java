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
	private final ModuleCategory category;
	private final boolean visible;
	private List<Value> values = new ArrayList<Value>();
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

	public void addValues(final Value... values) {
		this.values.addAll(Arrays.asList(values));
	}

	public final Value getValueByName(final String valueName) {
		for (final Value v : values) {
			if (v.getName().equalsIgnoreCase(valueName)) {
				return v;
			}
		}
		return null;
	}

	public void toggle() {
		enabled = !enabled;
		if (enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public final String getName() {
		return name;
	}

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

	public final ModuleCategory getCategory() {
		return category;
	}

	public final boolean isVisible() {
		return visible;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final void setKeybind(final InputUtil.Key key) {
		keybind = key;
	}

	public final InputUtil.Key getKeybind() {
		return keybind;
	}

	protected final void enable() {
		enabled = true;
		onEnable();
	}

	protected final void disable() {
		enabled = false;
		onDisable();
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