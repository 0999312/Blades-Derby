# 01-03 COMPLETE

**Plan:** Mod metadata & @Mod entry point migration
**Status:** Done
**Files:** neoforge.mods.toml, BladesDerby.java

## Results

### neoforge.mods.toml (new file)
- Created from mods.toml with NeoForge format
- `modId="forge"` → `modId="neoforge"`
- `forge_version_range` → `neo_version_range`
- SlashBlade version range: `[2.0.0,)` (was `[1.1.28,)`)
- Umapyoi version range: `[1.4.11-1.21.1,)` (was `[1.9.59-1.20.1,)`)
- `modLoader="javafml"` unchanged
- Original `mods.toml` deleted

### BladesDerby.java
- `FMLJavaModLoadingContext` → Removed (does not exist in NeoForge 1.21.1)
- `ModLoadingContext` → Removed (replaced by ModContainer injection)
- Constructor injection: `IEventBus modEventBus` + `ModContainer modContainer`
- Config: `modContainer.registerConfig(ModConfig.Type.COMMON, spec)`
- `ResourceLocation(MODID, path)` → `ResourceLocation.fromNamespaceAndPath(MODID, path)` (vanilla 1.21 change)
- Imports updated to `net.neoforged.*` packages
- Registry wiring preserved (`.register(modEventBus)`)

### Other fixes made during execution
- `player_anim` version corrected from `2.0.4+1.21.1-forge` to `2.0.4+1.21.1` (actual KosmX maven version)
- `programArguments` → `arguments` (deprecation fix)
- `setJavaVersions("Java 21")` → `setJavaVersions(["Java 21"])` (varargs vs list type fix)
