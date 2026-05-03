# Phase 1: Build & Entry - Context

**Gathered:** 2026-05-04
**Status:** Ready for planning

## Phase Boundary

Get the Gradle build system fully migrated to NeoGradle 1.21.1: `./gradlew compileJava` passes, all dependency coordinates resolve, mod metadata is correct NeoForge format, and the mod entry point compiles.

## Implementation Decisions

### Dependency Cleanup
- **D-01:** Keep Curios — required by SlashBlade Resharped at runtime (accessory slot system)
- **D-02:** Keep Patchouli — required by parent mod (Umapyoi or SlashBlade) at runtime
- **D-03:** Keep JEI — needed for recipe review during development (runtimeOnly)
- **D-04:** Keep Player Animation Lib explicit — SlashBlade requirement, declare explicitly for version control
- **D-05:** Upgrade MMLib to 1.21.1 version (`maven.modrinth:mmlib:1.2.15-1.21.1`) — don't drop or inline AbstractLangProvider
- **D-06:** Keep modpublisher plugin (`com.hypherionmc.modutils.modpublisher:2.1.6`) — version-independent publishing tool
- **D-07:** Keep all custom Maven repositories (MMMaven, Curios, KosmX, BlameJared, Modrinth)
- **D-08:** Java toolchain upgraded from 17 to 21 — NeoForge 1.21.1 requirement

### Mappings Strategy
- **D-09:** Switch from Mojang official to Parchment mappings for 1.21.1 — human-readable parameter names aid migration debugging. Adds ParchmentMC maven repository. Specific version to be determined by research.

### Publishing Configuration
- **D-10:** Loaders: `"neoforge"` only (drop `"forge"`)
- **D-11:** Game versions: `"1.21.1"` only
- **D-12:** Display name: `"[1.21.1] Blades Derby - ${project.version}"` (follow existing pattern)
- **D-13:** Required deps: keep `slashblade-resharped` + `umapyoi` for both CurseForge and Modrinth

### Agent's Discretion
- Specific Parchment version — research will find the latest stable Parchment release for 1.21.1
- NeoGradle version — use recommended version for 1.21.1 as documented by NeoForge
- neoforge.mods.toml format details — use loader-diff-research to confirm exact NeoForge format
- Dependency resolution syntax (replacing `fg.deobf()`) — use loader-diff-research to confirm NeoGradle conventions
- Gradle plugin management for NeoGradle in settings.gradle

## Canonical References

**Downstream agents MUST read these before planning or implementing.**

### Project Artifacts
- `.planning/ROADMAP.md` — Phase 1 requirements (BUILD-01 → BUILD-07) and success criteria
- `.planning/PROJECT.md` — Project constraints (migration only, no dual version, minimal changes)
- `.planning/REQUIREMENTS.md` — Full requirement traceability with REQ-IDs

### Migration Documentation
- `docs/migration/PREREQUISITES.md` — Confirmed 1.21.1 NeoForge dependency coordinates for SlashBlade, Umapyoi, MMLib
- `docs/migration/LOADER_API_MAP.md` — API mapping tracker (empty — fill during execution via loader-diff-research)
- `AGENTS.md` — Migration rules: use loader-diff-research for loader API unknowns, vanilla-code-research for Minecraft version differences

### Codebase Analysis
- `.planning/codebase/STACK.md` — Full build system details (Gradle plugins, dependency versions, repositories)
- `.planning/codebase/INTEGRATIONS.md` — Dependency integration details and mod interop points

### Build Files (current, to be migrated)
- `build.gradle` — Current ForgeGradle build configuration
- `gradle.properties` — Version variables (minecraft_version, forge_version, mapping_channel)
- `settings.gradle` — Plugin repositories
- `src/main/resources/META-INF/mods.toml` — Forge mod metadata (to become neoforge.mods.toml)

## Existing Code Insights

### Current Build System
- Plugin: `net.minecraftforge.gradle` version `[6.0,6.2)` → replace with `net.neoforged.gradle`
- Mappings: `mapping_channel=official`, `mapping_version=1.20.1` → Parchment for 1.21.1
- Java toolchain: 17 → 21
- Dependency wrapper: all deps use `fg.deobf()` → NeoGradle equivalent
- Run configs: client, server, gameTestServer, data → NeoGradle equivalents
- Resource processing: `${}` expansion for mods.toml and pack.mcmeta → verify NeoGradle compatibility

### mods.toml Format
- ModLoader: `"javafml"` — NeoForge uses similar but may have format changes
- Dependencies declared: forge, minecraft, slashblade, umapyoi
- Variable expansion: `${mod_id}`, `${forge_version_range}`, etc. from gradle.properties

### Settings & Repositories
- `settings.gradle` plugin repos: gradlePluginPortal, MinecraftForge maven, ModPublisher maven → add NeoForge maven
- Dependency repos in `build.gradle`: MMMaven, Curios (theillusivec4), KosmX, BlameJared, Modrinth → all kept

### Third-Party Dep 1.21.1 Coordinates (from PREREQUISITES.md)
- SlashBlade: `mods.flammpfeil.slashblade:SlashBlade_Resharped:2.0.0-1.21.1`
- Umapyoi: `maven.modrinth:umapyoi:1.4.11-1.21.1`
- MMLib: `maven.modrinth:mmlib:1.2.15-1.21.1`

## Specific Ideas

No specific requirements — open to standard NeoForge 1.21.1 build conventions.

## Deferred Ideas

None — discussion stayed within phase scope.

---
*Phase: 1-Build & Entry*
*Context gathered: 2026-05-04*
