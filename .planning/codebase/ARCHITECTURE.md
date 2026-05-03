---
last_mapped_commit: unknown
---

# ARCHITECTURE.md — System Architecture

> Last updated: 2026-05-04

## Overview

Blades Derby is a **Minecraft Forge 1.20.1 addon mod** that bridges the **SlashBlade Resharped** and **Umapyoi** mods. It adds custom blades powered by Uma Soul energy, custom Uma Skills that affect blade performance, and a support card. The architecture follows standard **Minecraft Forge mod patterns** with a single mod entry point, deferred registries, and event-driven behavior.

## Architecture Pattern

**Event-driven mod architecture** — no service layer, no DI framework, no layered tiers. All behavior is wired through:

1. **@Mod** entry point for bootstrap
2. **DeferredRegister** for registry entries
3. **@SubscribeEvent** static methods on any class for event handling

```
┌─────────────────────────────────────────┐
│              @Mod Entry                  │
│           BladesDerby.java              │
│                                         │
│  • Registers DeferredRegisters          │
│  • Registers config                     │
└──────────┬──────────────┬───────────────┘
           │              │
    ┌──────▼──────┐  ┌───▼──────────────┐
    │  Registries │  │   Event Handlers │
    │             │  │                  │
    │ SpecialEff  │  │ UmaSpecial       │
    │ UmaSkill    │  │ UmaSkillEvents   │
    │ (DataGen)   │  │ BladesDerbyConfig│
    └──────┬──────┘  │ DataGen          │
           │         └──────────────────┘
    ┌──────▼──────────────────┐
    │   Data Generation       │
    │                         │
    │  Recipes, Blade Defs    │
    │  Support Cards, Lang    │
    └─────────────────────────┘
```

## Entry Points

### Primary Entry
- `BladesDerby.java:18` — `@Mod(BladesDerby.MODID)` annotated class. Constructor called by Forge's mod loading system.
- `BladesDerby.java:26-32` — Constructor wires `SpecialEffectRegistry` and `UmaSkillRegistry` deferred registers to the mod event bus, and registers the config.

### Secondary Entry (Datagen)
- `DataGen.java:20` — `@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)` triggers on the `GatherDataEvent`.
- `DataGen.java:23-55` — Registers recipe provider, lang provider, and two `DatapackBuiltinEntriesProvider` instances (blade definitions + support cards).

### Event Handler Entry (Config)
- `BladesDerbyConfig.java:8` — `@Mod.EventBusSubscriber(modid = ..., bus = MOD)` — loads config values on `ModConfigEvent`.

### Event Handler Entry (Blade Behavior)
- `UmaSpecial.java:18` — `@EventBusSubscriber` (no modid constraint, fires for all mods) — handles SlashBlade update, power, and slash events.

### Event Handler Entry (Skill Attributes)
- `UmaSkillEvents.java:10` — `@EventBusSubscriber` — hooks into Umapyoi's `ApplyUmasoulAttributeEvent` to inject blade damage modifiers.

## Data Flow

### Blade Crafting → Use Flow
```
1. Player crafts blade via recipe (SlashBladeRecipeProvider)
2. Blade has UMA_SPECIAL effect pre-attached (BuiltInSlashBladeRegistry)
3. Player equips blade → SlashBladeEvent.UpdateEvent fires
   → UmaSpecial checks if player has UmaSoul:
     - No soul → debuffs (slow, weakness, mining fatigue)
     - Has soul → no debuffs
4. Player triggers power → SlashBladeEvent.PowerBladeEvent fires
   → UmaSpecial checks AP >= cost (configurable, default 20)
5. Player slashes → SlashBladeEvent.DoSlashEvent fires
   → UmaSpecial deducts AP (with discount if Breath of Nature skill)
```

### Skill → Attribute Flow
```
1. Player equips UmaSoul with Iaido skill
2. ApplyUmasoulAttributeEvent fires → UmaSkillEvents
3. Checks for IAIDO skill via UmaSoulUtils.hasSkill()
4. Adds +10% AttributeModifier to ModAttributes.getSlashBladeDamage()
```

## Key Abstractions

### Custom SpecialEffect: "Uma Soul Resonance"
- Class: `UmaSpecial` (`src/main/java/net/tracen/blades_derby/se/UmaSpecial.java`)
- Extends: `mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect`
- Registered via: `SpecialEffectRegistry.UMA_SPECIAL` (DeferredRegister)
- Config key: `se_ap_cost` (default 20)

### Custom UmaSkills
- `iaido` — PassiveSkill, level 2, wisdom 5. Adds blade damage via attribute modifier.
- `breath_of_nature` — PassiveSkill, level 2, wisdom 5. Reduces AP cost by 20% for Uma Soul Resonance.
- Registered via: `UmaSkillRegistry` using `DeferredRegister<UmaSkill>`

### Custom SupportCard
- `saint_lite_iaido` — SSR FRIENDSHIP card with STRENGTH_SUPPORT and Iaido skill.
- Registered via: `BuiltInSupportCardRegistry` (datagen, bootstrap context)

### Custom Named Blades (4 total)
- Registered via: `BuiltInSlashBladeRegistry` (datagen, bootstrap context)
- Defines model, texture, carry type, stats, default enchants, and special effects.

## Registration Pattern

All registry entries use the **DeferredRegister** pattern provided by Forge:

```java
// Registration
public static final DeferredRegister<T> REGISTRY = DeferredRegister.create(REGISTRY_KEY, MODID);
public static final RegistryObject<T> ENTRY = REGISTRY.register("name", Supplier::new);

// Wiring (in @Mod constructor)
REGISTRY.register(modEventBus);
```

For datapack registry entries (SlashBladeDefinition, SupportCard), the **RegistrySetBuilder + DatapackBuiltinEntriesProvider** pattern is used:
```java
RegistrySetBuilder builder = new RegistrySetBuilder().add(KEY, context -> { ... });
new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(MODID));
```

## Mod Lifecycle

1. **Construction** — `BladesDerby()` registers deferred registers and config
2. **Common Setup** — deferred registers resolve during Forge's registry events
3. **Config Load** — `BladesDerbyConfig.onLoad()` reads config values
4. **Datagen** (IDE/dev only) — `DataGen.dataGen()` on `GatherDataEvent`
5. **Runtime** — event handlers respond to SlashBlade/Umapyoi events

No custom packets, no entity rendering, no block entities, no world generation.
