package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ClientPlayerTickCallback {

	public Event<ClientPlayerTickCallback> EVENT = EventFactory.createArrayBacked(ClientPlayerTickCallback.class, (listeners) -> () -> {
		for (final ClientPlayerTickCallback event : listeners) {
			event.tick();
		}
	});

	public void tick();
}