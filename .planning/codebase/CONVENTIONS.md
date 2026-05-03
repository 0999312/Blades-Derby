---
last_mapped_commit: unknown
---

# CONVENTIONS.md — Coding Conventions

> Last updated: 2026-05-04

## Language & Formatting

| Aspect | Convention |
|---|---|
| Language | Java 17 |
| Encoding | UTF-8 (set in `build.gradle:221`) |
| Indentation | Mixed tabs and spaces (varies by file). Predominantly tabs in some files, 4-space in others. |
| Line endings | CRLF (Windows-native) |
| Max line length | No limit observed; some lines exceed 140 chars (e.g. `SlashBladeRecipeProvider:31-41`) |

## Naming Conventions

### Packages
- Reverse domain: `net.tracen.blades_derby`
- Sub-packages by feature domain: `se`, `umaskill`, `data`, `data.builtin`

### Classes
- **PascalCase**: `BladesDerby`, `UmaSpecial`, `UmaSkillRegistry`, `SlashBladeRecipeProvider`
- Registry classes suffix with `Registry`: `SpecialEffectRegistry`, `UmaSkillRegistry`, `BuiltInSlashBladeRegistry`
- Provider classes (datagen) suffix with `Provider`: `SlashBladeRecipeProvider`, `BDLangProvider`
- Config classes suffix with `Config`: `BladesDerbyConfig`

### Methods
- **camelCase**: `onSlashBladeUpdate()`, `hasSpecialEffect()`, `getAPCost()`
- Event handlers prefixed with `on`: `onLoad()`, `onSlashBladeUpdate()`, `onSlashBladePowered()`
- Static utility: `prefix()` in `BladesDerby` — factory for `ResourceLocation`

### Constants
- **UPPER_SNAKE_CASE** for `static final` fields:
  - `MODID`, `LOGGER` (`BladesDerby`)
  - `SPECIAL_EFFECT`, `UMA_SPECIAL` (`SpecialEffectRegistry`)
  - `UMA_SKILLS`, `IAIDO`, `BREATH_OF_NATURE` (`UmaSkillRegistry`)
  - `SPEC` (`BladesDerbyConfig`)
- Registry keys: `UPPER_SNAKE_CASE` — `UMA_HISHI`, `UMA_BLACK`, `SAINT_LITE_IAIDO`

### Fields
- **camelCase** for instance/mutable fields: `AP_COST` (inconsistent — uses UPPER_SNAKE for a mutable static)
- Builder instances: `BUILDER` (UPPER_SNAKE, `BladesDerbyConfig`)

## Code Organization

### Class Structure (typical pattern)
```
1. Package declaration
2. Imports (no wildcard imports observed)
3. Class Javadoc (absent in most files)
4. Static fields (constants, registries)
5. Constructor
6. Public methods
7. Event handler methods (@SubscribeEvent static methods)
8. Private helpers
```

### File Size
| File | Lines |
|---|---|
| `SlashBladeRecipeProvider.java` | 90 |
| `BuiltInSlashBladeRegistry.java` | 98 |
| `UmaSpecial.java` | 82 |
| `BDLangProvider.java` | 75 |
| `DataGen.java` | 56 |
| `BladesDerby.java` | 38 |
| `BuiltInSupportCardRegistry.java` | 35 |
| `BladesDerbyConfig.java` | 28 |
| `UmaSkillEvents.java` | 22 |
| `UmaSkillRegistry.java` | 18 |
| `SpecialEffectRegistry.java` | 14 |

All files are under 100 lines — small, focused classes.

## Patterns

### Registration Pattern
- **Forge-style DeferredRegister**: All registry entries follow this pattern:
  ```java
  public static final DeferredRegister<T> REGISTRY = DeferredRegister.create(KEY, MODID);
  public static final RegistryObject<T> ENTRY = REGISTRY.register("name", Constructor::new);
  ```
  Wiring happens in `@Mod` constructor: `REGISTRY.register(modEventBus)`.

### Event Handler Pattern
- **Static @SubscribeEvent methods**: Event handlers are `public static void` methods annotated with `@SubscribeEvent`.
- Classes use `@EventBusSubscriber` at the class level for auto-registration.
- Two bus targets used:
  - `Bus.MOD` — for mod lifecycle events (`BladesDerbyConfig`, `DataGen`)
  - Default (no bus specified) — for game events (`UmaSpecial`, `UmaSkillEvents`)

### Builder Pattern
- `ForgeConfigSpec.Builder` for config (`BladesDerbyConfig:10`)
- `.Builder` inner classes for blade definitions (properties, render, request definitions)
- `SupportCard.Builder.create()` for support card construction

### Datagen Pattern
- `GatherDataEvent` subscribed via `@Mod.EventBusSubscriber(bus = MOD)`
- Data generators added via `dataGenerator.addProvider(include, provider)`
- `DatapackBuiltinEntriesProvider` for datapack registry entries

## Imports & Dependencies

- No wildcard imports (`import x.y.*`) observed in any file
- Import order: external libraries first, then internal packages
- Dependencies accessed via their public API classes (e.g. `UmapyoiAPI`, `UmaSoulUtils`, `ModAttributes`)

## Error Handling

- **Minimal to none**: No `try/catch` blocks observed in any Java file
- Relies on Minecraft/Forge's built-in error handling for nullability and registry issues
- No custom exceptions defined
- `instanceof` checks used for type safety: `if (!(event.getEntity() instanceof LivingEntity)) return;`
- Guard clauses for early return (used extensively in `UmaSpecial`)

## Logging

- SLF4J logger via `com.mojang.logging.LogUtils.getLogger()`
- Single `LOGGER` constant in `BladesDerby.java`
- **No log statements** used in any event handlers — logger is defined but unused

## Documentation

- **No Javadoc** on any class or method
- Minimal inline comments — only Forge MDK template comments remain in `build.gradle`
- `README.md` is 2 lines: mod name and one-sentence description
- No Wiki, no Patchouli book

## Access Modifiers

- Registry entries: `public static final`
- Event handlers: `public static void`
- Config values: `private static` with `public static` getter
- Helper methods: `private static`
- Classes: `public class`
