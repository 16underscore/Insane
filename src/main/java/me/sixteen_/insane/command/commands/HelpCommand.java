package me.sixteen_.insane.command.commands;

import java.util.Iterator;

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
public final class HelpCommand extends Command {

	public HelpCommand() {
		super("help");
	}

	@Override
	public final String syntax() {
		return String.format("%s", getName());
	}

	@Override
	public final void run(final String... param) {
		if (param.length == 1) {
			insane.getLogger().log(String.format("\u00A73%s <module>\u00A7r", getName()));
			for (final Module m : insane.getModuleManager().getModules()) {
				insane.getLogger().log(m.getName());
			}
		} else if (param.length == 2) {
			insane.getLogger().log(String.format("\u00A73%s %s <value>\u00A7r", getName(), param[1]));
			for (final Value v : insane.getModuleManager().getModuleByName(param[1]).getValues()) {
				insane.getLogger().log(v.getName());
			}
		} else if (param.length == 3) {
			for (final Value v : insane.getModuleManager().getModuleByName(param[1]).getValues()) {
				if (v.getName().equals(param[2])) {
					String s;
					if (v instanceof IntegerValue) {
						final IntegerValue iValue = (IntegerValue) v;
						s = String.format("%s-%s", iValue.getMin(), iValue.getMax());
					} else if (v instanceof FloatValue) {
						final FloatValue fValue = (FloatValue) v;
						s = String.format("%s-%s", fValue.getMin(), fValue.getMax());
					} else if (v instanceof DoubleValue) {
						final DoubleValue dValue = (DoubleValue) v;
						s = String.format("%s-%s", dValue.getMin(), dValue.getMax());
					} else if (v instanceof BooleanValue) {
						final BooleanValue bValue = (BooleanValue) v;
						s = bValue.toString();
					} else if (v instanceof ListValue) {
						final ListValue lValue = (ListValue) v;
						final StringBuilder build = new StringBuilder();
						final Iterator<String> it = lValue.getValues().iterator();
						while (it.hasNext()) {
							final String s1 = it.next();
							if (it.hasNext()) {
								build.append(String.format("%s, ", s1));
							} else {
								build.append(s1);
							}
						}
						s = build.toString();
					} else if (v instanceof IntegerRange) {
						final IntegerRange iRange = (IntegerRange) v;
						s = String.format("%s-%s", iRange.getMin(), iRange.getMax());
					} else {
						s = "error";
					}
					insane.getLogger().log(s);
				}
			}
		}
	}
}