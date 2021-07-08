package me.sixteen_.insane.module.modules;

import me.sixteen_.insane.event.OpenInventoryCallback;
import me.sixteen_.insane.module.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class Refill extends Module {

	public Refill() {
		super(Refill.class.getSimpleName());
		OpenInventoryCallback.EVENT.register((player) -> {
			if (isEnabled()) {
				onUpdate();
			}
		});
	}

	@Override
	protected final void onUpdate() {
		final DefaultedList<ItemStack> main = mc.player.getInventory().main;
		for (int i = PlayerInventory.getHotbarSize(); i < main.size(); i++) {
			if (main.get(i).getItem().equals(Items.MUSHROOM_STEW)) {
				mc.interactionManager.clickSlot(0, i, 0, SlotActionType.QUICK_MOVE, mc.player);
			}
		}
	}
}