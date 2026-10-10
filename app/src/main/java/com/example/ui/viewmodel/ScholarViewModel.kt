package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.RetroAudioEngine
import com.example.data.db.PlayerProgressEntity
import com.example.data.db.ScholarDatabase
import com.example.data.model.*
import com.example.data.repository.SATQuestionBank
import com.example.data.repository.ScholarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppScreen {
    BATTLE,
    OVERWORLD,
    PARTY,
    MASTERY
}

enum class BattlePhase {
    INTRO,
    PLAYER_MENU,
    SELECTING_MOVE,
    QUESTION_CHALLENGE,
    ANIMATING_PLAYER_ATTACK,
    SHOWING_EXPLANATION,
    ANIMATING_ENEMY_ATTACK,
    ATTEMPTING_CAPTURE,
    VICTORY,
    DEFEAT,
    CAUGHT
}

enum class BattleMenuChoice {
    FIGHT,
    BALL,
    BAG,
    RUN
}

data class BattleUiState(
    val phase: BattlePhase = BattlePhase.INTRO,
    val zone: ZoneLocation = AcademiaMap.ZONES[0],
    val wildMonster: Scholarmon = ScholarmonSpecies.ALGERAY,
    val playerMonster: Scholarmon = ScholarmonSpecies.ALGERAY,
    val dialogueText: String = "",
    val activeQuestion: SATQuestion? = null,
    val selectedMove: Move? = null,
    val selectedAnswerIndex: Int? = null,
    val isAnswerCorrect: Boolean? = null,
    val captureWobbleCount: Int = 0,
    val isCaptured: Boolean = false,
    val screenShake: Boolean = false,
    val floatingDamage: String? = null,
    val promptContinue: Boolean = false,
    val currentMenuIndex: Int = 0, // 0: FIGHT, 1: BALL, 2: BAG, 3: RUN
    val showOpponentStatsDialog: Boolean = false
)

class ScholarViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ScholarDatabase.getInstance(application)
    private val repository = ScholarRepository(database.scholarDao())

    val partyCreatures: StateFlow<List<Scholarmon>> = repository.partyCreatures
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCreatures: StateFlow<List<Scholarmon>> = repository.allCreatures
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val playerProgress: StateFlow<PlayerProgressEntity> = repository.playerProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayerProgressEntity())

    private val _currentScreen = MutableStateFlow(AppScreen.BATTLE)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _battleState = MutableStateFlow(BattleUiState())
    val battleState: StateFlow<BattleUiState> = _battleState.asStateFlow()

    private val _isHandheldMode = MutableStateFlow(false)
    val isHandheldMode: StateFlow<Boolean> = _isHandheldMode.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureInitialData()
            // Wait briefly for repository seed then start initial encounter
            delay(200)
            val currentParty = partyCreatures.value.ifEmpty {
                listOf(ScholarmonSpecies.ALGERAY)
            }
            startEncounter(AcademiaMap.ZONES[0], currentParty.first())
        }
    }

    fun navigateTo(screen: AppScreen) {
        RetroAudioEngine.playSelect()
        _currentScreen.value = screen
    }

    fun toggleHandheldMode() {
        _isHandheldMode.value = !_isHandheldMode.value
    }

    fun startEncounter(zone: ZoneLocation, leadMonster: Scholarmon? = null) {
        viewModelScope.launch {
            val candidateSpeciesIds = zone.wildSpeciesIds
            val randomSpeciesId = candidateSpeciesIds.randomOrNull() ?: "algeray"
            val baseSpecies = ScholarmonSpecies.allSpecies.firstOrNull { it.speciesId == randomSpeciesId }
                ?: ScholarmonSpecies.ALGERAY

            // Scale wild level
            val wildLevel = (baseSpecies.level + Random.nextInt(-1, 2)).coerceAtLeast(3)
            val wildHp = baseSpecies.maxHp + (wildLevel - 5) * 4
            val wildAtk = baseSpecies.attack + (wildLevel - 5) * 2
            val wildDef = baseSpecies.defense + (wildLevel - 5) * 2

            val wild = baseSpecies.copy(
                id = "wild_" + System.currentTimeMillis(),
                level = wildLevel,
                currentHp = wildHp,
                maxHp = wildHp,
                attack = wildAtk,
                defense = wildDef,
                isWild = true
            )

            val activeLead = leadMonster
                ?: partyCreatures.value.firstOrNull { !it.isFainted }
                ?: partyCreatures.value.firstOrNull()
                ?: ScholarmonSpecies.ALGERAY

            _battleState.value = BattleUiState(
                phase = BattlePhase.INTRO,
                zone = zone,
                wildMonster = wild,
                playerMonster = activeLead,
                dialogueText = "Wild ${wild.name} appeared in ${zone.name}!",
                promptContinue = true
            )

            RetroAudioEngine.playEncounterStart()
            _currentScreen.value = AppScreen.BATTLE
        }
    }

    fun advanceDialogueOrIntro() {
        val current = _battleState.value
        when (current.phase) {
            BattlePhase.INTRO -> {
                RetroAudioEngine.playConfirm()
                _battleState.value = current.copy(
                    phase = BattlePhase.PLAYER_MENU,
                    dialogueText = "What will ${current.playerMonster.nickname} do?",
                    promptContinue = false
                )
            }
            BattlePhase.VICTORY, BattlePhase.CAUGHT, BattlePhase.DEFEAT -> {
                _currentScreen.value = AppScreen.OVERWORLD
            }
            else -> {}
        }
    }

    fun selectMenuChoice(choice: BattleMenuChoice) {
        val current = _battleState.value
        when (choice) {
            BattleMenuChoice.FIGHT -> {
                _battleState.value = current.copy(
                    phase = BattlePhase.SELECTING_MOVE,
                    dialogueText = "Choose an SAT academic move:"
                )
            }
            BattleMenuChoice.BALL -> {
                attemptCapture()
            }
            BattleMenuChoice.BAG -> {
                usePotion()
            }
            BattleMenuChoice.RUN -> {
                RetroAudioEngine.playMiss()
                _battleState.value = current.copy(
                    dialogueText = "Got away safely!",
                    phase = BattlePhase.PLAYER_MENU
                )
                viewModelScope.launch {
                    delay(800)
                    _currentScreen.value = AppScreen.OVERWORLD
                }
            }
        }
    }

    fun cancelMoveSelection() {
        _battleState.value = _battleState.value.copy(
            phase = BattlePhase.PLAYER_MENU,
            dialogueText = "What will ${_battleState.value.playerMonster.nickname} do?"
        )
    }

    fun selectMove(move: Move) {
        val q = SATQuestionBank.getRandomQuestion(domain = move.domain)
        _battleState.value = _battleState.value.copy(
            phase = BattlePhase.QUESTION_CHALLENGE,
            selectedMove = move,
            activeQuestion = q,
            selectedAnswerIndex = null,
            isAnswerCorrect = null,
            dialogueText = "[${move.name}] SAT Challenge: Solve to unleash maximum power!"
        )
    }

    fun submitAnswer(answerIndex: Int) {
        val current = _battleState.value
        val q = current.activeQuestion ?: return
        if (current.selectedAnswerIndex != null) return // Already submitted

        val isCorrect = (answerIndex == q.correctIndex)
        _battleState.value = current.copy(
            selectedAnswerIndex = answerIndex,
            isAnswerCorrect = isCorrect
        )

        viewModelScope.launch {
            // Update stats
            val currentProg = playerProgress.value
            val newQuestionsAns = currentProg.questionsAnswered + 1
            val newQuestionsCorr = currentProg.questionsCorrect + (if (isCorrect) 1 else 0)
            val newMathTot = currentProg.mathTotal + (if (q.domain == SatDomain.MATH) 1 else 0)
            val newMathCorr = currentProg.mathCorrect + (if (q.domain == SatDomain.MATH && isCorrect) 1 else 0)
            val newVerbTot = currentProg.verbalTotal + (if (q.domain == SatDomain.VERBAL) 1 else 0)
            val newVerbCorr = currentProg.verbalCorrect + (if (q.domain == SatDomain.VERBAL && isCorrect) 1 else 0)
            val newStreak = if (isCorrect) currentProg.currentStreak + 1 else 0
            val newBestStreak = maxOf(currentProg.bestStreak, newStreak)

            repository.updateProgress(
                currentProg.copy(
                    questionsAnswered = newQuestionsAns,
                    questionsCorrect = newQuestionsCorr,
                    mathTotal = newMathTot,
                    mathCorrect = newMathCorr,
                    verbalTotal = newVerbTot,
                    verbalCorrect = newVerbCorr,
                    currentStreak = newStreak,
                    bestStreak = newBestStreak
                )
            )

            delay(600)

            if (isCorrect) {
                // Correct answer executes critical attack
                RetroAudioEngine.playCriticalHit()
                val move = current.selectedMove
                val damage = ((move?.power ?: 20) * 1.3f).toInt()
                val updatedWildHp = (current.wildMonster.currentHp - damage).coerceAtLeast(0)
                val isWildFainted = (updatedWildHp <= 0)

                _battleState.value = _battleState.value.copy(
                    phase = BattlePhase.ANIMATING_PLAYER_ATTACK,
                    screenShake = true,
                    floatingDamage = "-$damage CRIT!",
                    wildMonster = current.wildMonster.copy(currentHp = updatedWildHp),
                    dialogueText = "Correct answer! ${current.playerMonster.nickname} used ${move?.name}! It's super effective! (-$damage HP)"
                )

                delay(900)
                _battleState.value = _battleState.value.copy(screenShake = false, floatingDamage = null)

                if (isWildFainted) {
                    handleVictory()
                } else {
                    delay(800)
                    _battleState.value = _battleState.value.copy(
                        phase = BattlePhase.PLAYER_MENU,
                        dialogueText = "What will ${current.playerMonster.nickname} do next?"
                    )
                }
            } else {
                // Incorrect answer: show explanation, then wild monster retaliates
                RetroAudioEngine.playMiss()
                _battleState.value = _battleState.value.copy(
                    phase = BattlePhase.SHOWING_EXPLANATION,
                    dialogueText = "Incorrect! Review the SAT explanation to learn."
                )
            }
        }
    }

    fun dismissExplanationAndContinue() {
        val current = _battleState.value
        viewModelScope.launch {
            // Wild enemy counter-attack
            _battleState.value = current.copy(
                phase = BattlePhase.ANIMATING_ENEMY_ATTACK,
                dialogueText = "Wild ${current.wildMonster.name} retaliates with Tackle!"
            )
            delay(500)
            RetroAudioEngine.playTackle()

            val enemyDamage = Random.nextInt(8, 16)
            val newPlayerHp = (current.playerMonster.currentHp - enemyDamage).coerceAtLeast(0)
            val updatedPlayer = current.playerMonster.copy(currentHp = newPlayerHp)

            _battleState.value = _battleState.value.copy(
                screenShake = true,
                floatingDamage = "-$enemyDamage",
                playerMonster = updatedPlayer,
                dialogueText = "${current.playerMonster.nickname} took $enemyDamage damage!"
            )
            repository.updateScholarmonHpAndExp(
                updatedPlayer.id,
                newPlayerHp,
                updatedPlayer.exp,
                updatedPlayer.level
            )

            delay(800)
            _battleState.value = _battleState.value.copy(screenShake = false, floatingDamage = null)

            if (newPlayerHp <= 0) {
                RetroAudioEngine.playFaint()
                _battleState.value = _battleState.value.copy(
                    phase = BattlePhase.DEFEAT,
                    dialogueText = "${current.playerMonster.nickname} fainted! You must study and recover at the Library.",
                    promptContinue = true
                )
            } else {
                _battleState.value = _battleState.value.copy(
                    phase = BattlePhase.PLAYER_MENU,
                    dialogueText = "What will ${current.playerMonster.nickname} do?"
                )
            }
        }
    }

    private fun handleVictory() {
        viewModelScope.launch {
            val wild = _battleState.value.wildMonster
            val currentPl = _battleState.value.playerMonster
            val currentProg = playerProgress.value

            // 1. Announce defeat and auto-throw ball
            _battleState.value = _battleState.value.copy(
                phase = BattlePhase.ATTEMPTING_CAPTURE,
                dialogueText = "Wild ${wild.name} was defeated! Auto-launching Scholar-Ball!",
                captureWobbleCount = 0
            )
            RetroAudioEngine.playBallThrow()
            delay(550)

            // Wobble 1
            RetroAudioEngine.playBallWobble()
            _battleState.value = _battleState.value.copy(captureWobbleCount = 1)
            delay(400)

            // Wobble 2
            RetroAudioEngine.playBallWobble()
            _battleState.value = _battleState.value.copy(captureWobbleCount = 2)
            delay(400)

            // Wobble 3 & Click!
            RetroAudioEngine.playBallWobble()
            _battleState.value = _battleState.value.copy(captureWobbleCount = 3)
            delay(400)

            // Capture burst & fanfare
            RetroAudioEngine.playCaptureSuccess()
            RetroAudioEngine.playMegaChargeBlast()

            // Save captured creature to Room DB!
            repository.saveCapturedMonster(wild)

            // EXP & Level calculations
            val expGain = 50 + wild.level * 10
            var newExp = currentPl.exp + expGain
            var newLevel = currentPl.level
            var maxExp = currentPl.maxExp
            var leveledUp = false

            if (newExp >= maxExp) {
                newLevel += 1
                newExp -= maxExp
                leveledUp = true
            }

            val updatedPl = currentPl.copy(
                exp = newExp,
                level = newLevel,
                maxHp = if (leveledUp) currentPl.maxHp + 4 else currentPl.maxHp,
                attack = if (leveledUp) currentPl.attack + 2 else currentPl.attack
            )

            repository.updateScholarmonHpAndExp(updatedPl.id, updatedPl.currentHp, newExp, newLevel)
            repository.updateProgress(
                currentProg.copy(
                    battlesWon = currentProg.battlesWon + 1,
                    totalBattles = currentProg.totalBattles + 1,
                    creaturesCaught = currentProg.creaturesCaught + 1
                )
            )

            if (leveledUp) {
                delay(300)
                RetroAudioEngine.playLevelUp()
            }

            _battleState.value = _battleState.value.copy(
                phase = BattlePhase.CAUGHT,
                isCaptured = true,
                playerMonster = updatedPl,
                dialogueText = "★ VICTORY & CAPTURED! ★\n${wild.name} was caught and added to your Party & ScholarDex! (+$expGain EXP)" +
                        (if (leveledUp) " ${updatedPl.nickname} grew to Level $newLevel!" else ""),
                promptContinue = true
            )
        }
    }

    fun toggleOpponentStats(show: Boolean) {
        RetroAudioEngine.playSelect()
        _battleState.value = _battleState.value.copy(showOpponentStatsDialog = show)
    }

    fun attemptCapture() {
        val current = _battleState.value
        val prog = playerProgress.value

        if (prog.scholarBalls <= 0) {
            _battleState.value = current.copy(
                dialogueText = "You have no Scholar-Balls left! Win battles to earn more."
            )
            return
        }

        viewModelScope.launch {
            // Deduct ball
            repository.updateProgress(prog.copy(scholarBalls = prog.scholarBalls - 1))

            _battleState.value = current.copy(
                phase = BattlePhase.ATTEMPTING_CAPTURE,
                dialogueText = "Threw a Scholar-Ball at ${current.wildMonster.name}!",
                captureWobbleCount = 0
            )

            RetroAudioEngine.playBallThrow()
            delay(700)

            // Catch rate formula: weaker HP = much higher catch rate
            val hpFraction = current.wildMonster.hpFraction
            val catchThreshold = when {
                hpFraction < 0.25f -> 0.90f
                hpFraction < 0.50f -> 0.65f
                else -> 0.35f
            }

            val willCatch = (Random.nextFloat() <= catchThreshold)

            // Wobble 1
            RetroAudioEngine.playBallWobble()
            _battleState.value = _battleState.value.copy(captureWobbleCount = 1)
            delay(500)

            // Wobble 2
            RetroAudioEngine.playBallWobble()
            _battleState.value = _battleState.value.copy(captureWobbleCount = 2)
            delay(500)

            if (willCatch) {
                // Wobble 3 & Click!
                RetroAudioEngine.playBallWobble()
                _battleState.value = _battleState.value.copy(captureWobbleCount = 3)
                delay(500)

                RetroAudioEngine.playCaptureSuccess()
                _battleState.value = _battleState.value.copy(
                    isCaptured = true,
                    phase = BattlePhase.CAUGHT,
                    dialogueText = "Gotcha! ${current.wildMonster.name} was caught!",
                    promptContinue = true
                )

                repository.saveCapturedMonster(current.wildMonster)
                repository.updateProgress(
                    playerProgress.value.copy(
                        creaturesCaught = playerProgress.value.creaturesCaught + 1
                    )
                )
            } else {
                // Breakout!
                RetroAudioEngine.playMiss()
                _battleState.value = _battleState.value.copy(
                    dialogueText = "Oh no! ${current.wildMonster.name} broke free!",
                    phase = BattlePhase.PLAYER_MENU
                )
            }
        }
    }

    fun usePotion() {
        val current = _battleState.value
        val prog = playerProgress.value

        if (prog.potions <= 0) {
            _battleState.value = current.copy(
                dialogueText = "No Knowledge Potions left in your bag!"
            )
            return
        }

        viewModelScope.launch {
            repository.updateProgress(prog.copy(potions = prog.potions - 1))
            val healAmount = 25
            val newHp = (current.playerMonster.currentHp + healAmount).coerceAtMost(current.playerMonster.maxHp)
            val updated = current.playerMonster.copy(currentHp = newHp)

            RetroAudioEngine.playConfirm()
            _battleState.value = current.copy(
                playerMonster = updated,
                floatingDamage = "+$healAmount HP",
                dialogueText = "Used Knowledge Potion! Restored $healAmount HP to ${updated.nickname}."
            )
            repository.updateScholarmonHpAndExp(updated.id, newHp, updated.exp, updated.level)

            delay(1000)
            _battleState.value = _battleState.value.copy(floatingDamage = null)
        }
    }

    fun handleDpad(direction: String) {
        val current = _battleState.value
        if (current.phase == BattlePhase.PLAYER_MENU) {
            val nextIndex = when (direction) {
                "LEFT" -> if (current.currentMenuIndex in listOf(1, 3)) current.currentMenuIndex - 1 else current.currentMenuIndex
                "RIGHT" -> if (current.currentMenuIndex in listOf(0, 2)) current.currentMenuIndex + 1 else current.currentMenuIndex
                "UP" -> if (current.currentMenuIndex in listOf(2, 3)) current.currentMenuIndex - 2 else current.currentMenuIndex
                "DOWN" -> if (current.currentMenuIndex in listOf(0, 1)) current.currentMenuIndex + 2 else current.currentMenuIndex
                else -> current.currentMenuIndex
            }
            _battleState.value = current.copy(currentMenuIndex = nextIndex)
        }
    }

    fun handleButtonA() {
        val current = _battleState.value
        when (current.phase) {
            BattlePhase.INTRO, BattlePhase.VICTORY, BattlePhase.CAUGHT, BattlePhase.DEFEAT -> {
                advanceDialogueOrIntro()
            }
            BattlePhase.PLAYER_MENU -> {
                val choice = when (current.currentMenuIndex) {
                    0 -> BattleMenuChoice.FIGHT
                    1 -> BattleMenuChoice.BALL
                    2 -> BattleMenuChoice.BAG
                    else -> BattleMenuChoice.RUN
                }
                selectMenuChoice(choice)
            }
            BattlePhase.SHOWING_EXPLANATION -> {
                dismissExplanationAndContinue()
            }
            else -> {}
        }
    }

    fun handleButtonB() {
        val current = _battleState.value
        if (current.phase == BattlePhase.SELECTING_MOVE) {
            cancelMoveSelection()
        }
    }
}
