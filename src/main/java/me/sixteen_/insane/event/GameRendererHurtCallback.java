package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface GameRendererHurtCallback {

	public Event<GameRendererHurtCallback> EVENT = EventFactory.createArrayBacked(GameRendererHurtCallback.class, (listeners) -> (matrixStack, f) -> {
		for (final GameRendererHurtCallback event : listeners) {
			final boolean cancel = event.bobViewWhenHurt(matrixStack, f);
			if (cancel) {
				return true;
			}
		}
		return false;
	});

	public boolean bobViewWhenHurt(final MatrixStack matrixStack, final float f);
}