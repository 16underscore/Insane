package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.event.ScreenSendMessageCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public abstract class ScreenMixin {

	@Inject(method = "sendMessage(Ljava/lang/String;Z)V", at = @At(target = "sendChatMessage", value = "INVOKE"), cancellable = true)
	private final void sendMessage(String message, boolean toHud, final CallbackInfo info) {
		final boolean cancel = ScreenSendMessageCallback.EVENT.invoker().sendMessage(message, toHud);
		if (cancel) {
			info.cancel();
		}
	}
}