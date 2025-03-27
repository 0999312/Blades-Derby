package net.tracen.blades_derby.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.blades_derby.BladesDerby;
import net.tracen.blades_derby.data.builtin.BuiltInSlashBladeRegistry;
import net.tracen.blades_derby.data.builtin.BuiltInSupportCardRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
	@SubscribeEvent
	public static void dataGen(GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		PackOutput packOutput = dataGenerator.getPackOutput();

		dataGenerator.addProvider(event.includeServer(), new SlashBladeRecipeProvider(packOutput));

		dataGenerator.addProvider(event.includeClient(), new BDLangProvider(packOutput));
		
		final RegistrySetBuilder bladeBuilder = new RegistrySetBuilder().add(SlashBladeDefinition.REGISTRY_KEY,
				BuiltInSlashBladeRegistry::registerAll);
		final RegistrySetBuilder cardBuilder = new RegistrySetBuilder().add(SupportCard.REGISTRY_KEY,
				BuiltInSupportCardRegistry::registerAll);
		
		dataGenerator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider,
				bladeBuilder, Set.of(BladesDerby.MODID)) {

			@Override
			public String getName() {
				return "SlashBlade Definition Registry";
			}

		});
		dataGenerator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider,
				cardBuilder, Set.of(BladesDerby.MODID)) {

			@Override
			public String getName() {
				return "Support Card Definition Registry";
			}

		});
	}
}
