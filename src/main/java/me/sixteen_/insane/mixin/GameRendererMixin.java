package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.modules.render.NoHurtCam;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

	@Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
	private final void bobViewWhenHurt(MatrixStack matrixStack, float f, final CallbackInfo info) {
		if (Insane.getInstance().getModuleManager().getModule(NoHurtCam.class).isEnabled()) {
			info.cancel();
		}
	}
}