---
last_mapped_commit: unknown
---

# STRUCTURE.md — Directory Layout

> Last updated: 2026-05-04

## Repository Root

```
Blades-Derby/
├── .gitignore
├── .opencode/
│   └── .gitignore
├── README.md
├── AGENTS.md
├── build.gradle                # Build logic, deps, publishing
├── gradle.properties           # Version variables, mod metadata
├── settings.gradle             # Plugin repos, toolchain config
├── gradlew / gradlew.bat       # Gradle wrapper scripts
├── gradle/
│   └── wrapper/
├── docs/
│   └── migration/              # Migration planning docs
│       ├── LOADER_API_MAP.md
│       ├── MIGRATION_PLAN.md
│       ├── PREREQUISITES.md
│       └── PROGRESS.md
├── .planning/
│   └── codebase/               # Codebase mapping output
└── src/
    ├── main/
    │   ├── java/net/tracen/blades_derby/
    │   │   ├── BladesDerby.java              # @Mod entry point
    │   │   ├── BladesDerbyConfig.java         # Forge config spec
    │   │   ├── se/
    │   │   │   ├── SpecialEffectRegistry.java # DeferredRegister for SpecialEffect
    │   │   │   └── UmaSpecial.java            # "Uma Soul Resonance" special effect logic
    │   │   ├── umaskill/
    │   │   │   ├── UmaSkillRegistry.java      # DeferredRegister for UmaSkill
    │   │   │   └── UmaSkillEvents.java        # Skill attribute event handler
    │   │   └── data/
    │   │       ├── DataGen.java               # GatherDataEvent handler
    │   │       ├── BDLangProvider.java        # Language generation (en_us)
    │   │       ├── SlashBladeRecipeProvider.java # Blade crafting recipes
    │   │       └── builtin/
    │   │           ├── BuiltInSlashBladeRegistry.java  # Named blade definitions
    │   │           └── BuiltInSupportCardRegistry.java # Support card definitions
    │   └── resources/
    │       ├── META-INF/
    │       │   └── mods.toml                  # Forge mod metadata
    │       ├── pack.mcmeta                     # Resource pack metadata
    │       └── assets/blades_derby/
    │           ├── lang/
    │           │   ├── zh_cn.json              # Simplified Chinese translations
    │           │   ├── zh_tw.json              # Traditional Chinese translations
    │           │   └── zh_hk.json              # Hong Kong Chinese translations
    │           └── model/
    │               ├── uma_hishi.obj / .png
    │               ├── uma_black.obj / .png
    │               ├── uma_odachi_haru.obj / .png
    │               └── uma_odachi_halo.obj / .png
    └── generated/
        └── resources/
            ├── .cache/                         # Datagen cache files
            ├── assets/blades_derby/lang/
            │   └── en_us.json                  # Generated English translations
            └── data/blades_derby/
                ├── advancements/recipes/combat/ # Recipe advancement triggers
                ├── recipes/                     # Crafting recipe JSONs
                └── slashblade/named_blades/     # Blade definition JSONs
```

## Key Locations

### Source Entry Point
- `src/main/java/net/tracen/blades_derby/BladesDerby.java` — `@Mod` class, mod ID constant, constructor

### Registries
- `src/main/java/net/tracen/blades_derby/se/SpecialEffectRegistry.java` — SpecialEffect registry
- `src/main/java/net/tracen/blades_derby/umaskill/UmaSkillRegistry.java` — UmaSkill registry

### Event Handlers
- `src/main/java/net/tracen/blades_derby/se/UmaSpecial.java` — SlashBlade events (update, power, slash)
- `src/main/java/net/tracen/blades_derby/umaskill/UmaSkillEvents.java` — Umapyoi attribute events
- `src/main/java/net/tracen/blades_derby/BladesDerbyConfig.java` — Config load event
- `src/main/java/net/tracen/blades_derby/data/DataGen.java` — Datagen event

### Data Generation
- `src/main/java/net/tracen/blades_derby/data/DataGen.java` — Orchestrator
- `src/main/java/net/tracen/blades_derby/data/SlashBladeRecipeProvider.java` — Recipes
- `src/main/java/net/tracen/blades_derby/data/BDLangProvider.java` — En_US lang
- `src/main/java/net/tracen/blades_derby/data/builtin/BuiltInSlashBladeRegistry.java` — Blade defs
- `src/main/java/net/tracen/blades_derby/data/builtin/BuiltInSupportCardRegistry.java` — Support card defs

### Resources
- `src/main/resources/META-INF/mods.toml` — Mod metadata for Forge
- `src/main/resources/assets/blades_derby/lang/` — Manual translations (zh_*)
- `src/main/resources/assets/blades_derby/model/` — 3D models and textures

### Build & Config
- `build.gradle` — All build logic in a single file
- `gradle.properties` — All version variables in a single file
- `settings.gradle` — Plugin repository configuration

## Package Naming Convention

- Root package: `net.tracen.blades_derby`
- Sub-packages by feature domain:
  - `se` — Special Effects (SlashBlade mechanics)
  - `umaskill` — Uma Skills
  - `data` — Data generation
  - `data.builtin` — Built-in datapack registries

## File Count

| Directory | Java Files | Resource Files |
|---|---|---|
| `se/` | 2 | 0 |
| `umaskill/` | 2 | 0 |
| `data/` | 3 | 0 |
| `data/builtin/` | 2 | 0 |
| Root package | 2 | 0 |
| **Total Java** | **11** | |
| assets/lang/ | 0 | 3 (zh_*) + 1 (en_us generated) |
| assets/model/ | 0 | 4 (.obj) + 4 (.png) |
