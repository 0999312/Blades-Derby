# Vibe Coding 移植工作流

本文档记录本项目的 AI 辅助模组移植工作流，供后续项目或他人参考复用。

---

## 技术栈概览

| 组件 | 用途 |
|---|---|
| **DeepSeek V4 Pro** (`deepseek/deepseek-v4-pro`) | 唯一开发模型，负责代码生成、分析、研究 |
| **opencode** (`@opencode-ai/plugin`) | AI 编码 CLI 运行时，提供 agent/command/MCP 编排能力 |
| **GSD** (`gsd-build/get-shit-done`) | 项目规划与阶段管理框架，自动生成 phase plan、tracking、交接文档 |
| **mcmodding-mcp** (MCP: `loader-docs`) | NeoForge/Forge loader API 文档查询 |
| **minecraft-dev-mcp** (MCP: `mc-source`) | Minecraft 原版源码、映射、注册表查询 |

---

## 关键项目引用

### GSD: get-shit-done

- **仓库**: `https://github.com/gsd-build/get-shit-done`
- **作用**: 自动化项目规划与进度管理。`/gsd-init` 生成 `.planning/` 目录下的完整项目上下文（codebase map、requirements、roadmap、phase plans 等），`/gsd-execute-phase` 逐个阶段推进实施。
- **配置**: 见 `.planning/config.json`，核心设置：
  ```json
  {
    "mode": "yolo",
    "granularity": "standard",
    "parallelization": true,
    "commit_docs": true,
    "workflow": {
      "research": true,
      "plan_check": true,
      "verifier": true,
      "nyquist_validation": true
    }
  }
  ```
- **关键命令**:
  - `/gsd-init` — 初始化项目，扫描代码生成 codebase map、requirements、roadmap
  - `/gsd-execute-phase <N>` — 执行第 N 个 phase 的开发任务
  - `/gsd-transition` — 完成当前 phase 后过渡到下一 phase
  - `/gsd-handoff` — 上下文过载时生成交接信息与 Next Prompt

### opencode

- **官网**: `https://opencode.ai`
- **配置**: `opencode.json` 定义运行时设置、MCP 连接、agent 权限，`AGENTS.md` 定义行为规则
- **本项目定制 agent**:
  - `loader-diff-research` — 只读研究 Forge → NeoForge loader API 差异（可调用 `loader-docs_*`）
  - `vanilla-code-research` — 只读研究 Minecraft 原版源码、映射、签名（可调用 `mc-source_*`）
  - `port-review` — 只读复核迁移改动，检查残留的旧 API 和验证缺口
- **本项目 slash command**:
  - `/prereq` — 校验前置环境（Java 21+、Node.js 20+、MCP 连通性）
  - `/scan-port` — 盘点迁移范围，生成 phase 列表
  - `/phase <描述>` — 执行一个明确 phase
  - `/resolve-loader <API>` — 查询某个 Forge API 的 NeoForge 替代
  - `/handoff` — 整理交接信息并输出 Next Prompt

### MCP 工具

| MCP Server | npm 包 | 用途 |
|---|---|---|
| `loader-docs` | `mcmodding-mcp` | NeoForge/Forge loader API 文档（`search_fabric_docs`、`get_example`） |
| `mc-source` | `@mcdxai/minecraft-dev-mcp` | Minecraft 原版源码查询、映射查询、版本差异、第三方模组源码参考 |

---

## 工作流总览

