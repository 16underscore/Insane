package me.sixteen_.insane.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.ModuleManager;
import me.sixteen_.insane.module.modules.combat.Killaura;
import me.sixteen_.insane.module.modules.combat.Trigger;
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
		final ModuleManager mm = Insane.getInstance().getModuleManager();
		final Killaura killaura = (Killaura) mm.getModule(Killaura.class);
		final Trigger trigger = (Trigger) mm.getModule(Trigger.class);
		killaura.setTickables(tickables);
		trigger.setTickables(tickables);
	}
}