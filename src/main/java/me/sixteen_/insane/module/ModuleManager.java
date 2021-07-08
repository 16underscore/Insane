package me.sixteen_.insane.module;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import me.sixteen_.insane.event.OnKeyCallback;
import me.sixteen_.insane.module.modules.ArrayList;
import me.sixteen_.insane.module.modules.AutoSoup;
import me.sixteen_.insane.module.modules.Criticals;
import me.sixteen_.insane.module.modules.Fly;
import me.sixteen_.insane.module.modules.Inspect;
import me.sixteen_.insane.module.modules.Killaura;
import me.sixteen_.insane.module.modules.Nametags;
import me.sixteen_.insane.module.modules.NoHurtCam;
import me.sixteen_.insane.module.modules.Refill;
import me.sixteen_.insane.module.modules.SprintStatus;
import me.sixteen_.insane.module.modules.Trigger;
import me.sixteen_.insane.module.modules.WTap;
import me.sixteen_.insane.module.modules.Fullbright;
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
		addModule(new AutoSoup());
		addModule(new Killaura());
		addModule(new Nametags());
		addModule(new Inspect());
		addModule(new Trigger());
		addModule(new Refill());
		addModule(new WTap());
		addModule(new Fly());
		OnKeyCallback.EVENT.register((window, key, scancode, i, j) -> {
			if (i == GLFW.GLFW_PRESS) {
				for (final Module m : getModules()) {
					if (m.getKeybind() != null) {
						if (m.getKeybind().getCode() == key) {
							m.toggle();
						}
					}
				}
			}
		});
	}

	/**
	 * @return a List of {@link Module}
	 */
	public final List<Module> getModules() {
		return modules;
	}

	/**
	 * @param needs the name of the module
	 * @return the module that has the name
	 */
	public final Module getModuleByName(final String moduleName) {
		for (final Module m : getModules()) {
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
		for (final Module m : getModules()) {
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
		for (final Module m : getModules()) {
			m.onShutdown();
		}
	}

	/**
	 * adds a {@link Module}.
	 * 
	 * @param needs a {@link Module}
	 */
	private final void addModule(final Module module) {
		getModules().add(module);
	}
}