```
┌────────────┐    ┌──────────────┐    ┌──────────────────┐
│ 1. 环境准备 │ → │ 2. 初始化规划 │ → │ 3. 逐 Phase 执行  │
│  /prereq   │    │  /gsd-init   │    │ /gsd-execute-ph  │
└────────────┘    └──────────────┘    └────────┬─────────┘
                                               │
                                    ┌──────────▼──────────┐
                                    │ 每 Phase 内部循环:   │
                                    │                     │
                                    │ a. 识别 loader API  │
                                    │ b. 调用 research 代理│
                                    │ c. 最小改动实现      │
                                    │ d. 编译验证          │
                                    │ e. 更新 API Map     │
                                    │ f. 更新 Progress    │
                                    │ g. port-review 复核  │
                                    └─────────────────────┘
                                               │
                                    ┌──────────▼──────────┐
                                    │ 4. 过渡与交接        │
                                    │  /gsd-transition   │
                                    │  /handoff          │
                                    └─────────────────────┘
```

### 阶段 1: 环境准备 (`/prereq`)

```powershell
# 启动 opencode 前设置环境变量（任选其一）
$env:MC_FORGE_PROJECT_PATH = "D:\mc-projects\my-mod-1.20.1"
$env:MC_FORGE_PROJECT_REPO   = "https://github.com/user/my-forge-mod"
```

`/prereq` 自动校验:
- Java 21+ / Node.js 20+ / opencode / Gradle wrapper 版本
- `loader-docs` 和 `mc-source` 两个 MCP 的连通性
- 将结果写入 `docs/migration/PREREQUISITES.md`

### 阶段 2: 初始化规划 (`/gsd-init` + `/scan-port`)

1. 运行 `/gsd-init`，GSD 会自动扫描项目代码，生成 `.planning/` 下的完整规划文件
2. 运行 `/scan-port`，盘点所有 Forge API 痕迹，生成 phase 列表和 `LOADER_API_MAP.md`

### 阶段 3: 逐 Phase 执行

每个 phase 执行时遵循以下规则：
- **不凭记忆判断 API**：遇到 `net.minecraftforge.*` / `net.neoforged.*` 等 loader API 差异，必须调用 `loader-diff-research` 查询
- **先查后改**：先在 `LOADER_API_MAP.md` 中检查是否已有已确认结论，没有则用 `/resolve-loader` 查询
- **最小改动**：优先修正编译错误和行为回归，不做额外重构
- **缓存结论**：每解决一个 API 差异，同步更新 `LOADER_API_MAP.md`

### 阶段 4: 过渡与交接

当上下文变紧或 phase 完成时：
1. 运行 `/gsd-transition` 更新规划状态
2. 运行 `/handoff` 生成交接信息
3. 输出 `Next Prompt` 供下次会话直接粘贴使用

---

## 与 SlashBlade 前置项目的对比

本项目的前置项目 **SlashBlade**（将 SlashBlade 模组从 1.20.1 Forge 移植到 1.21.1 NeoForge）使用了更早期的工作流进行移植实验。

| 维度 | SlashBlade（旧工作流） | Blades-Derby（本项目） |
|---|---|---|
| **规划管理** | 手动维护 MIGRATION_PLAN.md，无自动化工具 | **GSD 全自动**：自动生成 phase plan、需求追踪、进度管理、交接文档 |
| **代码复核** | 使用 **GPT 5.4** 对代码和进度进行独立复查 | 使用 **port-review 代理**（同 DeepSeek V4 Pro 模型），在 opencode 内部自动复核 |
| **API 差异处理** | **loader-diff-research + vanilla-code-research** 专用代理，结构化输出（旧API→新API→证据→验证方式） | 与 SlashBlade 使用**相同的处理方式** |
| **上下文管理** | 通过在 AGENTS.md 强制要求，模型在上下文紧张时自动输出 Next Prompt | **GSD 接手**：通过 `/handoff` 和 `/gsd-transition` 命令自动化交接，生成 Next Prompt |
| **模型** | **DeepSeek V4 Flash**（主）+ **GPT 5.4**（复核 / 复杂问题修复） | **DeepSeek V4 Pro** 统一所有任务 |

### 旧工作流的核心痛点

