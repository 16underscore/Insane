package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.event.GameRendererHurtCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class NoHurtCam extends Module {

	public NoHurtCam() {
		super(NoHurtCam.class.getSimpleName(), ModuleCategory.RENDER);
		GameRendererHurtCallback.EVENT.register((matrixStack, f) -> {
			if (isEnabled()) {
				return true;
			}
			return false;
		});
	}
}