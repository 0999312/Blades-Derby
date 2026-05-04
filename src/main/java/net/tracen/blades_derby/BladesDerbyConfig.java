package net.tracen.blades_derby;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BladesDerbyConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.IntValue UMA_SPECIAL_AP_COST = BUILDER.comment("The cost of AP by Uma Soul Resonance")
			.defineInRange("se_ap_cost", 20, 0, Integer.MAX_VALUE);

	static final ModConfigSpec SPEC = BUILDER.build();

	private static int AP_COST;

	public static void onLoad(final ModConfigEvent event) {
		AP_COST = UMA_SPECIAL_AP_COST.get();

	}

	public static int getAPCost() {
		return AP_COST;
	}
}
