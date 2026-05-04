# PROGRESS

## Target

- From: Minecraft 1.20.1 Forge
- To: Minecraft 1.21.1 NeoForge

## Current Phase

- Name: Phase 3 — Datagen & Cleanup
- Status: completed
- Last updated: 2026-05-04

## Completed

### Phase 1: Build & Entry ✅
- NeoGradle plugin (`net.neoforged.gradle.userdev` 7.1.25), Gradle 8.14.3
- gradle.properties: 1.21.1 versions, Parchment, dependency properties
- settings.gradle: NeoForge maven
- build.gradle: full rewrite, all deps without fg.deobf()
- neoforge.mods.toml created, old mods.toml deleted
- BladesDerby.java: IEventBus + ModContainer constructor injection

### Phase 2: Registration & Events ✅
- DeferredRegister + DeferredHolder (SpecialEffectRegistry, UmaSkillRegistry)
- @EventBusSubscriber + @SubscribeEvent NeoForge imports (UmaSpecial, UmaSkillEvents)
- ForgeConfigSpec → ModConfigSpec (BladesDerbyConfig), manual addListener registration
- ResourceLocation.fromNamespaceAndPath (BDLangProvider, BuiltInSupportCardRegistry)
- DeferredHolder type compatibility (BDLangProvider Supplier<? extends UmaSkill>)
- Enchantment package + RegistryKey fixes (BuiltInSlashBladeRegistry)
- AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL

### Phase 3: Datagen & Cleanup ✅
- DataGen: GatherDataEvent + DatapackBuiltinEntriesProvider NeoForge imports
- DataGen: removed @Mod.EventBusSubscriber, registered via modEventBus.addListener()
- SlashBladeRecipeProvider: SBItems → SlashBladeItems, FinishedRecipe → RecipeOutput
- SlashBladeRecipeProvider: Tags + IConditionBuilder NeoForge imports
- SlashBladeRecipeProvider: constructor + .save(output)
- BuiltInSlashBladeRegistry: BootstapContext → BootstrapContext
- BuiltInSupportCardRegistry: Registerable → BootstrapContext
- UmaSkillEvents: wrapAsHolder for Attribute → Holder<Attribute>
- All Forge imports cleaned up

## Validation

- compileJava: ✅ BUILD SUCCESSFUL (0 errors)
- runData: Not yet tested (requires Java 21)
- runClient: Not yet tested (requires Java 21)

## Blockers

- Java 21 not installed (currently Java 17) — needed for runClient/runData

## Next Action

Install Java 21, then verify runtime:
1. `./gradlew runData` — verify datagen produces correct output
2. `./gradlew runClient` — verify mod loads and functions in-game
3. Compare generated JSON with 1.20.1 output for parity

## Next Prompt

Install JDK 21 and run `./gradlew runData` to verify datagen. If successful, run `./gradlew runClient` to verify the mod loads in-game with all functionality intact.
