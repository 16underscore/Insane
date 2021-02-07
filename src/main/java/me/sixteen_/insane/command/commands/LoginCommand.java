package me.sixteen_.insane.command.commands;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;

/**
 * @author 16_
 */
public class LoginCommand extends Command {
	
	private MinecraftClient mc;

	public LoginCommand() {
		super("login", "l");
	}

	@Override
	public void runCommand(final String... param) {
		String mail = param[1];
		String password = param[2];
		if (mail.contains(":") && password.isBlank()) {
			mail = mail.split(":")[0];
			password = mail.split(":")[1];
		}
		if (isInvalidInput(mail, password)) {
			return;
		}
		mc = MinecraftClient.getInstance();
		if (!mc.isInSingleplayer()) {
			return;
		}
		mc.world.disconnect();
		mc.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
		mc.openScreen(new TitleScreen());
		YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
		auth.setUsername(param[1]);
		auth.setPassword(param[2]);
		try {
			auth.logIn();
			Insane.getInsane().getIMinecraftClient().setSession(new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang"));
		} catch (AuthenticationException e) {
		}
	}
	
	private boolean isInvalidInput(final String mail, final String password) {
		if (mail == null || password == null) {
			return false;
		}
		if (mail.isBlank() || password.isBlank()) {
			return false;
		}
		if (!mail.contains("@") || !mail.contains(".")) {
			return false;
		}
		return true;
	}

	@Override
	public String commandSyntax() {
		return String.format(".%s <mail> <password>", getDefaultName());
	}
}
