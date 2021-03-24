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
public interface OnKeyCallback {

	public Event<OnKeyCallback> EVENT = EventFactory.createArrayBacked(OnKeyCallback.class, (listeners) -> (window, key, scancode, i, j) -> {
		for (final OnKeyCallback event : listeners) {
			event.onKey(window, key, scancode, i, j);
		}
	});

	public void onKey(final long window, final int key, final int scancode, final int i, final int j);
}