package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface HeldItemRendererFirstPersonItemCallback {

	public Event<HeldItemRendererFirstPersonItemCallback> EVENT = EventFactory.createArrayBacked(HeldItemRendererFirstPersonItemCallback.class,
			(listeners) -> (player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light) -> {
				for (final HeldItemRendererFirstPersonItemCallback event : listeners) {
					event.renderFirstPersonItem(player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light);
				}
			});

	public void renderFirstPersonItem(final AbstractClientPlayerEntity player, final float tickDelta, final float pitch, final Hand hand, final float swingProgress, final ItemStack item, final float equipProgress, final MatrixStack matrices,
			final VertexConsumerProvider vertexConsumers, final int light);
}