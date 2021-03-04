package me.sixteen_.insane.util;

import me.sixteen_.insane.Insane;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Logger {

	private final MinecraftClient mc;
	private final Insane insane;

	public Logger() {
		mc = MinecraftClient.getInstance();
		insane = Insane.getInstance();
	}

	/**
	 * Sends a chat message.
	 * 
	 * @param message
	 */
	public final void log(final String message) {
		mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(String.format("[%s] %s", insane.getClientName(), message)), null);
	}
}