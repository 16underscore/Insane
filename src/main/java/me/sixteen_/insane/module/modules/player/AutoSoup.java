package me.sixteen_.insane.module.modules.player;

import me.sixteen_.insane.Insane;
import me.sixteen_.insane.event.ClientPlayerUpdateHealthCallback;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.module.ModuleCategory;
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
public class AutoSoup extends Module {

	private final IntegerValue health;
	private final BooleanValue quickdrop;

	public AutoSoup() {
		super(AutoSoup.class.getSimpleName(), ModuleCategory.PLAYER);
		health = new IntegerValue("health", true, 10, 1, 19);
		quickdrop = new BooleanValue("quickdrop", false, true);
		addValues(health);
		addValues(quickdrop);
		ClientPlayerUpdateHealthCallback.EVENT.register((health) -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected void onUpdate() {
		Insane.getInstance().getLogger().log("Aua");
		if (mc.player.getHealth() <= health.getValue()) {
			final int slot = mc.player.inventory.selectedSlot;
			for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
				mc.player.inventory.selectedSlot = i;
				if (mc.player.getMainHandStack().getItem().equals(Items.MUSHROOM_STEW)) {
					mc.interactionManager.interactItem(mc.player, mc.world, Hand.MAIN_HAND);
					break;
				}
			}
			if (quickdrop.getValue()) {
				mc.player.dropSelectedItem(true);
			}
			mc.player.inventory.selectedSlot = slot;
		}
	}
}