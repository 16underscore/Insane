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
public interface ClientPlayerUpdateHealthCallback {

	public Event<ClientPlayerUpdateHealthCallback> EVENT = EventFactory.createArrayBacked(ClientPlayerUpdateHealthCallback.class, (listeners) -> (health) -> {
		for (final ClientPlayerUpdateHealthCallback event : listeners) {
			event.udpateHealth(health);
		}
	});

	public void udpateHealth(final float health);
}