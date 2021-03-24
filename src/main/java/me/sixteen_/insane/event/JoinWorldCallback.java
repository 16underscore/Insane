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
public interface JoinWorldCallback {

	public Event<JoinWorldCallback> EVENT = EventFactory.createArrayBacked(JoinWorldCallback.class, (listeners) -> () -> {
		for (final JoinWorldCallback event : listeners) {
			event.joinWorld();
		}
	});

	public void joinWorld();
}