1. **规划成本高**：每次需要手动分析代码依赖关系、划分 phase、追踪执行进度，无 GSD 自动化支持
2. **复核滞后**：GPT 5.4 作为独立复核步骤，需要额外调用，结果与主开发上下文脱离
3. **上下文手动管理不可靠**：SlashBlade 虽通过 AGENTS.md 要求模型在上下文紧张时输出 Next Prompt，但模型自检机制不稳定——常出现未触发、触发过迟、或输出信息不全的情况，导致新会话仍需大量重复描述。本项目由 GSD 的 `/handoff` 与 `/gsd-transition` 接管上下文管理后，交接质量与一致性显著改善

---

## 启示

### 1. 完善 AI 工作流极大加速开发进度

本项目仅用 **3 个 Phase** 完成全部代码迁移（构建系统 → 注册与事件 → 数据生成与清理），11 个 Java 源文件、build.gradle、mod 元数据全部在 AI 工作流中自动化处理。`./gradlew compileJava` 在 Phase 1 完成即通过。

对比手动作业，工作流的关键加速点在于：
- **自动规划**（GSD 扫描代码 → 划分 phase → 识别每个文件的改动点）
- **并行研究**（loader-diff-research 和 vanilla-code-research 代理同时查询不同 API）
- **结构化复用**（LOADER_API_MAP.md 缓存 API 结论，相同 API 不再重复查询）
- **自动化交接**（/handoff 生成 Next Prompt，减少会话切换的上下文损失）

### 2. 足够清理部分技术债

在迁移过程中，AI 工作流还能顺便完成：
- **依赖版本统一升级**：从 ForgeGradle 6.x → NeoGradle 7.1.25，Gradle 8.8 → 8.14
- **过时 API 清理**：`fg.deobf()` 移除、`ResourceLocation` 构造器 → `fromNamespaceAndPath`
- **映射升级**：Mojang official → Parchment（可读参数名）
- **配置文件格式迁移**：`mods.toml` → `neoforge.mods.toml`
- **不再使用的依赖声明清理**（如 Curios 的 compileOnly → api）

这些改动如果在传统开发流程中需要开发者手动逐条确认，在 AI 工作流中通过研究代理自动完成。

---

## 配置方法

以下是在新项目中配置本工作流的步骤。

### 1. 安装前置依赖

