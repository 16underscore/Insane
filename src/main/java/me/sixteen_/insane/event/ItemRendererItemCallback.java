package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ItemRendererItemCallback {

	public Event<ItemRendererItemCallback> EVENT = EventFactory.createArrayBacked(ItemRendererItemCallback.class, (listeners) -> (stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model) -> {
		for (final ItemRendererItemCallback event : listeners) {
			event.renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
		}
	});

	public void renderItem(final ItemStack stack, final ModelTransformation.Mode renderMode, final boolean leftHanded, final MatrixStack matrices, final VertexConsumerProvider vertexConsumers, final int light, final int overlay, final BakedModel model);
}