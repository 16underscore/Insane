package me.sixteen_.insane.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.modules.render.Inspect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

	@Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
	private final void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, final CallbackInfo info) {
		final Inspect inspect = (Inspect) Insane.getInstance().getModuleManager().getModule(Inspect.class);
		if (inspect.isEnabled()) {
			inspect.disable(swingProgress, equipProgress);
		}
	}
}