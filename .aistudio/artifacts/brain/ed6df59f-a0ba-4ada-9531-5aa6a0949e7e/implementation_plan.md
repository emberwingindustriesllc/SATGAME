# Scholar's Journey: Retro GBA-Style SAT Monster Battler

Transform Scholar's Journey into an authentic Game Boy Advance / Pokemon-inspired educational RPG where players explore the Academia region, battle and capture wild "Scholarmon" creatures by solving SAT Math and Reading/Writing problems, level up their party, and master test-taking skills with dynamic retro pixel animations and audio feedback.

## User Review & Critical Decisions

> [!IMPORTANT]
> The visual direction and core game loop have been tailored to your preferences:
> - **Aesthetic**: Game Boy Advance / GBC Pokémon battle visual style with vibrant pixel-art sprites, classic dual-pane battle arena, and tactile retro dialogue UI.
> - **Game Loop**: Turn-based monster encounters where selecting moves prompts real SAT questions; correct answers power moves or secure captures, while incorrect answers give educational explanations and trigger enemy counter-attacks.
> - **Curriculum**: Balanced SAT coverage spanning Heart of Algebra, Advanced Math, Geometry, Words in Context, Sentence Boundaries/Punctuation, and Rhetorical Transitions.

- **Confirmed Decision 1**: Authentic GBA Pokémon battle layout with animated canvas pixel sprites (front wild monster, back player monster), health bars, EXP curves, and battle log dialogues.
- **Confirmed Decision 2**: Balanced mix of SAT Math and SAT Reading & Writing, featuring comprehensive step-by-step explanations for every question.
- **Confirmed Decision 3**: Offline-first Room persistence for caught Scholarmon, player levels, battle stats, and SAT domain mastery tracking.

---

## 1. Overview & Core Concept

- **What It Does**: Scholar's Journey puts students into the shoes of a Scholar Trainer in the Academia region. Players wander through knowledge habitats (e.g., *Verbal Vale*, *Algebra Archipelago*, *Syntactic Citadel*, *Geometric Gorge*), encounter wild Scholarmon creatures, and engage in classic turn-based battles. Answering SAT questions powers attacks, captures wild beasts into Scholar-Balls, and levels up the player's team.
- **Target Audience / Persona**: High school students prepping for the digital SAT who want an engaging, gamified alternative to boring flashcards and practice test PDFs, as well as retro gaming enthusiasts who appreciate nostalgic 90s/2000s handheld RPGs.
- **Key Value**: Replaces test prep burnout with genuine gameloop motivation (capturing rare monsters, leveling up, seeing satisfying critical hit animations and sound effects, and tracking tangible score improvement across SAT domains).

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Title & Start Screen**: Nostalgic start screen with pixel logo, "PRESS START" button, animated Scholarmon silhouette, and slot to load or begin a new Scholar's Journey.
2. **Overworld / Zone Navigation**: The Academia Region map where players travel between distinct biomes (Algebra Archipelago, Verbal Vale, Geometric Gorge, Syntax Sanctuary). Each biome has distinct wild encounter rates, difficulty tiers, and domain focus.
3. **Turn-Based Battle Screen (Core Loop)**:
   - Wild encounter intro: Screen wipe animation, "Wild Vocabula appeared!", entrance slide.
   - Dual-side battle arena: Player's companion back-sprite on the lower left platform, opponent front-sprite on the upper right platform.
   - Action Menu: `[FIGHT]`, `[SCHOLAR-BALL]`, `[BAG / ITEMS]`, `[RUN / NOTES]`.
   - **Fight Action**: Player chooses an attack move (e.g., *Linear Strike*, *Vocabulary Jab*, *Pythagorean Crush*, *Contextual Flare*). This opens the SAT Challenge Prompt in the lower battle box with 4 choices. Answering correctly executes the move with particle animations, screen shake, and damage floaters!
   - **Capture Action**: Throw a Scholar-Ball. The catch rate is influenced by enemy remaining HP and successfully answering a capture-clinch question. Features classic three-shake suspense animation and star-burst capture victory!
   - **Educational Feedback**: If an answer is missed, the opponent counters, and the game displays the step-by-step SAT solution breakdown and formula hint so the player actually learns from the mistake.
