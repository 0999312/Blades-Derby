# PREREQUISITES

## 环境依赖清单

| 依赖项 | 最低版本 | 检查命令 | 状态 |
|---|---|---|---|
| Java | 21 | `java -version` | ❌ BLOCKED |
| Node.js | 20 | `node -v` | ✅ OK |
| opencode | 最新 | `opencode --version` | ✅ OK (运行时内) |
| Gradle | 项目内置 wrapper | `./gradlew --version` | ✅ OK |
| MCP: mc-source | 自动拉取 | `/prereq` 内自动校验 | ✅ OK |
| MCP: loader-docs | 自动拉取 | `/prereq` 内自动校验 | ✅ OK |

## 项目源代码

| 属性 | 值 |
|---|---|
| 来源类型 | local |
| 来源值 | `D:\minecraft_121_modding\Blades-Derby` |
| 编译状态 (1.20.1 Forge) | ✅ BUILD SUCCESSFUL |

## 首次确认结果

| 检查项 | 结果 | 备注 |
|---|---|---|
| Java 21+ 可用 | ❌ FAIL | 当前: `OpenJDK 17.0.11` (Eclipse Adoptium `jdk-17.0.11.9-hotspot`)，无 Java 21+ 安装 |
| Node.js 20+ 可用 | ✅ OK | `v24.15.0` |
| opencode 可用 | ✅ OK | 当前在 opencode 运行时内执行 |
| Gradle wrapper 可用 | ✅ OK | `Gradle 8.8` (JVM 17) |
| 项目源代码可访问 | ✅ OK | 本地路径，1.20.1 Forge 编译通过 |
| mc-source MCP 可达 | ✅ OK | `mc-source_list_minecraft_versions` 调用成功 |
| loader-docs MCP 可达 | ✅ OK | `loader-docs_search_fabric_docs` 返回 NeoForge 文档结果 |

## 阻塞记录

| 阻塞项 | 影响 | 解决方式 |
|---|---|---|
| Java 17 < 21 | NeoForge 1.21.1 必须 Java 21+，无法编译运行 | 安装 JDK 21+（推荐 Eclipse Adoptium Temurin 21），设置 `JAVA_HOME` 指向新 JDK |

## 已知 1.21.1 NeoForge 依赖

| 依赖 | 坐标 | Maven 仓库 |
|---|---|---|
| SlashBlade Resharped | `mods.flammpfeil.slashblade:SlashBlade_Resharped:2.0.0-1.21.1` | `https://api.modrinth.com/maven` |
| MMLib | `maven.modrinth:mmlib:1.2.15-1.21.1` | `https://api.modrinth.com/maven` |
| Umapyoi | `maven.modrinth:umapyoi:1.4.11-1.21.1` | `https://api.modrinth.com/maven` |
| 0999312 Maven | 自定义 | `https://raw.github.com/0999312/MMMaven/main/repository` |
