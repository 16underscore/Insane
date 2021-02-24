package me.sixteen_.insane.config;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.sixteen_.insane.Insane;
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
					value.addProperty(v.getName(), v.toString());
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