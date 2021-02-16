package me.sixteen_.insane.module.modules.render;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ArrayList extends Module {

	private List<Module> modules;

	public ArrayList() {
		super("ArrayList", ModuleCategory.RENDER, false);
	}

	@Override
	protected final void onEnable() {
		modules = Insane.getInstance().getModuleManager().getModules();
		sortModules();
	}

	private final void sortModules() {
		Collections.sort(modules, new Comparator<Module>() {

			@Override
			public int compare(final Module m1, final Module m2) {
				final int s1Width = mc.textRenderer.getWidth(m1.getName()), s2Width = mc.textRenderer.getWidth(m2.getName());
				if (s1Width > s2Width) {
					return -1;
				}
				if (s1Width < s2Width) {
					return 1;
				}
				return 0;
			}
		});
	}

	public final void onUpdate(final MatrixStack matrices) {
		int h = 0;
		for (final Module m : modules) {
			if (m.isEnabled() && m.isVisible()) {
				final String s = m.getNameWithValue();
				mc.textRenderer.draw(matrices, s, mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(s) - 2, h * mc.textRenderer.fontHeight + 2, -1);
				h++;
			}
		}
	}
}