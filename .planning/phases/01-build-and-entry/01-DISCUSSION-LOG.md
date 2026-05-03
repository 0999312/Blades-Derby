# Phase 1: Build & Entry — Discussion Log

**Date:** 2026-05-04
**Mode:** Default (interactive)

## Areas Discussed

### Dependency Cleanup

| Question | Options | Selected | Notes |
|----------|---------|----------|-------|
| Curios — drop or keep? | Drop it / Keep it (future) / Keep it (required) | Keep it (required) | Required by SlashBlade Resharped at runtime |
| Patchouli — drop or keep? | Drop it / Keep it (future) / Keep it (required) | Keep it (required) | Required by parent mod |
| JEI — drop or keep? | Drop it / Keep it (future) / Keep it (required) | Keep it (required) | User: "Keep it for recipes review" |
| Player Animation Lib — drop or keep explicit? | Drop it (transitive) / Keep explicit | Keep explicit | SlashBlade requirement |
| **Extended round** | | | |
| MMLib — upgrade or drop? | Upgrade to 1.21.1 / Drop and inline | Upgrade to 1.21.1 | |
| Modpublisher plugin — keep or drop? | Keep & update / Drop it | Keep & update | "版本无关插件，用于发布" |
| Custom Maven repos — keep or drop? | Keep all / Trim unused | Keep all | |
| Java toolchain — 17 or 21? | Java 17 / Java 21 | Java 21 | NeoForge 1.21.1 requires Java 21 |

### Mappings Strategy

| Question | Options | Selected | Notes |
|----------|---------|----------|-------|
| Official or Parchment for 1.21.1? | Stick with official / Switch to Parchment | Switch to Parchment | Better readability aids migration |

### Publishing Configuration

| Question | Options | Selected | Notes |
|----------|---------|----------|-------|
| Loaders for publishing | neoforge only / forge + neoforge | neoforge only | Pure NeoForge 1.21.1 |
| Game versions | 1.21.1 only / 1.21.x range | 1.21.1 only | |
| Display name format | [1.21.1] prefix / No prefix | [1.21.1] prefix | Follow existing pattern |
| Required deps (CurseForge/Modrinth) | Keep as-is / Adjust | Keep as-is | slashblade-resharped + umapyoi |

## Summary

13 implementation decisions captured across 3 areas. Key outcomes:
- All existing dependencies preserved (Curios/Patchouli/JEI/PlayerAnim are required by parent mods or dev workflow)
- MMLib upgraded to 1.21.1 NeoForge version
- Modpublisher kept for CurseForge + Modrinth publishing
- Parchment mappings selected for 1.21.1 (human-readable parameter names)
- Publishing: NeoForge only, 1.21.1 only, [1.21.1] prefix

## Deferred Ideas

None.

---
*Discussion completed: 2026-05-04*
*Areas: 3 | Questions: 13 | Decisions: 13*
