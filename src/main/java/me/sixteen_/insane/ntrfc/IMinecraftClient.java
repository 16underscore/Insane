package me.sixteen_.insane.ntrfc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Session;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public interface IMinecraftClient {
	
	public void setSession(Session session);
}