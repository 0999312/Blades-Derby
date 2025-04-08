package net.tracen.blades_derby.data.builtin;

import java.util.List;

import mods.flammpfeil.slashblade.client.renderer.CarryType;
import mods.flammpfeil.slashblade.item.SwordType;
import mods.flammpfeil.slashblade.registry.SlashArtsRegistry;
import mods.flammpfeil.slashblade.registry.slashblade.EnchantmentDefinition;
import mods.flammpfeil.slashblade.registry.slashblade.PropertiesDefinition;
import mods.flammpfeil.slashblade.registry.slashblade.RenderDefinition;
import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;
import net.tracen.blades_derby.BladesDerby;
import net.tracen.blades_derby.se.SpecialEffectRegistry;

public class BuiltInSlashBladeRegistry {
	public static final ResourceKey<SlashBladeDefinition> UMA_HISHI = register("uma_hishi");
	public static final ResourceKey<SlashBladeDefinition> UMA_BLACK = register("uma_black");

	public static final ResourceKey<SlashBladeDefinition> UMA_ODACHI_HARU = register("uma_odachi_haru");
	public static final ResourceKey<SlashBladeDefinition> UMA_ODACHI_HALO = register("uma_odachi_halo");

	public static void registerAll(BootstapContext<SlashBladeDefinition> bootstrap) {

		bootstrap.register(UMA_HISHI,
				new SlashBladeDefinition(BladesDerby.prefix("uma_hishi"),
						RenderDefinition.Builder.newInstance()
								.textureName(BladesDerby.prefix("model/uma_hishi.png"))
								.modelName(BladesDerby.prefix("model/uma_hishi.obj"))
								.standbyRenderType(CarryType.KATANA)
                                .build(),
                        PropertiesDefinition.Builder.newInstance().baseAttackModifier(7.0F).maxDamage(50)
								.defaultSwordType(List.of(SwordType.BEWITCHED))
								.addSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())
								.slashArtsType(SlashArtsRegistry.DRIVE_VERTICAL.getId()).build(),
						List.of(new EnchantmentDefinition(getEnchantmentID(Enchantments.SHARPNESS), 1))));
		
		bootstrap.register(UMA_BLACK,
				new SlashBladeDefinition(BladesDerby.prefix("uma_black"),
						RenderDefinition.Builder.newInstance()
								.textureName(BladesDerby.prefix("model/uma_black.png"))
								.modelName(BladesDerby.prefix("model/uma_black.obj"))
								.standbyRenderType(CarryType.KATANA)
                                .build(),
                        PropertiesDefinition.Builder.newInstance().baseAttackModifier(7.0F).maxDamage(50)
								.defaultSwordType(List.of(SwordType.BEWITCHED))
								.addSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())
								.slashArtsType(SlashArtsRegistry.VOID_SLASH.getId()).build(),
						List.of(new EnchantmentDefinition(getEnchantmentID(Enchantments.SWEEPING_EDGE), 1))));
		
		bootstrap.register(UMA_ODACHI_HARU,
				new SlashBladeDefinition(BladesDerby.prefix("uma_odachi_haru"),
						RenderDefinition.Builder.newInstance()
								.textureName(BladesDerby.prefix("model/uma_odachi_haru.png"))
								.modelName(BladesDerby.prefix("model/uma_odachi_haru.obj"))
								.standbyRenderType(CarryType.PSO2)
                                .build(),
                        PropertiesDefinition.Builder.newInstance().baseAttackModifier(9.0F).maxDamage(45)
								.defaultSwordType(List.of(SwordType.BEWITCHED))
								.addSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())
								.slashArtsType(SlashArtsRegistry.SAKURA_END.getId()).build(),
						List.of(
								new EnchantmentDefinition(getEnchantmentID(Enchantments.SMITE), 1),
								new EnchantmentDefinition(getEnchantmentID(Enchantments.MOB_LOOTING), 1)
								)));
		
		bootstrap.register(UMA_ODACHI_HALO,
				new SlashBladeDefinition(BladesDerby.prefix("uma_odachi_halo"),
						RenderDefinition.Builder.newInstance()
								.textureName(BladesDerby.prefix("model/uma_odachi_halo.png"))
								.modelName(BladesDerby.prefix("model/uma_odachi_halo.obj"))
								.standbyRenderType(CarryType.PSO2)
                                .build(),
                        PropertiesDefinition.Builder.newInstance().baseAttackModifier(9.0F).maxDamage(45)
								.defaultSwordType(List.of(SwordType.BEWITCHED))
								.addSpecialEffect(SpecialEffectRegistry.UMA_SPECIAL.getId())
								.slashArtsType(SlashArtsRegistry.CIRCLE_SLASH.getId()).build(),
						List.of(
								new EnchantmentDefinition(getEnchantmentID(Enchantments.SMITE), 1),
								new EnchantmentDefinition(getEnchantmentID(Enchantments.POWER_ARROWS), 1)
								)));
	}

	private static ResourceLocation getEnchantmentID(Enchantment enchantment) {
		return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
	}

	private static ResourceKey<SlashBladeDefinition> register(String id) {
		ResourceKey<SlashBladeDefinition> loc = ResourceKey.create(SlashBladeDefinition.REGISTRY_KEY,
				BladesDerby.prefix(id));
		return loc;
	}
}
