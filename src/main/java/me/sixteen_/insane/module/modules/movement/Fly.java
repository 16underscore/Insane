package me.sixteen_.insane.module.modules.movement;

import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.minecraft.client.MinecraftClient;

/**
 * @author 16_
 */
public class Fly extends Module {

	public Fly() {
		super("Fly", ModuleCategory.MOVEMENT);
	}

	private MinecraftClient mc;

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		mc.player.addVelocity(0D, 0.1D, 0D);
		mc.player.abilities.flying = true;
	}

	@Override
	protected void onDisable() {
		mc.player.abilities.flying = false;
	}
}
