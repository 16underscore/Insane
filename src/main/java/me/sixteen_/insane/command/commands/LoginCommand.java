package me.sixteen_.insane.command.commands;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.command.Command;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.Session;
import net.minecraft.text.TranslatableText;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public class LoginCommand extends Command {

	private MinecraftClient mc;

	public LoginCommand() {
		super("login", "l");
	}

	@Override
	public void runCommand(final String... param) {
		String mail = param[1];
		String password = param[2];
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
		auth.setUsername(mail);
		auth.setPassword(password);
		try {
			auth.logIn();
			Insane.getInsane().getIMinecraftClient().setSession(new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang"));
		} catch (AuthenticationException e) {
		}
	}

	private boolean isInvalidInput(final String mail, final String password) {
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

	@Override
	public String commandSyntax() {
		return String.format(".%s <mail> <password>", getDefaultName());
	}
}
