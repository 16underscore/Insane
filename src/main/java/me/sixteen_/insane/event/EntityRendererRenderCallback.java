package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface EntityRendererRenderCallback {

	public Event<EntityRendererRenderCallback> EVENT = EventFactory.createArrayBacked(EntityRendererRenderCallback.class, (listeners) -> (entity, yaw, tickDelta, matrices, vertexConsumers, light, dispatcher) -> {
		for (final EntityRendererRenderCallback event : listeners) {
			final boolean cancel = event.render(entity, yaw, tickDelta, matrices, vertexConsumers, light, dispatcher);
			if (cancel) {
				return true;
			}
		}
		return false;
	});

	public boolean render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher dispatcher);
}