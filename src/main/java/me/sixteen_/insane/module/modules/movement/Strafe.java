package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.event.ClientPlayerMoveCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Strafe extends Module {

	// TODO strafe.
	public Strafe() {
		super(Strafe.class.getSimpleName(), ModuleCategory.MOVEMENT);
		ClientPlayerMoveCallback.EVENT.register((type, movement) -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected final void onUpdate() {
	}
}