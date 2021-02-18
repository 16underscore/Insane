package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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
	public final void setSession(Session session) {
		this.session = session;
	}
}