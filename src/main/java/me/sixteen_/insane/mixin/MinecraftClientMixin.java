package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.ntrfc.IMinecraftClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {

	@Shadow
	private Session session;

	@Override
	public final void setSession(final Session session) {
		this.session = session;
	}
	
	@Inject(method = "joinWorld", at = @At("RETURN"))
	private final void joinWorld(final CallbackInfo info) {
		final Insane insane = Insane.getInstance();
		if (insane.shouldLoadConfig()) {
			insane.getConfig().load();
			insane.dontLoadConfigAgain();
		}
	}
}