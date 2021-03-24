package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.ActionResult;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class WTap extends Module {

	public WTap() {
		super(WTap.class.getSimpleName());
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (isEnabled()) {
				onUpdate();
			}
			return ActionResult.PASS;
		});
	}

	@Override
	public final void onUpdate() {
		if (mc.player.isSprinting()) {
			sendPacket(Mode.STOP_SPRINTING);
			sendPacket(Mode.START_SPRINTING);
			sendPacket(Mode.STOP_SPRINTING);
			sendPacket(Mode.START_SPRINTING);
		}
	}

	/**
	 * Sends a client player packet
	 * 
	 * @param mode of the player action
	 */
	private final void sendPacket(final Mode mode) {
		mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, mode));
	}
}