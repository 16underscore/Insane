package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.ModuleManager;
import me.sixteen_.insane.module.modules.render.ArrayList;
import me.sixteen_.insane.module.modules.render.SprintStatus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

	@Inject(method = "render", at = @At("HEAD"))
	private void render(MatrixStack matrices, float tickDelta, final CallbackInfo info) {
		final ModuleManager mm = Insane.getInstance().getModuleManager();
		final ArrayList arrayList = (ArrayList) mm.getModule(ArrayList.class);
		final SprintStatus sprintStatus = (SprintStatus) mm.getModule(SprintStatus.class);
		if (arrayList.isEnabled()) {
			arrayList.onUpdate(matrices);
		}
		if (sprintStatus.isEnabled()) {
			sprintStatus.onUpdate(matrices);
		}
	}
}