4. **Party & ScholarDex**: View all collected Scholarmon, their stats, types, levels, and lore descriptions.
5. **SAT Mastery & Study Log**: Interactive radar / breakdown cards showing player performance in Math (Algebra, Advanced Math, Geometry) and Reading/Writing (Vocabulary, Grammar, Logic), streak count, and predicted SAT score band (400-1600 scale).

### Visual Identity & Theme

- **Aesthetic Direction**: Authentic GBA (Game Boy Advance) Pokémon Emerald / FireRed era pixel art aesthetic. Chunky pixelated frames, vibrant 16-bit palettes, pixel borders (`#2B2B2B` outlines with `#F8F8F8` highlights), and authentic battle dialogue boxes.
- **Color Palette**:
  - *Console Frame / Shell*: Deep Indigo (`#261C52`) and Retro Slate (`#1A1A24`).
  - *Battle Grass Arena*: Lush Greens (`#58B868`, `#78D880`, `#388848`).
  - *UI Dialogue Window*: Crisp Cream Background (`#F8F9FA`) with Dark Indigo Text (`#1E1B2E`) and Retro Double-Border (`#585868`).
  - *HP & EXP Gauges*: High-HP Emerald (`#22C55E`), Mid-HP Amber (`#F59E0B`), Low-HP Crimson (`#EF4444`), EXP Blue (`#3B82F6`).
  - *Type Badges*: Math Orange (`#F97316`), Verbal Purple (`#8B5CF6`), Logic Cyan (`#06B6D4`), Geometry Lime (`#84CC16`).
- **Typography & Motion**:
  - Monospace / Retro pixel style typography styling for names, damage counters, and battle prompts.
  - Smooth Compose spring & keyframe animations for sprite idle breathing, tackle dash, damage shake, screen flash, HP bar drain, and Pokeball toss arc.
  - Audio synthesizer: Retro 8-bit sound blips, tackle impacts, victory jingles, and button clicks generated via custom tone synthesis.

---

## 3. Key Product Decisions & Trade-Offs

- **Custom Compose Canvas Pixel Art Engine vs Static Images**:
  - *Decision*: Build dedicated procedural & canvas-rendered pixel art generators for each Scholarmon species (e.g. *Derivafox*, *Vocabularva*, *Pythagoruff*, *Thesauron*, *Parabolon*, *Hypotenuse Cat*) complete with idle floating, blinking, tackle lunges, and status effects.
  - *Why*: Eliminates broken or blurry external image assets, delivers 100% reliable offline rendering, and enables fluid sprite animation states (idle, attack, hit, faint, capture).
- **Embedded Authentic Digital SAT Question Bank**:
  - *Decision*: Provide an extensive built-in question repository across Algebra, Advanced Math, Geometry, Vocabulary in Context, Punctuation/Boundaries, and Rhetoric, with detailed explanations.
  - *Why*: Provides immediate, engaging test prep right out of the box without requiring external API keys or network latency.
- **Room Database for Local Save State**:
  - *Decision*: Store party creatures, ScholarDex records, player level, battle history, and SAT skill mastery locally using Android Room.
  - *Why*: Guarantees instant load times, seamless offline play on buses or flights, and zero sign-in friction for high school students.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                         Scholar's Journey App                          │
