# LOADER_API_MAP

## 说明

记录已经确认的 `Forge -> NeoForge 1.21.1` loader API 映射。
只有在查询得到明确证据后才能写入 `Confirmed`。

## 条目格式

| ID | 旧 Forge API / 模式 | 使用意图 | NeoForge 1.21.1 替换方案 | 证据 | 状态 | 备注 |

## Phase 1 — Build System

| ID | 旧 Forge API / 模式 | 使用意图 | NeoForge 1.21.1 替换方案 | 证据 | 状态 | 备注 |

| B-01 | `net.minecraftforge.gradle` plugin `[6.0,6.2)` | ForgeGradle build plugin | `net.neoforged.gradle.userdev` version `7.1.25` | NeoGradle MDK 1.21 template | Confirmed | Runs at top level, `implementation "net.neoforged:neoforge:${neo_version}"` |
| B-02 | `minecraft "net.minecraftforge:forge:${mc}-${forge}"` | Minecraft+Forge dependency | `implementation "net.neoforged:neoforge:${neo_version}"` | NeoGradle MDK build.gradle | Confirmed | Standard Gradle dependency |
| B-03 | `fg.deobf("group:artifact:version")` | Deobfuscate mod deps | `implementation "group:artifact:version"` (no wrapper) | NeoGradle docs, MDK build.gradle | Confirmed | Standard Gradle dependency syntax |
| B-04 | `minecraft { mappings channel: x, version: y }` | Mapping configuration | `neogradle.subsystems.parchment.*` in gradle.properties | NeoGradle MDK gradle.properties | Confirmed | Parchment via gradle.properties |
| B-05 | `minecraft { runs { configureEach { property ... } } }` | Run configs inside minecraft block | `runs { configureEach { systemProperty ... } }` at top level | NeoGradle MDK build.gradle | Confirmed | `property` → `systemProperty`, runs at top level |
| B-06 | `minecraft { runs { ... mods { id { source ss } } } }` | Mod source declaration | `runs { configureEach { modSource project.sourceSets.main } }` | NeoGradle MDK build.gradle | Confirmed | `modSource` in configureEach |
| B-07 | `property 'forge.enabledGameTestNamespaces'` | GameTest namespace | `systemProperty 'neoforge.enabledGameTestNamespaces'` | Both MDK templates | Confirmed | `forge` → `neoforge` |
| B-08 | `property 'forge.logging.markers'` / `forge.logging.console.level` | Logging config in runs | Unchanged: still `forge.logging.markers`, `forge.logging.console.level` | Both MDK templates | Confirmed | NOT renamed to neoforge.logging |
| B-09 | `copyIdeResources = true` | IDE resource copying | Removed — handled automatically by NeoGradle | NeoGradle docs | Confirmed | No replacement needed |
| B-10 | `finalizedBy 'reobfJar'` | Reobfuscation after jar | Removed — handled automatically | Both MDK templates | Confirmed | Obfuscation is automatic |
| B-11 | `filesMatching(['META-INF/mods.toml', ...])` | Process resources | `filesMatching(['META-INF/neoforge.mods.toml', ...])` | NeoGradle MDK build.gradle | Confirmed | Filename changed |
| B-12 | `forge_version` / `forge_version_range` properties | Forge version variables | `neo_version=21.1.216`, `neo_version_range=[21.1,)` | NeoForge Maven metadata, MDK template | Confirmed | |

## Phase 1 — Mod Metadata & Entry Point

| ID | 旧 Forge API / 模式 | 使用意图 | NeoForge 1.21.1 替换方案 | 证据 | 状态 | 备注 |

| M-01 | `mods.toml` (Forge format) | Mod metadata file | `neoforge.mods.toml` | NeoForge docs (modfiles), MDK template | Confirmed | Same TOML structure |
| M-02 | `modId="forge"` in dependencies | Forge loader dependency | `modId="neoforge"` | NeoForge docs, MDK neoforge.mods.toml | Confirmed | |
| M-03 | `modLoader="javafml"` | FML language loader | Unchanged: `modLoader="javafml"` | NeoForge docs, MDK template | Confirmed | |
| M-04 | `net.minecraftforge.fml.common.Mod` | @Mod annotation | `net.neoforged.fml.common.Mod` | NeoForge docs, MDK ExampleMod.java | Confirmed | |
| M-05 | `FMLJavaModLoadingContext.get().getModEventBus()` | Get mod event bus | `IEventBus modEventBus` constructor parameter | NeoForge docs, MDK ExampleMod.java | Confirmed | Constructor injection |
| M-06 | `net.minecraftforge.eventbus.api.IEventBus` | IEventBus import | `net.neoforged.bus.api.IEventBus` | NeoForge docs, MDK ExampleMod.java | Confirmed | |
| M-07 | `ModLoadingContext.get().registerConfig(type, spec)` | Config registration | `modContainer.registerConfig(ModConfig.Type.COMMON, spec)` | NeoForge docs (config), MDK ExampleMod.java | Confirmed | ModContainer injected |
| M-08 | `net.minecraftforge.fml.ModLoadingContext` | ModLoadingContext | Removed — use `net.neoforged.fml.ModContainer` | NeoForge docs | Confirmed | |
| M-09 | `net.minecraftforge.fml.config.ModConfig` | ModConfig import | `net.neoforged.fml.config.ModConfig` | NeoForge docs, MDK ExampleMod.java | Confirmed | |
| M-10 | `new ResourceLocation(String, String)` | Create a resource location | `ResourceLocation.fromNamespaceAndPath(String, String)` | Minecraft 1.21 source | Confirmed | Constructor is now private in 1.21 |

