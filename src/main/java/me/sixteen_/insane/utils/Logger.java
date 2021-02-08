package me.sixteen_.insane.utils;

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
public class Logger {

	private static final Logger LOGGER = new Logger();
	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final Insane insane = Insane.getInsane();

	public void addChatMessage(final Object message) {
		mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(String.format("[%s] %s", insane.getClientName(), message)), mc.player.getUuid());
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}