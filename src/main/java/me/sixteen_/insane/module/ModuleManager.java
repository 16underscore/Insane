package me.sixteen_.insane.module;

import java.util.List;

import me.sixteen_.insane.module.modules.render.ArrayList;
import me.sixteen_.insane.module.modules.render.Fullbright;
import me.sixteen_.insane.module.modules.render.Inspect;
import me.sixteen_.insane.module.modules.render.SprintStatus;

/**
 * @author 16_
 */
public class ModuleManager {

	private final List<Module> modules = new java.util.ArrayList<Module>();

	public ModuleManager() {
		addModule(new SprintStatus());
		addModule(new Fullbright());
		addModule(new ArrayList());
		addModule(new Inspect());
	}

	private void addModule(final Module module) {
		modules.add(module);
	}

	public List<Module> getModules() {
		return modules;
	}

	public Module getModuleByName(final String moduleName) {
		for (final Module m : modules) {
			if (m.getName().equalsIgnoreCase(moduleName)) {
				return m;
			}
		}
		return null;
	}

	public Module getModule(final Class<? extends Module> moduleClass) {
		for (final Module m : modules) {
			if (m.getClass() == moduleClass) {
				return m;
			}
		}
		return null;
	}
	
	public void shutdown() {
		for (Module m : modules) {
			m.onShutdown();
		}
	}
}
