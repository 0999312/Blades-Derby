---
last_mapped_commit: unknown
---

# CONCERNS.md — Technical Debt & Concerns

> Last updated: 2026-05-04

## Critical: Active Migration

This project is in the middle of a **Forge 1.20.1 → NeoForge 1.21.1 migration**. The AGENTS.md at the repository root documents the migration workflow. Current code is still Forge 1.20.1. Migration planning docs exist at `docs/migration/`:

- `MIGRATION_PLAN.md` — Phase plan
- `PROGRESS.md` — Current progress and blockers
- `LOADER_API_MAP.md` — Confirmed Forge → NeoForge API mappings
- `PREREQUISITES.md` — Environment prerequisites

**Every source file** will need changes for the migration:
- `BladesDerby.java` — `@Mod`, `FMLJavaModLoadingContext`, `ModLoadingContext`, event bus
- `BladesDerbyConfig.java` — `ForgeConfigSpec`, `@Mod.EventBusSubscriber`, `ModConfigEvent`
- `SpecialEffectRegistry.java` — `DeferredRegister`, `RegistryObject`
- `UmaSkillRegistry.java` — `DeferredRegister`, `RegistryObject`
- `UmaSpecial.java` — `@SubscribeEvent`, `@EventBusSubscriber`
- `UmaSkillEvents.java` — `@SubscribeEvent`, `@EventBusSubscriber`
- `DataGen.java` — `GatherDataEvent`, `DatapackBuiltinEntriesProvider`, `@Mod.EventBusSubscriber`
- `SlashBladeRecipeProvider.java` — `ForgeRegistries`, `Tags`, `IConditionBuilder`, `RecipeProvider`
- `BuiltInSlashBladeRegistry.java` — `ForgeRegistries`
- `BuiltInSupportCardRegistry.java` — (minimal Forge usage)
- `BDLangProvider.java` — (via MMLib's `AbstractLangProvider`)

## Testing Gap (Severity: High)

- **0% test coverage** across 11 source files
- **No CI/CD pipeline** — no automated build verification
- **No GameTests** despite having a `gameTestServer` run config
- All verification is manual (`runClient`, `runData`)
- Migration to 1.21.1 without tests risks behavioral regressions

## Code Quality Concerns

### Missing Error Handling (Severity: Medium)
- No `try/catch` in any event handler — uncaught exceptions will crash the game
- No null safety annotations (`@Nullable`, `@Nonnull`)
- `UmapyoiAPI.getUmaSoul()` return value used without explicit empty/null validation in some paths (though `.isEmpty()` is used elsewhere)
- `ForgeRegistries.ENCHANTMENTS.getKey()` could return null — no null check at `SlashBladeRecipeProvider:88`

### Mutable Static State (Severity: Low)
- `BladesDerbyConfig.java:17` — `private static int AP_COST` is a mutable static field set from an event handler. Potential race condition if events fire on multiple threads (unlikely in Forge, but not guaranteed for NeoForge).

### Logging Gap (Severity: Low)
- `BladesDerby.getLogger()` provides a logger, but **no log statements exist** in any event handler
- Debugging gameplay issues (AP not deducting, debuffs not applying) requires code inspection, not log analysis

### Inconsistent Formatting (Severity: Low)
- Mixed tabs and spaces within the same files
- No `.editorconfig` or code style configuration

## Documentation Gap (Severity: Medium)

- **No Javadoc** on any class, method, or field
- **README.md** is 2 lines with minimal description
- No in-game documentation (Patchouli dependency exists but no book is authored)
- No API documentation for other modders who might want to add compatible blades/skills

## Magic Numbers (Severity: Low)

| File | Line | Value | Context |
|---|---|---|---|
| `UmaSpecial.java` | 22 | `30` | SpecialEffect tick interval |
| `UmaSkillRegistry.java` | 14,17 | `2` | Skill level |
| `UmaSkillRegistry.java` | 14,17 | `5` | Required wisdom |
| `SlashBladeRecipeProvider.java` | 37 | `10000` | Required proud souls |
| `SlashBladeRecipeProvider.java` | 37,48 | `20` | Required refine count |
| `BladesDerbyConfig.java` | 13 | `20` | Default AP cost |
| `UmaSpecial.java` | 39-41 | `20` | Debuff duration |
| `UmaSpecial.java` | 41 | `2` | Weakness amplifier |

## Architecture Concerns

### Tight Coupling to Third-Party Mods (Severity: High)
- Every gameplay class depends directly on **both** SlashBlade Resharped **and** Umapyoi APIs
- No abstraction layer between addon logic and dependency APIs
- If either dependency changes API in a future update, widespread breakage is guaranteed
- Curios, Patchouli, and JEI are declared as dependencies but unused — potential dead code or incomplete future features

### Thin Abstraction (Severity: Low)
- Single `@Mod` class handles registration, config wiring, and utility methods
- 11 small files total — straightforward but no separation between public API and internal implementation
- All classes are `public` — other mods could accidentally depend on internal details

### Dependency Chain Depth
- `slashblade-resharped → player-animation-lib` (transitive)
- `umapyoi` (direct)
- `mmlib → AbstractLangProvider` (direct, datagen only)
- Version update of any upstream mod requires coordinated testing

## Security

- **No credential leaks detected** in source code
- Publishing tokens accessed via environment variables (`MODRINTH_TOKEN`, `CURSE_TOKEN`) — follows best practice
- No network code, no file I/O beyond Minecraft standard loading — minimal attack surface

## Performance

- Event handlers are lightweight (simple checks, arithmetic) — no performance concerns identified
- No heavy per-tick operations
- 4 blade model files — reasonable asset footprint

## Fragile Areas

| Area | Risk | Why |
|---|---|---|
| `UmaSpecial.onSlashBladeUpdate()` | High | Called every tick per player with blade — any exception crashes the game |
| `UmaSpecial.onSlashBladePowered()` | High | Power activation is a key gameplay moment — wrong logic = broken feature |
| `UmaSpecial.onSlashBladeDoSlash()` | High | AP deduction with skill discount — math error means wrong AP consumed |
| `DataGen.dataGen()` | Medium | Generates all JSON data — broken datagen = no recipes/blades |
| `BladesDerbyConfig.onLoad()` | Medium | Config load is the first runtime event — failure here prevents game start |
| `BuiltInSlashBladeRegistry.registerAll()` | Medium | Manual blade definition construction — typos in keys silent until runtime |
| `BDLangProvider.addTranslations()` | Low | Translation-only — failures are cosmetic |
