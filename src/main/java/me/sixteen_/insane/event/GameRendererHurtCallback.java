package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public interface GameRendererHurtCallback {

	public Event<GameRendererHurtCallback> EVENT = EventFactory.createArrayBacked(GameRendererHurtCallback.class, (listeners) -> (matrixStack, f) -> {
		for (final GameRendererHurtCallback event : listeners) {
			final ActionResult result = event.bobViewWhenHurt(matrixStack, f);
			if (result != ActionResult.PASS) {
				return result;
			}
		}
		return ActionResult.PASS;
	});

	public ActionResult bobViewWhenHurt(final MatrixStack matrixStack, final float f);
}