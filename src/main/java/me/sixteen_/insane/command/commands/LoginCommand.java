package me.sixteen_.insane.command.commands;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class LoginCommand extends Command {

	public LoginCommand() {
		super("login");
	}

	@Override
	public final String syntax() {
		return String.format(".%s <mail> <password>", getName());
	}

	@Override
	public final void run(final String... param) {
		final String mail = param[1], password = param[2];
		if (isInvalidInput(mail, password)) {
			return;
		}
		if (!mc.isInSingleplayer()) {
			return;
		}
		mc.world.disconnect();
		mc.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
		final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
		auth.setUsername(mail);
		auth.setPassword(password);
		try {
			auth.logIn();
			insane.getIMinecraftClient().setSession(new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang"));
		} catch (AuthenticationException e) {
		}
	}

	private final boolean isInvalidInput(final String mail, final String password) {
		if (mail == null || password == null) {
			return true;
		}
		if (mail.isEmpty() || password.isEmpty()) {
			return true;
		}
		if (!mail.contains("@") || !mail.contains(".")) {
			return true;
		}
		return false;
	}
}