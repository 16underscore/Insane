package me.sixteen_.insane.module.modules;

import me.sixteen_.insane.event.ClientPlayerUpdateHealthCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.value.values.BooleanValue;
import me.sixteen_.insane.value.values.IntegerValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class AutoSoup extends Module {

	private final IntegerValue health;
	private final BooleanValue quickdrop;
	private final BooleanValue soupInInv;

	public AutoSoup() {
		super(AutoSoup.class.getSimpleName());
		health = new IntegerValue("health", true, 10, 1, 19);
		quickdrop = new BooleanValue("quickdrop", false, true);
		soupInInv = new BooleanValue("soupininv", false, true);
		addValues(health);
		addValues(quickdrop);
		addValues(soupInInv);
		ClientPlayerUpdateHealthCallback.EVENT.register((health) -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected final void onUpdate() {
		if (!soupInInv.getValue() && mc.currentScreen != null) {
			return;
		}
		if (mc.player.getHealth() <= health.getValue()) {
			final int slot = mc.player.getInventory().selectedSlot;
			for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
				mc.player.getInventory().selectedSlot = i;
				if (mc.player.getMainHandStack().getItem().equals(Items.MUSHROOM_STEW)) {
					mc.interactionManager.interactItem(mc.player, mc.world, Hand.MAIN_HAND);
					if (quickdrop.getValue()) {
						mc.player.dropSelectedItem(true);
					}
					break;
				}
			}
			mc.player.getInventory().selectedSlot = slot;
		}
	}
}