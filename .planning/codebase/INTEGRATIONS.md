---
last_mapped_commit: unknown
---

# INTEGRATIONS.md — External Integrations

> Last updated: 2026-05-04

## Mod Integration Summary

This mod is an **addon** that bridges two mods: **SlashBlade Resharped** and **Umapyoi**. It has no external network APIs, databases, auth providers, or webhooks. All integrations are in-modloader.

---

## 1. SlashBlade Resharped

**Dependency scope:** implementation (required at both compile and runtime)

### Integration Points

| Class | What it uses | Purpose |
|---|---|---|
| `SpecialEffectRegistry` | `SpecialEffect.REGISTRY_KEY`, `DeferredRegister<SpecialEffect>` | Registers a custom SpecialEffect ("Uma Soul Resonance") |
| `UmaSpecial` | `SpecialEffect`, `ISlashBladeState`, `SlashBladeEvent.UpdateEvent`, `SlashBladeEvent.PowerBladeEvent`, `SlashBladeEvent.DoSlashEvent` | Logic for checking/activating the special effect on blade updates, power, and slash events |
| `UmaSkillEvents` | `ModAttributes.getSlashBladeDamage()` | Adds SlashBlade damage attribute modifier through Umapyoi's attribute system |
| `SlashBladeRecipeProvider` | `SBItems`, `SlashBladeIngredient`, `SlashBladeShapedRecipeBuilder`, `RequestDefinition`, `EnchantmentDefinition` | Defines crafting recipes for 4 custom blades |
| `BuiltInSlashBladeRegistry` | `SlashBladeDefinition`, `RenderDefinition`, `PropertiesDefinition`, `SlashArtsRegistry`, `CarryType`, `SwordType` | Registers 4 named blade definitions with models, textures, and default stats |
| `BDLangProvider` | SlashBlade name translations (`addSlashBlade()`) | Localized blade display names |

### Blade Definitions Registered

| Key | Model | Carry Type | Base Attack | SA Type |
|---|---|---|---|---|
| `uma_hishi` | `uma_hishi.obj/.png` | KATANA | 7.0 | DRIVE_VERTICAL |
| `uma_black` | `uma_black.obj/.png` | KATANA | 7.0 | VOID_SLASH |
| `uma_odachi_haru` | `uma_odachi_haru.obj/.png` | PSO2 | 9.0 | SAKURA_END |
| `uma_odachi_halo` | `uma_odachi_halo.obj/.png` | PSO2 | 9.0 | CIRCLE_SLASH |

All 4 blades have `SwordType.BEWITCHED` and the `UMA_SPECIAL` effect attached.

### Events Subscribed

- `SlashBladeEvent.UpdateEvent` — applies debuffs if player has no Uma Soul equipped
- `SlashBladeEvent.PowerBladeEvent` — checks AP cost before allowing power activation
- `SlashBladeEvent.DoSlashEvent` — deducts AP on slash (with skill discount)

---

## 2. Umapyoi

**Dependency scope:** implementation (required at both compile and runtime)

### Integration Points

| Class | What it uses | Purpose |
|---|---|---|
| `UmaSkillRegistry` | `UmaSkill.REGISTRY_KEY`, `DeferredRegister<UmaSkill>`, `PassiveSkill` | Registers 2 custom UmaSkills ("Iaido", "Breath of Nature") |
| `UmaSpecial` | `UmapyoiAPI.getUmaSoul()`, `UmaSoulUtils.getActionPoint()`, `UmaSoulUtils.setActionPoint()`, `UmaSoulUtils.hasSkill()` | Reads/modifies UmaSoul data (AP, skills) |
| `UmaSkillEvents` | `ApplyUmasoulAttributeEvent`, `UmaSoulUtils.hasSkill()` | Hooks into Umapyoi's attribute system to add SlashBlade damage bonus |
| `BuiltInSupportCardRegistry` | `SupportCard`, `SupportType`, `TrainingSupportRegistry`, `GachaRanking`, `UmaSkillUtils` | Registers 1 custom SupportCard |
| `SlashBladeRecipeProvider` | `ItemRegistry.JEWEL` | Uses Umapyoi's JEWEL item as a recipe ingredient |
| `BDLangProvider` | Skill translations, support card translations | Localized skill/card display names |

### Skills Registered

| Skill | Type | Level | Wisdom | Effect |
|---|---|---|---|---|
| `iaido` | PassiveSkill | 2 | 5 | +10% SlashBlade damage (via attribute modifier) |
| `breath_of_nature` | PassiveSkill | 2 | 5 | 20% AP cost reduction for Uma Soul Resonance |

### Support Card Registered

| Card | Ranking | Type | Max Damage | Supports |
|---|---|---|---|---|
| `saint_lite_iaido` | SSR | FRIENDSHIP | 5 | Saint Lite, STRENGTH_SUPPORT, Iaido skill |

### Events Subscribed

- `ApplyUmasoulAttributeEvent` — adds `AttributeModifier` for `ModAttributes.getSlashBladeDamage()` when Iaido skill is present

---

## 3. Curios

**Dependency scope:** compileOnly (API) + runtimeOnly

- Declared as dependency but **not directly used** in mod source code.
- Present because SlashBlade Resharped or Umapyoi may depend on it transitively.

---

## 4. Patchouli

**Dependency scope:** compileOnly + runtimeOnly

- Declared as dependency but **not directly used** in mod source code.
- Likely a transitive dependency or planned future integration.

---

## 5. JEI

**Dependency scope:** compileOnly (API) + runtimeOnly

- Declared as dependency but **not directly used** in mod source code.
- Likely a transitive dependency or planned future integration.

---

## 6. MMLib (Mysterious Mountain Library)

**Dependency scope:** implementation

- Used by `BDLangProvider` which extends `AbstractLangProvider`
- Provides base language generation utilities for datagen

---

## 7. Player Animation Lib

**Dependency scope:** implementation

- Declared as dependency but **not directly used** in mod source code.
- Transitive dependency of SlashBlade Resharped (powers player animations for blade carry poses).

---

## 8. Publishing Platforms

### CurseForge
- Project ID: `1232462`
- Token via `CURSE_TOKEN` env var
- Required dependencies: `slashblade-resharped`, `umapyoi`
- Java versions: 17, 18, 20, 21

### Modrinth
- Project ID: `blades-derby`
- Token via `MODRINTH_TOKEN` env var
- Required dependencies: `slashblade-resharped`, `umapyoi`
- Loaders: forge, neoforge

---

## No External APIs

This mod has **no**:
- HTTP/HTTPS API calls
- Database connections
- Authentication providers
- Webhook integrations
- Network protocol custom packets
- File system access beyond Minecraft's standard resource loading
