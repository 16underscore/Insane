package me.sixteen_.insane.module.modules;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.event.EntityRendererRenderCallback;
import me.sixteen_.insane.module.Module;
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
public final class Nametags extends Module {

	public Nametags() {
		super(Nametags.class.getSimpleName());
		EntityRendererRenderCallback.EVENT.register((entity, yaw, tickDelta, matrices, vertexConsumers, light, dispatcher) -> {
			if (isEnabled()) {
				return onUpdate(entity, matrices, vertexConsumers, light, dispatcher);
			}
			return false;
		});
	}

	private final boolean onUpdate(final Entity entity, final MatrixStack matrices, final VertexConsumerProvider vertexConsumers, final int light, final EntityRenderDispatcher dispatcher) {
		if (entity instanceof PlayerEntity) {
			final PlayerEntity pe = (PlayerEntity) entity;
			final String name = String.format("%s %s%s\u00A7r", pe.getName().asString(), Insane.getInstance().getClientColor(), (int) pe.getHealth());
			final double d = dispatcher.getSquaredDistanceToCamera(pe);
			if (d <= 4096D) {
				final TextRenderer textRenderer = mc.textRenderer;
				final float f = entity.getHeight() + 0.5F, h = (float) (-textRenderer.getWidth(name) / 2);
				matrices.push();
				matrices.translate(0.0D, (double) f, 0.0D);
				matrices.multiply(dispatcher.getRotation());
				matrices.scale(-0.025F, -0.025F, 0.025F);
				final Matrix4f matrix4f = matrices.peek().getModel();
				textRenderer.draw(name, h, 0, -1, false, matrix4f, vertexConsumers, true, 0, light);
				matrices.pop();
			}
			return true;
		}
		return false;
	}
}