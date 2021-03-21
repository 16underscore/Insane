package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public interface ClientPlayerMoveCallback {

	public Event<ClientPlayerMoveCallback> EVENT = EventFactory.createArrayBacked(ClientPlayerMoveCallback.class, (listeners) -> (type, movement) -> {
		for (final ClientPlayerMoveCallback event : listeners) {
			event.move(type, movement);
		}
	});

	/**
	 * Called before the player move.
	 * 
	 * @param type
	 * @param movement
	 */
	public void move(final MovementType type, final Vec3d movement);
}