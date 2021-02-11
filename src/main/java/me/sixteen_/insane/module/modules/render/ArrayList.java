package me.sixteen_.insane.module.modules.render;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ArrayList extends Module {

	private List<Module> modules;
	private MinecraftClient mc;
	private TextRenderer tr;

	public ArrayList() {
		super("ArrayList", ModuleCategory.RENDER, false);
	}

	private void sortModules() {
		Collections.sort(modules, new Comparator<Module>() {

			@Override
			public int compare(final Module m1, final Module m2) {
				if (tr.getWidth(m1.getName()) > tr.getWidth(m2.getName())) {
					return -1;
				}
				if (tr.getWidth(m1.getName()) < tr.getWidth(m2.getName())) {
					return 1;
				}
				return 0;
			}
		});
	}

	@Override
	protected void onEnable() {
		mc = MinecraftClient.getInstance();
		modules = Insane.getInsane().getModuleManager().getModules();
		tr = mc.textRenderer;
		sortModules();
	}

	public void onUpdate(final MatrixStack matrices) {
		int h = 0;
		for (Module m : modules) {
			if (m.isEnabled() && m.isVisible()) {
				tr.draw(matrices, m.getName(), mc.getWindow().getScaledWidth() - tr.getWidth(m.getName()) - 2, h * tr.fontHeight + 2, -1);
				h++;
			}
		}
	}
}