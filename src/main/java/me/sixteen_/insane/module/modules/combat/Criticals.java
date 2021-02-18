package me.sixteen_.insane.module.modules.combat;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Criticals extends Module {

	public Criticals() {
		super("Criticals", ModuleCategory.COMBAT);
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (isEnabled()) {
				onUpdate();
			}
			return ActionResult.PASS;
		});
	}

	@Override
	public void onUpdate() {
		final double posX = mc.player.getX(), posY = mc.player.getY(), posZ = mc.player.getZ();
		sendPos(posX, posY + 0.05D, posZ, true);
		sendPos(posX, posY, posZ, false);
	}

	private final void sendPos(final double x, final double y, final double z, final boolean onGround) {
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(x, y, z, onGround));
	}
}