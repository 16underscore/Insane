package me.sixteen_.insane.module;

import java.util.List;

import me.sixteen_.insane.module.modules.combat.Criticals;
import me.sixteen_.insane.module.modules.combat.Killaura;
import me.sixteen_.insane.module.modules.combat.Trigger;
import me.sixteen_.insane.module.modules.movement.Fly;
import me.sixteen_.insane.module.modules.render.ArrayList;
import me.sixteen_.insane.module.modules.render.Fullbright;
import me.sixteen_.insane.module.modules.render.Inspect;
import me.sixteen_.insane.module.modules.render.NoHurtCam;
import me.sixteen_.insane.module.modules.render.SprintStatus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ModuleManager {

	private final List<Module> modules = new java.util.ArrayList<Module>();

	public ModuleManager() {
		addModule(new SprintStatus());
		addModule(new Fullbright());
		addModule(new ArrayList());
		addModule(new Criticals());
		addModule(new NoHurtCam());
		addModule(new Killaura());
		addModule(new Inspect());
		addModule(new Trigger());
		addModule(new Fly());
	}

	private final void addModule(final Module module) {
		modules.add(module);
	}

	public final List<Module> getModules() {
		return modules;
	}

	public final Module getModuleByName(final String moduleName) {
		for (final Module m : modules) {
			if (m.getName().equalsIgnoreCase(moduleName)) {
				return m;
			}
		}
		return null;
	}

	public final Module getModule(final Class<? extends Module> moduleClass) {
		for (final Module m : modules) {
			if (m.getClass() == moduleClass) {
				return m;
			}
		}
		return null;
	}

	public final void shutdown() {
		for (final Module m : modules) {
			m.onShutdown();
		}
	}
}