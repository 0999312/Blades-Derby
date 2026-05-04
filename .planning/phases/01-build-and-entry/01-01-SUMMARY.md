# 01-01 COMPLETE

**Plan:** Gradle properties & plugin repos for NeoForge 1.21.1
**Status:** Done
**Files:** gradle.properties, settings.gradle

## Results

### gradle.properties
- `minecraft_version=1.21.1` (was 1.20.1)
- `neo_version=21.1.216` (replaces forge_version=47.3.1)
- `neo_version_range=[21.1,)` (replaces forge_version_range)
- `loader_version_range=[2,)` (was [47,))
- `mapping_channel` removed (Parchment via neogradle.subsystems.parchment.*)
- `player_anim=2.0.4+1.21.1` (was 1.0.2-rc1+1.20)
- `jei_version=19.27.0.340` (was 15.20.0.105)
- New: `curios_version=9.5.1+1.21.1`
- New: `patchouli_version=1.21.1-93-NEOFORGE`
- New: `neogradle.subsystems.parchment.minecraftVersion=1.21`
- New: `neogradle.subsystems.parchment.mappingsVersion=2024.11.10`
- All mod metadata preserved

### settings.gradle
- NeoForge maven (`https://maven.neoforged.net/releases`) added
- Forge maven removed
- ModPublisher maven kept

### Research
- NeoForge Maven metadata confirmed version 21.1.216
- Parchment version 2024.11.10 from official NeoGradle MDK template
- All dependency versions confirmed via Modrinth API and KosmX maven
