---
last_mapped_commit: unknown
---

# STACK.md — Technology Stack

> Last updated: 2026-05-04

## Language & Runtime

| Property | Value |
|---|---|
| Language | Java 17 |
| Runtime | JVM (OpenJDK 17+) |

## Build System

| Property | Value |
|---|---|
| Build tool | Gradle |
| Gradle wrapper | Included (wrapper scripts present) |
| JVM args | `-Xmx3G` |
| Daemon | Disabled |

### Gradle Plugins

| Plugin | Version | Purpose |
|---|---|---|
| `net.minecraftforge.gradle` | `[6.0,6.2)` | Forge MDK — Minecraft mod compilation, obfuscation, run configs |
| `com.hypherionmc.modutils.modpublisher` | `2.1.6` | Publish to CurseForge & Modrinth |
| `maven-publish` | built-in | Local Maven publishing to `mcmodsrepo` |
| `eclipse` | built-in | Eclipse IDE support |
| `idea` | built-in | IntelliJ IDEA support |
| `org.gradle.toolchains.foojay-resolver-convention` | `0.7.0` | JDK toolchain resolution |

## Framework

| Property | Value |
|---|---|
| Mod loader | Minecraft Forge (javafml) |
| Minecraft version | 1.20.1 |
| Forge version | 47.3.1 |
| Mappings channel | official |
| Mappings version | 1.20.1 |

## Dependencies

### Direct Dependencies

| Dependency | Version | Scope | Purpose |
|---|---|---|---|
| `net.minecraftforge:forge` | `1.20.1-47.3.1` | minecraft | Forge runtime + MCP mappings |
| `cn.mcmod_mmf.mysterious_mountain_lib:MMLib` | `1.5.22-1.20.1` | implementation | AbstractLangProvider, base utilities for data generation |
| `top.theillusivec4.curios:curios-forge` | `5.14.1+1.20.1` | compileOnly (api) + runtimeOnly | Curios accessory slot API |
| `maven.modrinth:slashblade-resharped` | `1.4.44` | implementation | SlashBlade core — custom sword mechanics, SpecialEffect, registry keys |
| `maven.modrinth:umapyoi` | `1.9.59-1.20.1` | implementation | Uma Musume themed mod — UmaSkill, SupportCard, UmaSoul API |
| `dev.kosmx.player-anim:player-animation-lib-forge` | `1.0.2-rc1+1.20` | implementation | Player animation library (transitive dep of slashblade-resharped) |
| `vazkii.patchouli:Patchouli` | `1.20.1-83-FORGE` | compileOnly + runtimeOnly | In-game documentation book API |
| `mezz.jei:jei` | `15.20.0.105` | compileOnly (api) + runtimeOnly | JEI recipe viewer integration |

### Dependency Repositories

- `https://raw.github.com/0999312/MMMaven/main/repository` — MMLib
- `https://maven.theillusivec4.top/` — Curios
- `https://maven.kosmx.dev/` — Player Animation Lib
- `https://maven.blamejared.com` — Patchouli
- `https://api.modrinth.com/maven` — SlashBlade Resharped, Umapyoi

### Required Mod Dependencies (mods.toml)

| Mod ID | Version Range | Ordering | Required |
|---|---|---|---|
| `forge` | `[47,)` | NONE | Yes |
| `minecraft` | `[1.20.1,1.21)` | NONE | Yes |
| `slashblade` | `[1.1.28,)` | AFTER | Yes |
| `umapyoi` | `[1.9.59-1.20.1,)` | AFTER | Yes |

## Configuration

| File | Purpose |
|---|---|
| `gradle.properties` | Minecraft/Forge version, mod metadata, dependency versions |
| `build.gradle` | Build logic, repositories, dependencies, publishing |
| `settings.gradle` | Plugin repositories, toolchain resolver |
| `mods.toml` | Forge mod metadata (modLoader, deps, display info) |
| `pack.mcmeta` | Minecraft resource pack metadata (format 15) |
| `BladesDerbyConfig.java` (runtime) | `ForgeConfigSpec` — AP cost for Uma Soul Resonance (`se_ap_cost`, default 20, range 0–MAX_INT) |

## Publishing

| Platform | ID |
|---|---|
| CurseForge | `1232462` |
| Modrinth | `blades-derby` |
| Local Maven | `mcmodsrepo/` |

## Generated Resources

- `src/generated/resources/` — data generator output (recipes, named blades, advancements, en_us lang)
- Included via `sourceSets.main.resources { srcDir 'src/generated/resources' }`

## Assets

| Type | Path | Count |
|---|---|---|
| Lang (manual) | `src/main/resources/assets/blades_derby/lang/` | 3 files (zh_cn, zh_tw, zh_hk) |
| Lang (generated) | `src/generated/resources/assets/blades_derby/lang/` | 1 file (en_us) |
| Models | `src/main/resources/assets/blades_derby/model/` | 4 .obj + 4 .png |
