package me.sixteen_.insane.module.modules;

import java.util.List;

import me.sixteen_.insane.module.Module;
import net.minecraft.client.util.ClientPlayerTickable;

public abstract class ModuleTickable extends Module implements ClientPlayerTickable {

	private List<ClientPlayerTickable> tickables;

	public ModuleTickable(final String moduleName) {
		super(moduleName);
	}

	public final void setTickables(final List<ClientPlayerTickable> tickables) {
		this.tickables = tickables;
	}

	@Override
	protected final void onEnable() {
		tickables.add(this);
	}

	@Override
	protected final void onDisable() {
		tickables.remove(this);
	}

	@Override
	public final void tick() {
		onUpdate();
	}
}