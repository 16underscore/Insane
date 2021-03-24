package me.sixteen_.insane.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ClientPlayerApplyDamageCallback {

	public Event<ClientPlayerApplyDamageCallback> EVENT = EventFactory.createArrayBacked(ClientPlayerApplyDamageCallback.class, (listeners) -> (source, amount) -> {
		for (final ClientPlayerApplyDamageCallback event : listeners) {
			event.applyDamage(source, amount);
		}
	});

	public void applyDamage(final DamageSource source, final float amount);
}
