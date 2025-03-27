package net.tracen.blades_derby;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tracen.blades_derby.se.SpecialEffectRegistry;
import net.tracen.blades_derby.umaskill.UmaSkillRegistry;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BladesDerby.MODID)
public class BladesDerby {
	public static final String MODID = "blades_derby";
	private static final Logger LOGGER = LogUtils.getLogger();

	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(MODID, path);
	}

	public BladesDerby() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		SpecialEffectRegistry.SPECIAL_EFFECT.register(modEventBus);
		UmaSkillRegistry.UMA_SKILLS.register(modEventBus);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
