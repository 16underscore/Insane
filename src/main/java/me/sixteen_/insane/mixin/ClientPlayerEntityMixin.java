package me.sixteen_.insane.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.modules.ModuleTickable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

	@Shadow
	private List<ClientPlayerTickable> tickables;

	@Inject(method = "<init>", at = @At("RETURN"))
	private final void init(final CallbackInfo info) {
		for (final ModuleTickable m : Insane.getInstance().getModuleManager().getTickableModules()) {
			m.setTickables(tickables);
		}
	}
}