├────────────────────────────────────────────────────────────────────────┤
│                                                                        │
│   ┌────────────────────┐   ┌───────────────────────────────────────┐   │
│   │   Navigation /     │   │         Audio & Sound FX Engine       │   │
│   │   Active Screen    │   │  (Retro Bleeps, Hits, Victory Tunes)  │   │
│   └─────────┬──────────┘   └───────────────────────────────────────┘   │
│             │                                                          │
│   ┌─────────▼──────────────────────────────────────────────────────┐   │
│   │                      ScholarViewModel                          │   │
│   │  - BattleState (Turn, Monster HP/EXP, Dialogue, Animations)    │   │
│   │  - PartyState (Active Team, Levels, Stats, Moves)              │   │
│   │  - QuestionState (Current SAT Question, Choices, Explanation)  │   │
│   │  - PlayerProgressState (Trainer Level, SAT Mastery, Badges)    │   │
│   └─────────┬──────────────────────────────────┬───────────────────┘   │
│             │                                  │                       │
│   ┌─────────▼──────────────┐         ┌─────────▼───────────────────┐   │
│   │    Room Database       │         │      SAT Question Bank      │   │
│   │ - MonsterEntity        │         │ - 40+ Curated SAT Questions │   │
│   │ - ProgressEntity       │         │ - Math & Verbal Domains     │   │
│   │ - MasteryStatEntity    │         │ - Detailed Explanations     │   │
│   └────────────────────────┘         └─────────────────────────────┘   │
│                                                                        │
│   ┌────────────────────────────────────────────────────────────────┐   │
│   │                       UI Presentation Layer                    │   │
│   │  ┌───────────────────────┐        ┌─────────────────────────┐  │   │
│   │  │   Retro Battle Screen │        │    Academia World Map   │  │   │
│   │  │ - Animated Canvas Arena        │  - Zone Select & Biomes │  │   │
│   │  │ - SAT Question Dialogue Box    │  - Encounter Rates      │  │   │
│   │  │ - Action Menu (Fight/Catch)    │  - Dean / Boss Trials   │  │   │
│   │  └───────────────────────┘        └─────────────────────────┘  │   │
│   │  ┌───────────────────────┐        ┌─────────────────────────┐  │   │
│   │  │   ScholarDex & Party  │        │   SAT Mastery Dashboard │  │   │
│   │  │ - Creature Lore & Stats        │  - Domain Accuracy %    │  │   │
│   │  │ - Team Roster / Moves │        │  - Score Band Estimate  │  │   │
│   │  └───────────────────────┘        └─────────────────────────┘  │   │
│   └────────────────────────────────────────────────────────────────┘   │
└────────────────────────────────────────────────────────────────────────┘
```

### Core Data Models

- **Scholarmon**: ID, Name, Species, Domain (`MATH` vs `VERBAL`), Level, CurrentHP, MaxHP, AttackPower, Defense, EXP, MoveSet, SpriteKey, Description.
- **SATQuestion**: ID, Domain, Subtopic (e.g., "Linear Equations", "Words in Context", "Punctuation"), PromptText, CodeSnippetOrFormula, Options (A, B, C, D), CorrectOptionIndex, Explanation, Difficulty.
- **PlayerProfile**: TrainerName, Level, TotalBattles, CreaturesCaught, EstimatedSatScore, DomainAccuracyMap.

### Interactive State Transitions

1. **Battle Start**: Zone selection triggers random wild encounter $\rightarrow$ Wild monster intro animates $\rightarrow$ Player sends out party lead.
2. **Move Selected**: Battle menu transitions to SAT question dialog $\rightarrow$ Timer & options presented $\rightarrow$ User selects option.
3. **Question Evaluation**:
   - *Correct*: Critical hit sound FX $\rightarrow$ Attack animation plays $\rightarrow$ Target HP drains $\rightarrow$ If opponent HP reaches 0: Victory fanfare, EXP granted, Level up check.
   - *Incorrect*: Miss sound FX $\rightarrow$ Explanatory lesson modal shown $\rightarrow$ Enemy retaliates with counterattack $\rightarrow$ Player HP drains.
4. **Catch Attempt**: Scholar-Ball thrown $\rightarrow$ Arc animation $\rightarrow$ 3 wobble suspense sequence $\rightarrow$ Click! Monster added to Party/ScholarDex.
