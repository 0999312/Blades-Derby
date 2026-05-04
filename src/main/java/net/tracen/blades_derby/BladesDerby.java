package net.tracen.blades_derby;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.tracen.blades_derby.data.DataGen;
import net.tracen.blades_derby.se.SpecialEffectRegistry;
import net.tracen.blades_derby.umaskill.UmaSkillRegistry;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BladesDerby.MODID)
public class BladesDerby {
	public static final String MODID = "blades_derby";
	private static final Logger LOGGER = LogUtils.getLogger();

	public static ResourceLocation prefix(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

	public BladesDerby(IEventBus modEventBus, ModContainer modContainer) {
		SpecialEffectRegistry.SPECIAL_EFFECT.register(modEventBus);
		UmaSkillRegistry.UMA_SKILLS.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.COMMON, BladesDerbyConfig.SPEC);
		modEventBus.addListener(BladesDerbyConfig::onLoad);
		modEventBus.addListener(DataGen::dataGen);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
