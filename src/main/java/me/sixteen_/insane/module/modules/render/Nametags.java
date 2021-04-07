package me.sixteen_.insane.module.modules.render;

import me.sixteen_.insane.event.EntityRendererRenderCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Matrix4f;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class Nametags extends Module {

	public Nametags() {
		super(Nametags.class.getSimpleName(), ModuleCategory.RENDER);
		EntityRendererRenderCallback.EVENT.register((entity, yaw, tickDelta, matrices, vertexConsumers, light, dispatcher) -> {
			if (isEnabled()) {
				return onUpdate(entity, matrices, vertexConsumers, light, dispatcher);
			}
			return false;
		});
	}

	private final boolean onUpdate(Entity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher dispatcher) {
		if (entity instanceof PlayerEntity) {
			final PlayerEntity pe = (PlayerEntity) entity;
			final String name = String.format("%s %s", pe.getName().asString(), (int) pe.getHealth());
			double d = dispatcher.getSquaredDistanceToCamera(pe);
			if (d <= 4096.0D) {
				final float f = entity.getHeight() + 0.5F;
				matrices.push();
				matrices.translate(0.0D, (double) f, 0.0D);
				matrices.multiply(dispatcher.getRotation());
				matrices.scale(-0.025F, -0.025F, 0.025F);
				final Matrix4f matrix4f = matrices.peek().getModel();
				final TextRenderer textRenderer = mc.textRenderer;
				float h = (float) (-textRenderer.getWidth(name) / 2);
				textRenderer.draw(name, h, 0, -1, false, matrix4f, vertexConsumers, true, 0, light);
				matrices.pop();
			}
			return true;
		}
		return false;
	}
}