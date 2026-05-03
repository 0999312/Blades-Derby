# Blades Derby — Migration

## What This Is

Blades Derby is a Minecraft mod addon that bridges **SlashBlade Resharped** and **Umapyoi**, adding 4 custom blades powered by Uma Soul energy, 2 custom Uma Skills, and a support card. This project is migrating the mod from **Minecraft 1.20.1 Forge** to **Minecraft 1.21.1 NeoForge**.

## Core Value

The mod compiles and runs on NeoForge 1.21.1 with all existing functionality intact — blades craftable, special effects working, skills applied, no behavioral regressions.

## Requirements

### Validated

- ✓ 4 named blades with custom models, stats, special effects, and recipes — existing (1.20.1)
- ✓ "Uma Soul Resonance" special effect — AP-based power/slash mechanics with debuffs — existing
- ✓ 2 custom Uma Skills (Iaido, Breath of Nature) — attribute modifiers and AP discount — existing
- ✓ 1 custom Support Card (Saint Lite Iaido) — existing
- ✓ Configurable AP cost via ForgeConfigSpec — existing
- ✓ Data generation for recipes, blade definitions, support cards, en_us lang — existing
- ✓ Manual zh_cn/zh_tw/zh_hk translations — existing

### Active

- [ ] Build system works on NeoForge 1.21.1 (NeoGradle, gradle props, mod metadata)
- [ ] Mod entry point @Mod compiles and loads on NeoForge
- [ ] Registries (SpecialEffect, UmaSkill) use NeoForge DeferredRegister
- [ ] Event handlers (UmaSpecial, UmaSkillEvents, BladesDerbyConfig) use NeoForge event system
- [ ] Config system migrated from ForgeConfigSpec to NeoForge equivalent
- [ ] Data generation works on NeoForge (GatherDataEvent, DatapackBuiltinEntriesProvider)
- [ ] Recipes generate correctly
- [ ] All Forge imports replaced with NeoForge equivalents
- [ ] runData produces identical output
- [ ] runClient launches without crashes
- [ ] All 4 blades testable in-game with full functionality

### Out of Scope

- New features or content — migration only
- Dual-version compatibility layer — explicitly excluded per AGENTS.md
- Network/packet system — mod has no custom networking
- Client rendering changes — mod has no custom renderers
- Capability system migration — mod doesn't use capabilities
- Patchouli book authoring — dependency exists but no book is authored
- JEI integration — dependency exists but no integration code
- Curios direct usage — declared but unused

## Context

- **Codebase**: 11 Java files, all under 100 lines, in `src/main/java/net/tracen/blades_derby/`
- **Dependencies**: SlashBlade Resharped 2.0.0-1.21.1, Umapyoi 1.4.11-1.21.1, MMLib 1.2.15-1.21.1 (all have 1.21.1 NeoForge versions confirmed in PREREQUISITES.md)
- **Build**: Currently ForgeGradle `[6.0,6.2)`, needs NeoGradle for 1.21.1
- **Mappings**: Currently `official` channel, 1.20.1. NeoForge 1.21.1 uses different mapping format.
- **Existing migration docs**: `docs/migration/` contains MIGRATION_PLAN.md, PROGRESS.md, LOADER_API_MAP.md, PREREQUISITES.md
- **Codebase map**: `.planning/codebase/` contains full analysis (STACK.md, ARCHITECTURE.md, STRUCTURE.md, CONVENTIONS.md, TESTING.md, INTEGRATIONS.md, CONCERNS.md)
- **Loader API research**: Uses `loader-diff-research` agent for Forge → NeoForge API mapping queries
- **Vanilla code research**: Uses `vanilla-code-research` agent for Minecraft version differences
- **Third-party deps**: Player Animation Lib, Curios, Patchouli, JEI — all exist as dependencies but mod code doesn't directly reference most of them. Curios, Patchouli, JEI are likely transitive or planned.

## Constraints

- **Tech stack**: Must use NeoForge 1.21.1, Java 21+, NeoGradle
- **Compatibility**: Must work with SlashBlade Resharped 2.0.0+ and Umapyoi 1.4.11+ (1.21.1 versions)
- **Mappings**: Must use Mojang official mappings (current project convention)
- **No dual version**: No compat layer — pure NeoForge 1.21.1
- **Minimal changes**: Prefer minimal diffs, fix compile errors first, then regressions
- **AGENTS.md rules**: Loader API unknowns must be resolved via `loader-diff-research` before changing code; vanilla differences via `vanilla-code-research`

## Key Decisions

| Decision | Rationale | Outcome |
|----------|-----------|---------|
| Migration only, no new features | User confirmed; clear scope boundary | ✓ Good |
| 3-phase structure (not 8) | Codebase is 11 small files with no networking/client/capabilities — simpler structure matches reality | ✓ Good |
| Follow existing mapping channel (official) | Project convention from 1.20.1 | — Pending |
| Resolve loader API differences before touching code | AGENTS.md rule to prevent guessing wrong APIs | ✓ Good |

## Evolution

This document evolves at phase transitions and milestone boundaries.

**After each phase transition** (via `/gsd-transition`):
1. Requirements invalidated? → Move to Out of Scope with reason
2. Requirements validated? → Move to Validated with phase reference
3. New requirements emerged? → Add to Active
4. Decisions to log? → Add to Key Decisions
5. "What This Is" still accurate? → Update if drifted

**After each milestone** (via `/gsd-complete-milestone`):
1. Full review of all sections
2. Core Value check — still the right priority?
3. Audit Out of Scope — reasons still valid?
4. Update Context with current state

---
*Last updated: 2026-05-04 after initialization*
