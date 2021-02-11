package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.modules.combat.Criticals;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerInteractionManager;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

	@Inject(method = "attackEntity", at = @At("HEAD"))
	private void attackEntity(final CallbackInfo info) {
		final Module criticals = Insane.getInsane().getModuleManager().getModule(Criticals.class);
		if (criticals.isEnabled()) {
			criticals.onUpdate();
		}
	}
}