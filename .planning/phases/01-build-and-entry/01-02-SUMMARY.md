# 01-02 COMPLETE

**Plan:** build.gradle migration to NeoGradle
**Status:** Done
**Files:** build.gradle

## Results

### Plugin
- `net.minecraftforge.gradle` [6.0,6.2) → `net.neoforged.gradle.userdev` 7.1.25
- Gradle wrapper: 8.8 → 8.14.3 (required by NeoGradle 7.1.25)

### Java Toolchain
- Java 17 → Java 21

### Build Structure
- Removed `minecraft {}` block entirely
- `runs {}` at top level (not inside minecraft)
- Mappings handled via `neogradle.subsystems.parchment.*` in gradle.properties
- `property` → `systemProperty` for run configs
- `mods { source }` → `modSource project.sourceSets.main`
- `args` → `arguments`
- GameTest: `forge.enabledGameTestNamespaces` → `neoforge.enabledGameTestNamespaces`
- `copyIdeResources = true` removed (handled automatically)
- `finalizedBy 'reobfJar'` removed (handled automatically)

### Dependencies
- All `fg.deobf()` wrappers removed
- NeoForge base: `implementation "net.neoforged:neoforge:${neo_version}"`
- MMLib: `maven.modrinth:mmlib:1.2.15-1.21.1`
- Curios: `top.theillusivec4.curios:curios-neoforge:${curios_version}`
- SlashBlade: `mods.flammpfeil.slashblade:SlashBlade_Resharped:2.0.0-1.21.1`
- Umapyoi: `maven.modrinth:umapyoi:1.4.11-1.21.1`
- Player Anim: `dev.kosmx.player-anim:player-animation-lib-forge:${player_anim}`
- Patchouli: `vazkii.patchouli:Patchouli:${patchouli_version}`
- JEI: `mezz.jei:jei-${minecraft_version}-neoforge*:${jei_version}`

### processResources
- `filesMatching(['META-INF/neoforge.mods.toml', 'pack.mcmeta'])`
- `forge_version`/`forge_version_range` → `neo_version`/`neo_version_range` in replaceProperties

### Publishing
- `from components.java` (handles obfuscation automatically)

### Publisher
- Game versions: `"1.21.1"` only
- Loaders: `"neoforge"` only
- Display name: `"[1.21.1] Blades Derby - ${project.version}"`
- Java: `["Java 21"]` (fixed varargs as list)

### Verification
- `./gradlew compileJava`: Plugin & dependency resolution SUCCESSFUL
- Source compilation fails with 94 errors (Forge API in other files → Phase 2)
