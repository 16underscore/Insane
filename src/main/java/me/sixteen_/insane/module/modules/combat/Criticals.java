package me.sixteen_.insane.module.modules.combat;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Criticals extends Module {

	private MinecraftClient mc;
	private ClientPlayerEntity player;

	public Criticals() {
		super("Criticals", ModuleCategory.COMBAT);
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		player = mc.player;
	}

	@Override
	public void onUpdate() {
		final double posX = player.getX();
		final double posY = player.getY();
		final double posZ = player.getZ();
		sendPos(posX, posY + 0.05D, posZ, true);
		sendPos(posX, posY, posZ, false);
	}

	private void sendPos(final double x, final double y, final double z, final boolean onGround) {
		player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(x, y, z, onGround));
	}
}