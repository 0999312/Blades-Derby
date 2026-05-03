# Requirements: Blades Derby Migration

**Defined:** 2026-05-04
**Core Value:** The mod compiles and runs on NeoForge 1.21.1 with all existing functionality intact — no behavioral regressions.

## v1 Requirements

Requirements for the migration. Each maps to roadmap phases.

### Build & Entry

- [ ] **BUILD-01**: Gradle buildscript migrated to NeoGradle (ForgeGradle → NeoGradle)
- [ ] **BUILD-02**: gradle.properties updated for NeoForge 1.21.1 (minecraft_version, neo_version, mappings)
- [ ] **BUILD-03**: settings.gradle updated with NeoForge plugin repositories
- [ ] **BUILD-04**: Mod metadata migrated (mods.toml → neoforge.mods.toml with updated format)
- [ ] **BUILD-05**: @Mod entry point compiles on NeoForge (FMLJavaModLoadingContext → IEventBus)
- [ ] **BUILD-06**: ./gradlew compileJava passes without errors
- [ ] **BUILD-07**: Dependency versions and repositories updated for 1.21.1 NeoForge artifacts

### Registration & Events

- [ ] **REG-01**: DeferredRegister migration for SpecialEffectRegistry (Forge → NeoForge register system)
- [ ] **REG-02**: DeferredRegister migration for UmaSkillRegistry
- [ ] **REG-03**: Event handlers migrated — UmaSpecial (SlashBlade events)
- [ ] **REG-04**: Event handlers migrated — UmaSkillEvents (Umapyoi attribute events)
- [ ] **REG-05**: Config system migrated (ForgeConfigSpec → NeoForge equivalent)
- [ ] **REG-06**: BladesDerbyConfig @EventBusSubscriber and config loading updated

### Datagen & Cleanup

- [ ] **DATA-01**: DataGen GatherDataEvent handler migrated to NeoForge equivalent
- [ ] **DATA-02**: DatapackBuiltinEntriesProvider migrated for blade definitions
- [ ] **DATA-03**: DatapackBuiltinEntriesProvider migrated for support card definitions
- [ ] **DATA-04**: SlashBladeRecipeProvider migrated (ForgeRegistries, Tags, IConditionBuilder)
- [ ] **DATA-05**: BDLangProvider migration (via MMLib's AbstractLangProvider update)
- [ ] **DATA-06**: All remaining Forge imports cleaned up across all files
- [ ] **DATA-07**: ./gradlew runData generates identical output to 1.20.1
- [ ] **DATA-08**: ./gradlew runClient launches without crashes

## v2 Requirements

None — migration is a one-time project with no deferred scope.

## Out of Scope

| Feature | Reason |
|---------|--------|
| New blades, skills, or content | Migration only — feature freeze |
| Dual-version compatibility layer | Explicitly excluded per AGENTS.md |
| Network/packet system | Mod has no custom networking |
| Custom client rendering | Mod has no custom renderers |
| Capability system | Mod doesn't use capabilities |
| Patchouli guidebook | Dependency exists but no book content planned |
| JEI plugin | Dependency exists but no integration planned |
| Curios integration | Dependency declared but unused in code |

## Traceability

| Requirement | Phase | Status |
|-------------|-------|--------|
| BUILD-01 | Phase 1 | Pending |
| BUILD-02 | Phase 1 | Pending |
| BUILD-03 | Phase 1 | Pending |
| BUILD-04 | Phase 1 | Pending |
| BUILD-05 | Phase 1 | Pending |
| BUILD-06 | Phase 1 | Pending |
| BUILD-07 | Phase 1 | Pending |
| REG-01 | Phase 2 | Pending |
| REG-02 | Phase 2 | Pending |
| REG-03 | Phase 2 | Pending |
| REG-04 | Phase 2 | Pending |
| REG-05 | Phase 2 | Pending |
| REG-06 | Phase 2 | Pending |
| DATA-01 | Phase 3 | Pending |
| DATA-02 | Phase 3 | Pending |
| DATA-03 | Phase 3 | Pending |
| DATA-04 | Phase 3 | Pending |
| DATA-05 | Phase 3 | Pending |
| DATA-06 | Phase 3 | Pending |
| DATA-07 | Phase 3 | Pending |
| DATA-08 | Phase 3 | Pending |

**Coverage:**
- v1 requirements: 21 total
- Mapped to phases: 21
- Unmapped: 0 ✓

---
*Requirements defined: 2026-05-04*
*Last updated: 2026-05-04 after initial definition*
