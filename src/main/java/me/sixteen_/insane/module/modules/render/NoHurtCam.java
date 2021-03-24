package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.event.GameRendererHurtCallback;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class NoHurtCam extends Module {

	public NoHurtCam() {
		super(NoHurtCam.class.getSimpleName());
		GameRendererHurtCallback.EVENT.register((matrixStack, f) -> {
			if (isEnabled()) {
				return true;
			}
			return false;
		});
	}
}