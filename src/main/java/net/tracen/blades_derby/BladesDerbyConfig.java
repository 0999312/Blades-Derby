package net.tracen.blades_derby;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = BladesDerby.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BladesDerbyConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.IntValue UMA_SPECIAL_AP_COST = BUILDER.comment("The cost of AP by Uma Soul Resonance")
			.defineInRange("se_ap_cost", 20, 0, Integer.MAX_VALUE);

	static final ForgeConfigSpec SPEC = BUILDER.build();

	private static int AP_COST;

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent event) {
		AP_COST = UMA_SPECIAL_AP_COST.get();

	}

	public static int getAPCost() {
		return AP_COST;
	}
}
