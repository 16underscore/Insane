package me.sixteen_.insane.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

	@Inject(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;fromKeyCode(II)Lnet/minecraft/client/util/InputUtil$Key;", shift = At.Shift.AFTER))
	private void onKey(long window, int key, int scancode, int i, int j, final CallbackInfo callback) {
		if (i == GLFW.GLFW_PRESS) {
			final ModuleManager mm = Insane.getInstance().getModuleManager();
			for (Module m : mm.getModules()) {
				if (m.getKeybind().getCode() == key) {
					m.toggle();
				}
			}
		}
	}
}