- **Java 21+**: 必须
- **Node.js 20+**: `winget install OpenJS.NodeJS.LTS`
- **opencode**: 按 [opencode.ai](https://opencode.ai) 官方指引安装
- **Gradle**: 项目级别的 wrapper 即可

### 2. 初始化 opencode 项目

```powershell
# 在项目根目录初始化 opencode
opencode init
```

配置 `opencode.json`，添加 MCP 服务器：

```json
{
  "$schema": "https://opencode.ai/config.json",
  "shell": "pwsh",
  "default_agent": "build",
  "instructions": ["AGENTS.md"],
  "compaction": { "auto": true, "prune": true, "reserved": 18000 },
  "mcp": {
    "mc-source": {
      "type": "local",
      "command": ["npx", "-y", "@mcdxai/minecraft-dev-mcp"],
      "enabled": true,
      "environment": { "LOG_LEVEL": "INFO" },
      "timeout": 20000
    },
    "loader-docs": {
      "type": "local",
      "command": ["npx", "-y", "mcmodding-mcp"],
      "enabled": true,
      "timeout": 15000
    }
  },
  "permission": {
    "mc-source_*": "deny",
    "loader-docs_*": "deny"
  },
  "agent": {
    "build": {
      "mode": "primary",
      "description": "执行当前迁移步骤",
      "permission": {
        "task": {
          "*": "deny",
          "loader-diff-research": "allow",
          "vanilla-code-research": "allow",
          "port-review": "allow",
          "explore": "allow",
          "general": "allow"
        }
      }
    },
    "plan": {
      "mode": "primary",
      "description": "分析迁移影响、制定 phase，不直接改代码",
      "permission": {
        "edit": "deny",
        "bash": {
          "*": "deny",
          "git status*": "allow",
          "git diff*": "allow",
          "./gradlew*": "allow"
        },
        "task": {
          "*": "deny",
          "loader-diff-research": "allow",
          "vanilla-code-research": "allow",
          "explore": "allow",
          "general": "allow"
        },
        "webfetch": "allow"
      }
    }
  }
}
```

### 3. 安装 GSD

```powershell
# 在 .opencode/ 目录下安装（如果 opencode init 已生成）
cd .opencode
npm install gsd-build/get-shit-done
```

或者通过 opencode 插件方式安装：

```json
// .opencode/package.json
{
  "dependencies": {
    "@opencode-ai/plugin": "1.14.33",
    "gsd-build": "github:gsd-build/get-shit-done"
  }
}
```

### 4. 复制 Agent 和 Command 定义

将以下文件从本项目 `.opencode/` 复制到你的项目对应位置：

```
.opencode/
├── agents/
│   ├── loader-diff-research.md
│   ├── port-review.md
│   └── vanilla-code-research.md
└── commands/
    ├── handoff.md
    ├── phase.md
    ├── prereq.md
    ├── resolve-loader.md
    └── scan-port.md
```

### 5. 编写 AGENTS.md

参考本项目的 `AGENTS.md`，定义你的迁移规则：
- 目标版本（From → To）
- 前置确认流程
- Loader API 差异处理流程
- MCP 分工
- 阶段推进规则
- 上下文控制规则
- 验证方式

### 6. 执行迁移

按顺序执行：

```
/prereq          → 校验环境
/gsd-init        → GSD 初始化规划
/scan-port       → 盘点迁移范围
/gsd-execute-phase 1  → 执行 Phase 1
...              → 后续 phase
/gsd-transition  → Phase 间过渡
/handoff         → 上下文切换时交接
```

---

## 文件结构总览

```
project/
├── AGENTS.md                    # AI 行为规则（核心文档）
├── opencode.json               # opencode 运行时配置 + MCP 声明 + agent 权限
├── .opencode/
│   ├── package.json            # 插件依赖
│   ├── agents/                 # 自定义子代理定义
│   │   ├── loader-diff-research.md
│   │   ├── port-review.md
│   │   └── vanilla-code-research.md
│   └── commands/               # 自定义 slash command 定义
│       ├── handoff.md
│       ├── phase.md
│       ├── prereq.md
│       ├── resolve-loader.md
│       └── scan-port.md
├── .planning/                  # GSD 自动生成的规划文件
│   ├── config.json             # GSD 工作流配置
│   ├── PROJECT.md              # 项目定义与约束
│   ├── REQUIREMENTS.md         # 需求追踪
│   ├── ROADMAP.md              # 阶段路线图
│   ├── STATE.md                # 当前状态与进度
│   ├── codebase/               # 代码扫描结果
│   │   ├── ARCHITECTURE.md
│   │   ├── STACK.md
│   │   ├── STRUCTURE.md
│   │   ├── CONVENTIONS.md
│   │   ├── INTEGRATIONS.md
│   │   ├── TESTING.md
│   │   └── CONCERNS.md
│   └── phases/                 # 各 Phase 的执行计划
│       └── 01-build-and-entry/
│           ├── 01-CONTEXT.md
│           ├── 01-DISCUSSION-LOG.md
│           ├── 01-01-PLAN.md
│           ├── 01-01-SUMMARY.md
│           └── ...
└── docs/
    └── migration/              # 迁移文档
        ├── PREREQUISITES.md    # 前置校验结果
        ├── MIGRATION_PLAN.md   # 迁移阶段计划
        ├── PROGRESS.md         # 进度追踪
        ├── LOADER_API_MAP.md   # Forge→NeoForge API 映射缓存
        └── VIBE_CODING_WORKFLOW.md  # 本文档
```

---

*文档版本: 1.0*
*更新日期: 2026-05-04*
*适用模型: DeepSeek V4 Pro*
