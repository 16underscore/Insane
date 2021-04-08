package me.sixteen_.insane.module.modules.render;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
import me.sixteen_.insane.value.values.BooleanValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ArrayList extends Module {

	private final BooleanValue descending;
	private final ListValue sort;
	private List<Module> modules;

	public ArrayList() {
		super(ArrayList.class.getSimpleName(), ModuleCategory.RENDER, false);
		descending = new BooleanValue("descending", false, false);
		sort = new ListValue("sort", false, "none", "size", "alphabet");
		addValues(descending);
		addValues(sort);
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (isEnabled()) {
				onUpdate(matrixStack);
			}
		});
	}

	@Override
	public final void onUpdateValue() {
		sortModules();
	}

	@Override
	protected final void onEnable() {
		modules = Insane.getInstance().getModuleManager().getModules();
		onUpdateValue();
	}

	private final void sortModules() {
		final int i = descending.getValue() ? -1 : 1;
		switch (sort.getIndex()) {
		case 1:
			Collections.sort(modules, new Comparator<Module>() {

				@Override
				public int compare(final Module m1, final Module m2) {
					final int s1Width = mc.textRenderer.getWidth(m1.getNameWithValue()), s2Width = mc.textRenderer.getWidth(m2.getNameWithValue());
					if (s1Width > s2Width) {
						return -i;
					}
					if (s1Width < s2Width) {
						return i;
					}
					return 0;
				}
			});
			break;
		case 2:
			Collections.sort(modules, new Comparator<Module>() {

				@Override
				public int compare(final Module m1, final Module m2) {
					final char c1 = m1.getName().charAt(0), c2 = m2.getName().charAt(0);
					if (c1 < c2) {
						return -i;
					}
					if (c1 > c2) {
						return i;
					}
					return 0;
				}
			});
			break;
		default:
			break;
		}
	}

	private final void onUpdate(final MatrixStack matrices) {
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