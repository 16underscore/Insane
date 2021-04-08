package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface OpenInventoryCallback {

	public Event<OpenInventoryCallback> EVENT = EventFactory.createArrayBacked(OpenInventoryCallback.class, (listeners) -> (player) -> {
		for (final OpenInventoryCallback event : listeners) {
			event.init(player);
		}
	});

	public void init(final PlayerEntity player);
}