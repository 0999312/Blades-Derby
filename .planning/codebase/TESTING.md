---
last_mapped_commit: unknown
---

# TESTING.md — Testing Practices

> Last updated: 2026-05-04

## Test Framework

**None.** This project has no test framework configured and no test files.

## Test Files

- Searched patterns: `**/*test*/**/*.java`, `**/*Test*.java`
- **Result: 0 test files found**

## Test Dependencies

No test-scoped dependencies declared in `build.gradle`. The project does not include:
- JUnit
- Mockito
- GameTest framework (despite having a `gameTestServer` run config)
- Any other test library

## GameTest Configuration

The project has a `gameTestServer` run configuration in `build.gradle:98-100` that sets `forge.enabledGameTestNamespaces` to `blades_derby`, but **no game tests are implemented**. The comment in the build file notes: "By default, the server will crash when no gametests are provided."

## CI/CD

**No CI/CD configuration found.** Searched for:
- `.github/workflows/` — not present
- `.gitlab-ci.yml` — not present
- `Jenkinsfile` — not present
- Any `.yml` or `.yaml` files — none found

## Build Verification

The only build verification is:
- `./gradlew compileJava` — Java compilation
- `./gradlew runData` — data generation (validates datagen code compiles and generates)
- `./gradlew runClient` — manual client testing
- `./gradlew runServer` — manual server testing

## Test Coverage

**0%** — No tests of any kind exist.

## Mocking

Not applicable — no test framework in use.

## What Should Be Tested

Given the mod's functionality, the following should ideally have test coverage:

| Component | Risk | Test Type |
|---|---|---|
| `UmaSpecial` event handlers | High — core gameplay logic | GameTest / unit test |
| `UmaSkillEvents` attribute handling | Medium — skill effect correctness | GameTest / unit test |
| `BladesDerbyConfig` config loading | Low — config system | Unit test |
| `SpecialEffectRegistry` registration | Low — registry wiring | Integration test |
| `UmaSkillRegistry` registration | Low — registry wiring | Integration test |
| `SlashBladeRecipeProvider` recipe generation | Medium — recipe correctness | Datagen validation |
| `BuiltInSlashBladeRegistry` blade definitions | Medium — data integrity | Datagen validation |
| `BuiltInSupportCardRegistry` card definitions | Low — data integrity | Datagen validation |

## Summary

| Metric | Value |
|---|---|
| Test framework | None |
| Test files | 0 |
| Test dependencies | 0 |
| CI/CD pipeline | None |
| Code coverage | 0% |
| GameTests | 0 (config present, no tests) |
