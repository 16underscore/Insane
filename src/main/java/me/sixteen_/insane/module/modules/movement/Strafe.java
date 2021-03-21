package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.event.ClientPlayerMoveCallback;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Strafe extends Module {

	public Strafe() {
		super("Strafe");
		ClientPlayerMoveCallback.EVENT.register((type, movement) -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	public final void onUpdate() {
	}
}