package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.ntrfc.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

/**
 * @author 16_
 */
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {

	@Shadow
	private Session session;
	
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(final CallbackInfo info) {
		new Insane();
	}

	@Override
	public void setSession(Session session) {
		this.session = session;
	}
}