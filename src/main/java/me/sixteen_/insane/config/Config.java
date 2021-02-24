package me.sixteen_.insane.config;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.value.ranges.IntegerRange;
import me.sixteen_.insane.value.values.BooleanValue;
import me.sixteen_.insane.value.values.DoubleValue;
import me.sixteen_.insane.value.values.FloatValue;
import me.sixteen_.insane.value.values.IntegerValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Config {

	private final Insane insane;
	private final File file;
	private final Gson gson;

	public Config() {
		insane = Insane.getInstance();
		gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
		file = new File(FabricLoader.getInstance().getConfigDir().toFile(), String.format("%s.json", insane.getClientName()));
		load();
	}

	public final void save() {
		final JsonObject config = new JsonObject();
		final JsonObject modules = new JsonObject();
		insane.getModuleManager().getModules().forEach(m -> {
			final JsonObject module = new JsonObject();
			module.addProperty("enabled", m.isEnabled());
			if (m.hasValues()) {
				final JsonObject value = new JsonObject();
				m.getValues().forEach(v -> {
					if (v instanceof IntegerValue) {
						value.addProperty(v.getName(), ((IntegerValue) v).getValue());
					} else if (v instanceof FloatValue) {
						value.addProperty(v.getName(), ((FloatValue) v).getValue());
					} else if (v instanceof DoubleValue) {
						value.addProperty(v.getName(), ((DoubleValue) v).getValue());
					} else if (v instanceof BooleanValue) {
						value.addProperty(v.getName(), ((BooleanValue) v).getValue());
					} else if (v instanceof ListValue) {
						value.addProperty(v.getName(), ((ListValue) v).getValue());
					} else if (v instanceof IntegerRange) {
						final JsonArray range = new JsonArray();
						range.add(((IntegerRange) v).getMinValue());
						range.add(((IntegerRange) v).getMaxValue());
						value.add(v.getName(), range);
					}
				});
				module.add("value", value);
			}
			modules.add(m.getName(), module);
		});
		config.add("modules", modules);
		final String json = gson.toJson(config);
		try (final FileWriter fw = new FileWriter(file)) {
			fw.write(json);
		} catch (Exception e) {
			System.err.println("Json go brrr");
		}
	}

	private final void load() {
		if (file.exists()) {
		} else {
			save();
		}
	}
}