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
public final class ClientPlayerApplyDamageEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, (listeners) -> (source, amount) -> {
		for (final Before event : listeners) {
			event.beforeApplyDamage(source, amount);
		}
	});
	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, (listeners) -> (source, amount) -> {
		for (final After event : listeners) {
			event.afterApplyDamage(source, amount);
		}
	});

	@FunctionalInterface
	public interface Before {

		public void beforeApplyDamage(final DamageSource source, final float amount);
	}

	@FunctionalInterface
	public interface After {

		public void afterApplyDamage(final DamageSource source, final float amount);
	}
}