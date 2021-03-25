package me.sixteen_.insane.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.modules.render.ArrayList;
import me.sixteen_.insane.value.Value;
import me.sixteen_.insane.value.ranges.IntegerRange;
import me.sixteen_.insane.value.values.BooleanValue;
import me.sixteen_.insane.value.values.DoubleValue;
import me.sixteen_.insane.value.values.FloatValue;
import me.sixteen_.insane.value.values.IntegerValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.InputUtil;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Config {

	private final Insane insane;
	private final Gson gson;
	private final File file;
	private final String sEnabled = "enabled", sValues = "values", sKeybind = "keybind", sModules = "modules";

	public Config() {
		insane = Insane.getInstance();
		gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
		file = new File(FabricLoader.getInstance().getConfigDir().toFile(), String.format("%s.json", insane.getClientName()));
	}

	/**
	 * Saves the config.
	 */
	public final void save() {
		final JsonObject config = new JsonObject();
		final JsonObject modules = new JsonObject();
		insane.getModuleManager().getModules().forEach(m -> {
			final JsonObject module = new JsonObject();
			module.addProperty(sEnabled, m.isEnabled());
			if (m.hasValues()) {
				final JsonObject values = new JsonObject();
				m.getValues().forEach(v -> {
					if (v instanceof IntegerValue) {
						values.addProperty(v.getName(), ((IntegerValue) v).getValue());
					} else if (v instanceof FloatValue) {
						values.addProperty(v.getName(), ((FloatValue) v).getValue());
					} else if (v instanceof DoubleValue) {
						values.addProperty(v.getName(), ((DoubleValue) v).getValue());
					} else if (v instanceof BooleanValue) {
						values.addProperty(v.getName(), ((BooleanValue) v).getValue());
					} else if (v instanceof ListValue) {
						values.addProperty(v.getName(), ((ListValue) v).getValue());
					} else if (v instanceof IntegerRange) {
						final JsonArray range = new JsonArray();
						range.add(((IntegerRange) v).getMinValue());
						range.add(((IntegerRange) v).getMaxValue());
						values.add(v.getName(), range);
					}
				});
				module.add(sValues, values);
			}
			if (m.hasKeybind()) {
				module.addProperty(sKeybind, m.getKeybind().getCode());
			}
			modules.add(m.getName(), module);
		});
		config.add(sModules, modules);
		final String json = gson.toJson(config);
		try (final FileWriter fw = new FileWriter(file)) {
			fw.write(json);
		} catch (Exception e) {
		}
	}

	/**
	 * Loads the config.
	 */
	public final void load() {
		if (!file.exists()) {
			save();
		} else if (file.exists()) {
			try {
				final BufferedReader br = new BufferedReader(new FileReader(file));
				final JsonObject config = new JsonParser().parse(br).getAsJsonObject();
				if (config.has(sModules)) {
					Module arrayList = null;
					final JsonObject modules = (JsonObject) config.get(sModules);
					for (final Module m : insane.getModuleManager().getModules()) {
						if (modules.has(m.getName())) {
							final JsonObject module = (JsonObject) modules.get(m.getName());
							// Set values
							if (module.has(sValues)) {
								final JsonObject values = (JsonObject) module.get(sValues);
								for (final Value v : m.getValues()) {
									if (values.has(v.getName())) {
										final JsonPrimitive value = (JsonPrimitive) values.get(v.getName());
										if (v instanceof IntegerValue) {
											((IntegerValue) v).setValue(value.getAsInt());
										} else if (v instanceof FloatValue) {
											((FloatValue) v).setValue(value.getAsFloat());
										} else if (v instanceof DoubleValue) {
											((DoubleValue) v).setValue(value.getAsDouble());
										} else if (v instanceof BooleanValue) {
											((BooleanValue) v).setValue(value.getAsBoolean());
										} else if (v instanceof ListValue) {
											((ListValue) v).setValue(value.getAsString());
										} else if (v instanceof IntegerRange) {
											final JsonArray range = new JsonArray();
											((IntegerRange) v).setMinValue(range.get(0).getAsInt());
											((IntegerRange) v).setMaxValue(range.get(1).getAsInt());
										}
									}
								}
							}
							// Set keybind
							if (module.has(sKeybind)) {
								final JsonPrimitive keybind = (JsonPrimitive) module.get(sKeybind);
								m.setKeybind(InputUtil.fromKeyCode(keybind.getAsInt(), keybind.getAsInt()));
							}
							// Enable
							if (module.has(sEnabled)) {
								final JsonPrimitive enabled = (JsonPrimitive) module.get(sEnabled);
								if (enabled.getAsBoolean()) {
									if (!m.isEnabled()) {
										if (m instanceof ArrayList) {
											arrayList = m;
										} else {
											m.enable();
										}
									}
								}
							}
						}
					}
					if (arrayList != null) {
						arrayList.toggle();
					}
				}
			} catch (FileNotFoundException e) {
			}
		}
	}
}