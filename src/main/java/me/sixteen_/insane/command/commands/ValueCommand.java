package me.sixteen_.insane.command.commands;

import me.sixteen_.insane.command.Command;
import me.sixteen_.insane.module.Module;
import me.sixteen_.insane.value.Value;
import me.sixteen_.insane.value.ranges.IntegerRange;
import me.sixteen_.insane.value.values.BooleanValue;
import me.sixteen_.insane.value.values.DoubleValue;
import me.sixteen_.insane.value.values.FloatValue;
import me.sixteen_.insane.value.values.IntegerValue;
import me.sixteen_.insane.value.values.ListValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author 16_
 */
@Environment(EnvType.CLIENT)
public final class ValueCommand extends Command {

	public ValueCommand() {
		super("value");
	}

	@Override
	public final void run(final String... param) {
		final Module m = insane.getModuleManager().getModuleByName(param[1]);
		final Value v = m.getValueByName(param[2]);
		if (v instanceof IntegerValue) {
			((IntegerValue) v).setValue(Integer.parseInt(param[3]));
		} else if (v instanceof FloatValue) {
			((FloatValue) v).setValue(Float.parseFloat(param[3]));
		} else if (v instanceof DoubleValue) {
			((DoubleValue) v).setValue(Double.parseDouble(param[3]));
		} else if (v instanceof BooleanValue) {
			((BooleanValue) v).setValue(Boolean.parseBoolean(param[3]));
		} else if (v instanceof ListValue) {
			((ListValue) v).setValue(param[3]);
		} else if (v instanceof IntegerRange) {
			if (param[3].equalsIgnoreCase("min")) {
				((IntegerRange) v).setMinValue(Integer.parseInt(param[4]));
			} else if (param[3].equalsIgnoreCase("max")) {
				((IntegerRange) v).setMaxValue(Integer.parseInt(param[4]));
			}
		}
		if (m.isEnabled()) {
			m.onUpdateValue();
		}
	}

	@Override
	public final String syntax() {
		return String.format(".%s <Module> <Type> <Value>", getName());
	}
}