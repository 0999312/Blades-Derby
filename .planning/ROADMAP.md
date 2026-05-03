# Roadmap: Blades Derby Migration

**Created:** 2026-05-04
**Granularity:** Standard
**Phases:** 3

## Phase Overview

| # | Phase | Goal | Requirements | Success Criteria |
|---|-------|------|--------------|------------------|
| 1 | Build & Entry | Project compiles on NeoForge 1.21.1 | BUILD-01 → BUILD-07 | 3 |
| 2 | Registration & Events | All registries and event handlers work | REG-01 → REG-06 | 3 |
| 3 | Datagen & Cleanup | Datagen runs, cleanup complete, client launches | DATA-01 → DATA-08 | 3 |

## Phase 1: Build & Entry

**Goal:** The Gradle build system is fully migrated to NeoGradle, the mod metadata is correct for NeoForge, and `./gradlew compileJava` passes without errors.

**Requirements:** BUILD-01, BUILD-02, BUILD-03, BUILD-04, BUILD-05, BUILD-06, BUILD-07

**Success criteria:**
1. `./gradlew compileJava` exits with BUILD SUCCESSFUL
2. `mods.toml` (or `neoforge.mods.toml`) is valid NeoForge format with correct dependency declarations
3. All dependency coordinates resolve — no missing artifact errors

**Key tasks:**
- Replace `net.minecraftforge.gradle` with `net.neoforged.gradle` (NeoGradle)
- Update `gradle.properties`: `minecraft_version=1.21.1`, add `neo_version`, update mappings
- Update `settings.gradle`: add NeoForge maven repository
- Convert `mods.toml` format for NeoForge (`javafml` → `javafml`, version range changes if needed)
- Replace `FMLJavaModLoadingContext` with `IEventBus` parameter in `@Mod` constructor
- Update all dependency versions to NeoForge 1.21.1 artifacts
- Remove `fg.deobf()` wrappers (NeoGradle handles differently)
- Update `java.toolchain.languageVersion` to Java 21
- Update `modpublisher` plugin settings for 1.21.1 if still needed

**Research notes:** See `docs/migration/PREREQUISITES.md` for confirmed 1.21.1 dependency coordinates. Use `loader-diff-research` for any uncertain NeoGradle/build API differences.

**Plans:** 3 plans

| Plan | Objective | Wave | Files |
|------|-----------|------|-------|
| 01-01 | Update gradle.properties and settings.gradle for NeoForge 1.21.1 | 1 | gradle.properties, settings.gradle |
| 01-02 | Migrate build.gradle to NeoGradle with all deps and publishing | 2 | build.gradle |
| 01-03 | Convert mods.toml to neoforge.mods.toml and migrate @Mod entry point | 3 | neoforge.mods.toml, BladesDerby.java |

Plans:
- [ ] 01-01-PLAN.md — Gradle properties and plugin repos for NeoForge 1.21.1
- [ ] 01-02-PLAN.md — build.gradle migration to NeoGradle
- [ ] 01-03-PLAN.md — Mod metadata and @Mod entry point migration

---

## Phase 2: Registration & Events

**Goal:** All DeferredRegister registries, event subscribers, and config system are migrated to NeoForge API. The mod loads without crash.

**Requirements:** REG-01, REG-02, REG-03, REG-04, REG-05, REG-06

**Success criteria:**
1. `./gradlew compileJava` passes with all registry and event code migrated
2. Mod loads in dev environment without registry or event bus errors
3. Config file generates with correct structure on first launch

**Key tasks:**
- Migrate `DeferredRegister.create()` calls (Forge → NeoForge pattern)
- Migrate `SpecialEffectRegistry` registration wiring
- Migrate `UmaSkillRegistry` registration wiring
- Update `@Mod.EventBusSubscriber` annotations for NeoForge bus targets
- Update `@SubscribeEvent` usage if NeoForge differs
- Migrate `ForgeConfigSpec` to NeoForge config system
- Update `ModConfigEvent` handling in `BladesDerbyConfig`
- Verify `UmaSpecial` event handlers (SlashBladeEvent subclasses) still compile
- Verify `UmaSkillEvents` (ApplyUmasoulAttributeEvent) still compiles
- Update SlashBlade Resharped API references if their NeoForge version changed signatures

**Research notes:** DeferredRegister and event bus are the most Forge-specific APIs in this codebase. Every source file touches one of these. Use `loader-diff-research` aggressively here — every register/event call needs verification.

---

## Phase 3: Datagen & Cleanup

**Goal:** Data generation runs on NeoForge and produces identical output to 1.20.1. All Forge imports cleaned up. Client launches with full mod functionality.

**Requirements:** DATA-01, DATA-02, DATA-03, DATA-04, DATA-05, DATA-06, DATA-07, DATA-08

**Success criteria:**
1. `./gradlew runData` exits successfully with no errors
2. Generated JSON output matches 1.20.1 output (blade definitions, recipes, advancements, lang)
3. `./gradlew runClient` launches and the mod's blades/skills are functional in-game

**Key tasks:**
- Migrate `GatherDataEvent` subscription and event handler
- Migrate `DatapackBuiltinEntriesProvider` usage for blade definitions
- Migrate `DatapackBuiltinEntriesProvider` usage for support cards
- Update `SlashBladeRecipeProvider` — replace `ForgeRegistries.ENCHANTMENTS`, `Tags`, `IConditionBuilder`
- Update `BDLangProvider` — verify MMLib's 1.21.1 AbstractLangProvider changes
- Update `BuiltInSlashBladeRegistry` — replace `ForgeRegistries.ENCHANTMENTS.getKey()`
- Update `BuiltInSupportCardRegistry` — verify Umapyoi registry key access pattern
- Run diff against 1.20.1 generated output to verify parity
- Clean up any remaining `net.minecraftforge.*` imports
- Manual smoke test: craft a blade, equip UmaSoul, verify special effect fires, verify skill attributes

**Research notes:** NeoForge 1.21.1 significantly changed datagen APIs. `GatherDataEvent` exists but the provider registration pattern differs. `DatapackBuiltinEntriesProvider` may have a different constructor. The recipe system (`RecipeProvider`, `FinishedRecipe`) changed in 1.21. `ForgeRegistries` → `BuiltInRegistries` for vanilla registries; for mod registries, use the registry's key directly.
