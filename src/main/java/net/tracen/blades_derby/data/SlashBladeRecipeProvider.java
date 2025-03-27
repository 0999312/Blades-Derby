package net.tracen.blades_derby.data;

import java.util.function.Consumer;

import mods.flammpfeil.slashblade.init.SBItems;
import mods.flammpfeil.slashblade.recipe.RequestDefinition;
import mods.flammpfeil.slashblade.recipe.SlashBladeIngredient;
import mods.flammpfeil.slashblade.recipe.SlashBladeShapedRecipeBuilder;
import mods.flammpfeil.slashblade.registry.slashblade.EnchantmentDefinition;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.tracen.blades_derby.data.builtin.BuiltInSlashBladeRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class SlashBladeRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public SlashBladeRecipeProvider(PackOutput output) {
        super(output);
    }

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_HISHI.location())
		.pattern("SLJ")
        .pattern("LBL")
        .pattern("JLS")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().proudSoul(10000).refineCount(20).build()))
        .define('S', Ingredient.of(SBItems.proudsoul_sphere))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('L', Ingredient.of(Tags.Items.DYES_BLUE))
        .unlockedBy(getHasName(SBItems.slashblade), has(SBItems.slashblade)).save(consumer);
		SlashBladeShapedRecipeBuilder.shaped(BuiltInSlashBladeRegistry.UMA_BLACK.location())
		.pattern("SLJ")
        .pattern("LBD")
        .pattern("JDS")
        .define('B',
                SlashBladeIngredient
                        .of(RequestDefinition.Builder.newInstance().proudSoul(10000).refineCount(20).build()))
        .define('S', Ingredient.of(SBItems.proudsoul_sphere))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('L', Ingredient.of(Tags.Items.DYES_LIME))
        .define('D', Ingredient.of(Tags.Items.DYES_BLACK))
        .unlockedBy(getHasName(SBItems.slashblade), has(SBItems.slashblade)).save(consumer);
		
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
        .define('S', Ingredient.of(SBItems.proudsoul_ingot))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('W', Ingredient.of(Tags.Items.DYES_WHITE))
        .define('G', Ingredient.of(Tags.Items.INGOTS_GOLD))
        .unlockedBy(getHasName(SBItems.slashblade), has(SBItems.slashblade)).save(consumer);
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
        .define('S', Ingredient.of(SBItems.proudsoul_ingot))
        .define('J', Ingredient.of(ItemRegistry.JEWEL.get()))
        .define('W', Ingredient.of(Tags.Items.DYES_WHITE))
        .define('G', Ingredient.of(SBItems.proudsoul))
        .unlockedBy(getHasName(SBItems.slashblade), has(SBItems.slashblade)).save(consumer);
	}

    private static ResourceLocation getEnchantmentID(Enchantment enchantment) {
        return ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
    }
}