## Phase 2 — Registration & Events

| ID | 旧 Forge API / 模式 | 使用意图 | NeoForge 1.21.1 替换方案 | 证据 | 状态 | 备注 |

| R-01 | `net.minecraftforge.registries.DeferredRegister` | Deferred register creation | `net.neoforged.neoforge.registries.DeferredRegister` | NeoForge source, docs | Confirmed | `create(ResourceKey, String)` sig unchanged |
| R-02 | `net.minecraftforge.registries.RegistryObject<T>` | Registry entry holder | `net.neoforged.neoforge.registries.DeferredHolder<R, T extends R>` | NeoForge source | Confirmed | Two type params. Also a Supplier<T> |
| R-03 | `net.minecraftforge.fml.common.Mod.EventBusSubscriber` | Auto-subscribe static events | `net.neoforged.fml.common.EventBusSubscriber` | NeoForge events docs | Confirmed | Standalone annotation, not nested in @Mod |
| R-04 | `Mod.EventBusSubscriber.Bus.FORGE` | Game event bus target | `EventBusSubscriber.Bus.GAME` (default) | NeoForge events docs | Confirmed | Default bus in 1.21.1 |
| R-05 | `net.minecraftforge.eventbus.api.SubscribeEvent` | Event handler annotation | `net.neoforged.bus.api.SubscribeEvent` | NeoForge events docs | Confirmed | Same annotation, different package |
| R-06 | `net.minecraftforge.common.ForgeConfigSpec` | Config spec builder | `net.neoforged.neoforge.common.ModConfigSpec` | NeoForge config docs | Confirmed | Builder API unchanged |
| R-07 | `@Mod.EventBusSubscriber(bus = MOD)` for config | Config load listener on MOD bus | `modEventBus.addListener(ConfigClass::onLoad)` in @Mod constructor | NeoForge config docs | Confirmed | Manual reg for MOD bus in 21.1.216+ |
| R-08 | `net.minecraftforge.registries.ForgeRegistries` | Registry access | `net.neoforged.neoforge.registries.NeoForgeRegistries` | NeoForge docs | Confirmed | For datagen/reference |
| R-09 | `AttributeModifier.Operation.MULTIPLY_TOTAL` | Attribute operation enum | `AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL` | Minecraft 1.21 source | Confirmed | Enum rename in 1.21 |
| R-10 | `net.minecraftforge.fml.event.config.ModConfigEvent` | Config event | `net.neoforged.fml.event.config.ModConfigEvent` | NeoForge config docs | Confirmed | Same class, different package |

## Open

| ID | 待确认旧 API / 模式 | 文件位置 | 需要确认的问题 | 下一次查询建议 |
|---|---|---|---|---|---|

| O-01 | `net.minecraftforge.data.event.GatherDataEvent` | DataGen.java | NeoForge package for datagen events | loader-docs: "GatherDataEvent neoforge" |
| O-02 | `net.minecraftforge.common.data.DatapackBuiltinEntriesProvider` | DataGen.java | NeoForge package and API | loader-docs: "DatapackBuiltinEntriesProvider neoforge" |
| O-03 | `mods.flammpfeil.slashblade.init.SBItems` | SlashBladeRecipeProvider.java | SB 2.0.0 moved/renamed SBItems | SlashBlade 2.0.0 API docs/source |
| O-04 | `BootstapContext` / `Registerable` | BuiltInSlashBladeRegistry, BuiltInSupportCardRegistry | Correct 1.21.1 package for datagen registry context | mc-source: "Registerable 1.21" |
| O-05 | `Attribute` → `Holder<Attribute>` | UmaSkillEvents.java | How to get Holder<Attribute> from SlashBlade ModAttributes | mc-source: "Holder<Attribute> 1.21" |
| O-06 | `net.minecraftforge.common.crafting.conditions.IConditionBuilder` | SlashBladeRecipeProvider.java | NeoForge equivalent | loader-docs: "IConditionBuilder neoforge" |
| O-07 | `net.minecraftforge.common.Tags` | SlashBladeRecipeProvider.java | NeoForge Tags package and member names | loader-docs: "Tags.Items neoforge" |
