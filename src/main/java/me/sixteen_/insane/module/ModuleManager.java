package me.sixteen_.insane.module;

import java.util.List;

import me.sixteen_.insane.module.modules.ModuleTickable;
import me.sixteen_.insane.module.modules.combat.Criticals;
import me.sixteen_.insane.module.modules.combat.Killaura;
import me.sixteen_.insane.module.modules.combat.Trigger;
import me.sixteen_.insane.module.modules.movement.Fly;
import me.sixteen_.insane.module.modules.movement.Strafe;
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
	private final List<ModuleTickable> tickableModules = new java.util.ArrayList<ModuleTickable>();

	public ModuleManager() {
		addModule(new SprintStatus());
		addModule(new Fullbright());
		addModule(new ArrayList());
		addModule(new Criticals());
		addModule(new NoHurtCam());
		addModule(new Killaura());
		addModule(new Inspect());
		addModule(new Trigger());
		addModule(new Strafe());
		addModule(new Fly());
		modules.stream().filter(ModuleTickable.class::isInstance).forEach(m -> addTickableModule((ModuleTickable) m));
	}

	/**
	 * adds a {@link Module}.
	 * 
	 * @param needs a {@link Module}
	 */
	private final void addModule(final Module module) {
		modules.add(module);
	}

	/**
	 * adds a {@link ModuleTickable}.
	 * 
	 * @param needs a {@link ModuleTickable}
	 */
	private final void addTickableModule(final ModuleTickable module) {
		tickableModules.add(module);
	}

	/**
	 * @return a List of {@link Module}
	 */
	public final List<Module> getModules() {
		return modules;
	}

	/**
	 * @return a List of modules that extends {@link ModuleTickable}
	 */
	public final List<ModuleTickable> getTickableModules() {
		return tickableModules;
	}

	/**
	 * @param needs the name of the module
	 * @return the module that has the name
	 */
	public final Module getModuleByName(final String moduleName) {
		for (final Module m : modules) {
			if (m.getName().equalsIgnoreCase(moduleName)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * @param needs the class of the module
	 * @return the module
	 */
	public final Module getModule(final Class<? extends Module> moduleClass) {
		for (final Module m : modules) {
			if (m.getClass() == moduleClass) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Called when minecraft gets closed.
	 */
	public final void shutdown() {
		for (final Module m : modules) {
			m.onShutdown();
		}
	}
}