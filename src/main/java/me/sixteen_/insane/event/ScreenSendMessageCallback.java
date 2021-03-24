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
public interface ScreenSendMessageCallback {

	public Event<ScreenSendMessageCallback> EVENT = EventFactory.createArrayBacked(ScreenSendMessageCallback.class, (listeners) -> (message, toHud) -> {
		for (final ScreenSendMessageCallback event : listeners) {
			final boolean cancel = event.sendMessage(message, toHud);
			if (cancel) {
				return true;
			}
		}
		return false;
	});

	public boolean sendMessage(final String message, final boolean toHud);
}