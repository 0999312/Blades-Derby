package net.tracen.blades_derby.data;

import java.util.concurrent.CompletableFuture;

import mods.flammpfeil.slashblade.recipe.RequestDefinition;
import mods.flammpfeil.slashblade.recipe.SlashBladeIngredient;
import mods.flammpfeil.slashblade.recipe.SlashBladeShapedRecipeBuilder;
import mods.flammpfeil.slashblade.registry.SlashBladeItems;
import mods.flammpfeil.slashblade.registry.slashblade.EnchantmentDefinition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.tracen.blades_derby.data.builtin.BuiltInSlashBladeRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class SlashBladeRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public SlashBladeRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

	@Override
	protected void buildRecipes(RecipeOutput output) {
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_HISHI.location())
		.pattern("SLJ")
        .pattern("LBL")
        .pattern("JLS")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().proudSoul(10000).refineCount(20).build()))
        .define('S', Ingredient.of(SlashBladeItems.PROUDSOUL_SPHERE.get()))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('L', Ingredient.of(Tags.Items.DYES_BLUE))
        .unlockedBy(getHasName(SlashBladeItems.SLASHBLADE.get()), has(SlashBladeItems.SLASHBLADE.get())).save(output);
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_BLACK.location())
		.pattern("SLJ")
        .pattern("LBD")
        .pattern("JDS")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().proudSoul(10000).refineCount(20).build()))
        .define('S', Ingredient.of(SlashBladeItems.PROUDSOUL_SPHERE.get()))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('L', Ingredient.of(Tags.Items.DYES_LIME))
        .define('D', Ingredient.of(Tags.Items.DYES_BLACK))
        .unlockedBy(getHasName(SlashBladeItems.SLASHBLADE.get()), has(SlashBladeItems.SLASHBLADE.get())).save(output);
		
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_ODACHI_HARU.location())
		.pattern(" JS")
        .pattern("JSW")
        .pattern("BWG")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().refineCount(10)
                        		.addEnchantment(new EnchantmentDefinition(
                                getEnchantmentID(Enchantments.SMITE), 1))
                        		.build()))
        .define('S', Ingredient.of(SlashBladeItems.PROUDSOUL_INGOT.get()))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('W', Ingredient.of(Tags.Items.DYES_WHITE))
        .define('G', Ingredient.of(Tags.Items.INGOTS_GOLD))
        .unlockedBy(getHasName(SlashBladeItems.SLASHBLADE.get()), has(SlashBladeItems.SLASHBLADE.get())).save(output);
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_ODACHI_HALO.location())
		.pattern(" JS")
        .pattern("JSW")
        .pattern("BWG")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().refineCount(10)
                        		.addEnchantment(new EnchantmentDefinition(
                                getEnchantmentID(Enchantments.SMITE), 1))
                        		.build()))
        .define('S', Ingredient.of(SlashBladeItems.PROUDSOUL_INGOT.get()))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('W', Ingredient.of(Tags.Items.DYES_WHITE))
        .define('G', Ingredient.of(SlashBladeItems.PROUDSOUL.get()))
        .unlockedBy(getHasName(SlashBladeItems.SLASHBLADE.get()), has(SlashBladeItems.SLASHBLADE.get())).save(output);
	}

    private static ResourceLocation getEnchantmentID(ResourceKey<Enchantment> key) {
        return key.location();
    }
}
