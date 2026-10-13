package com.emberwingindustriesllc.satgame.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emberwingindustriesllc.satgame.audio.RetroAudioEngine
import com.emberwingindustriesllc.satgame.data.model.*
import com.emberwingindustriesllc.satgame.ui.components.*
import com.emberwingindustriesllc.satgame.ui.theme.*
import com.emberwingindustriesllc.satgame.ui.viewmodel.BattleMenuChoice
import com.emberwingindustriesllc.satgame.ui.viewmodel.BattlePhase
import com.emberwingindustriesllc.satgame.ui.viewmodel.BattleUiState
import com.emberwingindustriesllc.satgame.ui.viewmodel.ScholarViewModel

@Composable
fun BattleScreen(
    viewModel: ScholarViewModel,
    modifier: Modifier = Modifier
) {
    val battleState by viewModel.battleState.collectAsState()
    val playerProgress by viewModel.playerProgress.collectAsState()

    // Screen Shake effect on impact
    val shakeTransition = rememberInfiniteTransition(label = "screen_shake")
    val shakeOffset by shakeTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(40, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake_offset"
    )

    val currentShake = if (battleState.screenShake) shakeOffset.dp else 0.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .offset(x = currentShake, y = currentShake)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1), Color(0xFF94A3B8))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Zone Header with Opponent Stats Quick Inspector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1B2E).copy(alpha = 0.90f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = battleState.zone.name.uppercase(),
                        style = Typography.labelSmall.copy(
                            fontFamily = PixelFontFamily,
                            color = Color(0xFFFDE047),
                            fontSize = 8.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Inspect Opponent Stats Button
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF312E81), RoundedCornerShape(4.dp))
                            .border(1.dp, Color(0xFF818CF8), RoundedCornerShape(4.dp))
                            .clickable { viewModel.toggleOpponentStats(true) }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🔍 STATS",
                            style = Typography.labelSmall.copy(
                                fontFamily = PixelFontFamily,
                                color = Color(0xFFFDE047),
                                fontSize = 7.sp
                            )
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "BALLS: ${playerProgress.scholarBalls}",
                        style = Typography.labelSmall.copy(
                            fontFamily = PixelFontFamily,
                            color = Color.White,
                            fontSize = 8.sp
                        )
                    )
                    Text(
                        text = "POTIONS: ${playerProgress.potions}",
                        style = Typography.labelSmall.copy(
                            fontFamily = PixelFontFamily,
                            color = Color(0xFF86EFAC),
                            fontSize = 8.sp
                        )
                    )
                }
            }

            // === BATTLE ARENA (Dual Platforms & Sprites) ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Background Arena Scenery Gradients
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFBBE5ED), Color(0xFFC7EFD4), Color(0xFF65A30D))
                            )
                        )
                )

                // OPPONENT (Top Right)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                ) {
                    MonsterBattleStatusCard(
                        name = battleState.wildMonster.name,
                        level = battleState.wildMonster.level,
                        currentHp = battleState.wildMonster.currentHp,
                        maxHp = battleState.wildMonster.maxHp,
                        type = battleState.wildMonster.type,
                        modifier = Modifier
                            .width(170.dp)
                            .clickable { viewModel.toggleOpponentStats(true) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.size(140.dp, 100.dp)
                    ) {
                        BattlePlatform(
                            color = Color(0xFF86EFAC),
                            shadowColor = Color(0xFF15803D),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        )

                        if (battleState.phase == BattlePhase.ATTEMPTING_CAPTURE || battleState.isCaptured) {
                            AnimatedScholarBall(
                                wobbleCount = battleState.captureWobbleCount,
                                isCaptured = battleState.isCaptured,
                                modifier = Modifier
                                    .size(46.dp)
                                    .offset(y = (-14).dp)
                            )
                        } else {
                            val animState = when (battleState.phase) {
                                BattlePhase.ANIMATING_ENEMY_ATTACK -> SpriteAnimationState.ATTACKING
                                BattlePhase.ANIMATING_PLAYER_ATTACK -> SpriteAnimationState.TAKING_DAMAGE
                                BattlePhase.DEFEAT, BattlePhase.VICTORY -> if (battleState.wildMonster.isFainted) SpriteAnimationState.FAINTED else SpriteAnimationState.IDLE
                                else -> SpriteAnimationState.IDLE
                            }

                            MonsterPixelSprite(
                                speciesId = battleState.wildMonster.speciesId,
                                isBackSprite = false,
                                animationState = animState,
                                modifier = Modifier
                                    .size(90.dp)
                                    .offset(y = (-10).dp)
                            )
                        }
                    }
                }

                // PLAYER (Bottom Left)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 12.dp, start = 16.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.size(140.dp, 110.dp)
                    ) {
                        BattlePlatform(
                            color = Color(0xFFBBF7D0),
                            shadowColor = Color(0xFF166534),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )

                        val playerAnimState = when (battleState.phase) {
                            BattlePhase.ANIMATING_PLAYER_ATTACK -> SpriteAnimationState.ATTACKING
                            BattlePhase.ANIMATING_ENEMY_ATTACK -> SpriteAnimationState.TAKING_DAMAGE
                            BattlePhase.DEFEAT -> SpriteAnimationState.FAINTED
                            else -> SpriteAnimationState.IDLE
                        }

                        MonsterPixelSprite(
                            speciesId = battleState.playerMonster.speciesId,
                            isBackSprite = true,
                            animationState = playerAnimState,
                            modifier = Modifier
                                .size(96.dp)
                                .offset(y = (-10).dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    MonsterBattleStatusCard(
                        name = battleState.playerMonster.nickname,
                        level = battleState.playerMonster.level,
                        currentHp = battleState.playerMonster.currentHp,
                        maxHp = battleState.playerMonster.maxHp,
                        type = battleState.playerMonster.type,
                        currentExp = battleState.playerMonster.exp,
                        maxExp = battleState.playerMonster.maxExp,
                        modifier = Modifier.width(180.dp)
                    )
                }

                // Floating Damage / Crit Text
                if (battleState.floatingDamage != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .shadow(6.dp, RoundedCornerShape(8.dp))
                            .background(Color(0xFFEF4444), RoundedCornerShape(8.dp))
                            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = battleState.floatingDamage ?: "",
                            style = Typography.titleMedium.copy(
                                color = Color.White,
                                fontFamily = PixelFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // === LOWER DIALOGUE & INTERACTIVE COMBAT CONSOLE ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 210.dp, max = 300.dp)
                    .background(Color(0xFF1E1B2E))
                    .padding(8.dp)
            ) {
                when (battleState.phase) {
                    BattlePhase.CAUGHT -> {
                        // Clear, prominent victory and automatic capture celebratory card!
                        CapturedCelebrationCard(
                            wildMonster = battleState.wildMonster,
                            playerMonster = battleState.playerMonster,
                            onContinue = { viewModel.advanceDialogueOrIntro() }
                        )
                    }

                    BattlePhase.INTRO, BattlePhase.VICTORY, BattlePhase.DEFEAT -> {
                        RetroDialogueBox(
                            text = battleState.dialogueText,
                            promptContinue = battleState.promptContinue,
                            onClick = { viewModel.advanceDialogueOrIntro() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    BattlePhase.PLAYER_MENU -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            RetroDialogueBox(
                                text = battleState.dialogueText,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RetroButton(
                                    text = "FIGHT",
                                    color = Color(0xFFDC2626),
                                    onClick = { viewModel.selectMenuChoice(BattleMenuChoice.FIGHT) },
                                    modifier = Modifier.weight(1.2f),
                                    tag = "battle_fight_button"
                                )
                                RetroButton(
                                    text = "CAPTURE",
                                    color = Color(0xFF2563EB),
                                    onClick = { viewModel.selectMenuChoice(BattleMenuChoice.BALL) },
                                    modifier = Modifier.weight(1.2f),
                                    tag = "battle_catch_button"
                                )
                                RetroButton(
                                    text = "STATS 🔍",
                                    color = Color(0xFF7C3AED),
                                    onClick = { viewModel.toggleOpponentStats(true) },
                                    modifier = Modifier.weight(1f),
                                    tag = "battle_stats_button"
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RetroButton(
                                    text = "POTION",
                                    color = Color(0xFF059669),
                                    onClick = { viewModel.selectMenuChoice(BattleMenuChoice.BAG) },
                                    modifier = Modifier.weight(1f),
                                    tag = "battle_bag_button"
                                )
                                RetroButton(
                                    text = "RUN",
                                    color = Color(0xFF4B5563),
                                    onClick = { viewModel.selectMenuChoice(BattleMenuChoice.RUN) },
                                    modifier = Modifier.weight(1f),
                                    tag = "battle_run_button"
                                )
                            }
                        }
                    }

                    BattlePhase.SELECTING_MOVE -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "SELECT SAT MOVE",
                                    style = Typography.labelMedium.copy(
                                        fontFamily = PixelFontFamily,
                                        color = Color(0xFFFDE047)
                                    )
                                )
                                Text(
                                    text = "[BACK]",
                                    style = Typography.labelSmall.copy(
                                        fontFamily = PixelFontFamily,
                                        color = Color(0xFF94A3B8)
                                    ),
                                    modifier = Modifier
                                        .clickable { viewModel.cancelMoveSelection() }
                                        .padding(4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(battleState.playerMonster.moves.size) { index ->
                                    val move = battleState.playerMonster.moves[index]
                                    MoveSelectionCard(
                                        move = move,
                                        onClick = { viewModel.selectMove(move) }
                                    )
                                }
                            }
                        }
                    }

                    BattlePhase.QUESTION_CHALLENGE -> {
                        val q = battleState.activeQuestion
                        if (q != null) {
                            SatQuestionChallengeCard(
                                question = q,
                                selectedIndex = battleState.selectedAnswerIndex,
                                isCorrect = battleState.isAnswerCorrect,
                                onSelectAnswer = { viewModel.submitAnswer(it) }
                            )
                        }
                    }

                    BattlePhase.SHOWING_EXPLANATION -> {
                        val q = battleState.activeQuestion
                        if (q != null) {
                            SatExplanationCard(
                                question = q,
                                onContinue = { viewModel.dismissExplanationAndContinue() }
                            )
                        }
                    }

                    else -> {
                        RetroDialogueBox(
                            text = battleState.dialogueText,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // Opponent Full Stats & SAT Intel Overlay Modal
        if (battleState.showOpponentStatsDialog) {
            OpponentStatsDialog(
                monster = battleState.wildMonster,
                onDismiss = { viewModel.toggleOpponentStats(false) }
            )
        }
    }
}

@Composable
fun CapturedCelebrationCard(
    wildMonster: Scholarmon,
    playerMonster: Scholarmon,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(6.dp, RoundedCornerShape(8.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
                ),
                RoundedCornerShape(8.dp)
            )
            .border(2.5.dp, Color(0xFFFDE047), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "★ GOTCHA! CAPTURED! ★",
                    fontFamily = PixelFontFamily,
                    fontSize = 11.sp,
                    color = Color(0xFFFDE047),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131024), RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .background(Color(0xFF0F172A), RoundedCornerShape(6.dp))
                        .border(1.dp, Color(0xFF38BDF8), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    MonsterPixelSprite(
                        speciesId = wildMonster.speciesId,
                        isBackSprite = false,
                        modifier = Modifier.size(46.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "${wildMonster.name} was successfully caught!",
                        fontFamily = PixelFontFamily,
                        fontSize = 8.5.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Enrolled into your active Scholar Party & ScholarDex.",
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFF86EFAC)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            RetroButton(
                text = "CONTINUE EXPEDITION ▶",
                color = Color(0xFF22C55E),
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
                tag = "captured_continue_button"
            )
        }
    }
}

@Composable
fun OpponentStatsDialog(
    monster: Scholarmon,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.75f))
            .clickable { onDismiss() }
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(10.dp))
                .background(Color(0xFF1E1B2E), RoundedCornerShape(10.dp))
                .border(2.5.dp, Color(0xFFFDE047), RoundedCornerShape(10.dp))
                .clickable(enabled = false) {}
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🔍 ENEMY INTEL // STATS",
                    style = Typography.titleSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFFFDE047),
                        fontSize = 9.5.sp
                    )
                )
                Text(
                    text = "[X CLOSE]",
                    style = Typography.labelSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFF94A3B8),
                        fontSize = 8.sp
                    ),
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(Color(0xFF0F172A), RoundedCornerShape(6.dp))
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    MonsterPixelSprite(
                        speciesId = monster.speciesId,
                        isBackSprite = false,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "${monster.name} (Wild)",
                        style = Typography.titleMedium.copy(
                            fontFamily = PixelFontFamily,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TypeBadge(type = monster.type)
                        Text(
                            text = "LV.${monster.level}",
                            fontFamily = PixelFontFamily,
                            fontSize = 8.sp,
                            color = Color(0xFF38BDF8)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    RetroHpBar(currentHp = monster.currentHp, maxHp = monster.maxHp, modifier = Modifier.width(130.dp))
                    Text(
                        text = "HP: ${monster.currentHp}/${monster.maxHp}",
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Combat Attribute Matrix
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F172A), RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "ATK", fontFamily = PixelFontFamily, fontSize = 7.sp, color = Color(0xFFF87171))
                    Text(text = "${monster.attack}", fontFamily = MonoFontFamily, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "DEF", fontFamily = PixelFontFamily, fontSize = 7.sp, color = Color(0xFF60A5FA))
                    Text(text = "${monster.defense}", fontFamily = MonoFontFamily, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "SPD", fontFamily = PixelFontFamily, fontSize = 7.sp, color = Color(0xFF4ADE80))
                    Text(text = "${monster.speed}", fontFamily = MonoFontFamily, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "CATCH", fontFamily = PixelFontFamily, fontSize = 7.sp, color = Color(0xFFFBBF24))
                    Text(text = "${(100 - monster.hpFraction * 60).toInt()}%", fontFamily = MonoFontFamily, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SAT Academic Tactical Intel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF131024), RoundedCornerShape(6.dp))
                    .border(1.dp, Color(0xFF6366F1), RoundedCornerShape(6.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "⚡ ACADEMIC VULNERABILITY:",
                        fontFamily = PixelFontFamily,
                        fontSize = 7.5.sp,
                        color = Color(0xFFFDE047)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = monster.weakness.ifEmpty { "Linear Equations & Critical Reading" },
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFFFCA5A5)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "💡 TACTICAL SAT TIP:",
                        fontFamily = PixelFontFamily,
                        fontSize = 7.5.sp,
                        color = Color(0xFF38BDF8)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = monster.satTip.ifEmpty { "Solve quickly by testing answer choices against problem constraints." },
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            RetroButton(
                text = "RETURN TO BATTLE",
                color = Color(0xFF4F46E5),
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MoveSelectionCard(
    move: Move,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(6.dp))
            .background(Color(0xFF28243D), RoundedCornerShape(6.dp))
            .border(1.5.dp, Color(0xFF4A4468), RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = move.name,
                    style = Typography.titleSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color.White,
                        fontSize = 11.sp
                    )
                )
                Text(
                    text = move.description,
                    style = Typography.bodySmall.copy(
                        color = Color(0xFFCBD5E1),
                        fontSize = 11.sp
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                TypeBadge(type = move.type)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "PWR: ${move.power}",
                    style = Typography.labelSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFFFDE047),
                        fontSize = 7.sp
                    )
                )
            }
        }
    }
}

@Composable
fun SatQuestionChallengeCard(
    question: SATQuestion,
    selectedIndex: Int?,
    isCorrect: Boolean?,
    onSelectAnswer: (Int) -> Unit
) {
    var showHint by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
            .border(2.dp, Color(0xFF38BDF8), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        if (question.domain == SatDomain.MATH) Color(0xFFEA580C) else Color(0xFF2563EB),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${question.domain.label.uppercase()} • ${question.subtopic}",
                    style = Typography.labelSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color.White,
                        fontSize = 7.sp
                    )
                )
            }

            Text(
                text = if (showHint) "HIDE HINT" else "💡 HINT",
                style = Typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    color = Color(0xFFFDE047),
                    fontSize = 7.sp
                ),
                modifier = Modifier
                    .clickable { showHint = !showHint }
                    .padding(2.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (showHint) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E293B), RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(4.dp))
                    .padding(6.dp)
            ) {
                Text(
                    text = question.hint,
                    style = Typography.bodySmall.copy(
                        color = Color(0xFFFDE047),
                        fontSize = 11.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (question.contextPassage != null) {
            Text(
                text = question.contextPassage,
                style = Typography.bodySmall.copy(
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                ),
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(3.dp))
        }

        Text(
            text = question.prompt,
            style = Typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 4 Options Grid (2x2)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in question.options.indices step 2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionChoiceButton(
                        optionLetter = ('A' + i).toString(),
                        optionText = question.options[i],
                        isSelected = selectedIndex == i,
                        isCorrect = if (selectedIndex == i) isCorrect else null,
                        onClick = { if (selectedIndex == null) onSelectAnswer(i) },
                        modifier = Modifier.weight(1f)
                    )
                    if (i + 1 < question.options.size) {
                        OptionChoiceButton(
                            optionLetter = ('A' + (i + 1)).toString(),
                            optionText = question.options[i + 1],
                            isSelected = selectedIndex == (i + 1),
                            isCorrect = if (selectedIndex == (i + 1)) isCorrect else null,
                            onClick = { if (selectedIndex == null) onSelectAnswer(i + 1) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionChoiceButton(
    optionLetter: String,
    optionText: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        isSelected && isCorrect == true -> Color(0xFF16A34A)
        isSelected && isCorrect == false -> Color(0xFFDC2626)
        isSelected -> Color(0xFF4338CA)
        else -> Color(0xFF1E293B)
    }

    Box(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(6.dp))
            .background(bgColor, RoundedCornerShape(6.dp))
            .border(1.5.dp, Color(0xFF475569), RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "[$optionLetter]",
                style = Typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    color = Color(0xFFFDE047),
                    fontSize = 9.sp
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = optionText,
                style = Typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun SatExplanationCard(
    question: SATQuestion,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1B4B), RoundedCornerShape(8.dp))
            .border(2.5.dp, Color(0xFFF59E0B), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📖 SAT LESSON BREAKDOWN",
                style = Typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    color = Color(0xFFFDE047),
                    fontSize = 8.sp
                )
            )
            Text(
                text = "CORRECT: [${('A' + question.correctIndex)}]",
                style = Typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    color = Color(0xFF4ADE80),
                    fontSize = 8.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF0F172A), RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF334155), RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            LazyColumn {
                item {
                    Text(
                        text = question.explanation,
                        style = Typography.bodySmall.copy(
                            color = Color(0xFFF8FAFC),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        RetroButton(
            text = "CONTINUE BATTLE ▶",
            color = Color(0xFFEF4444),
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            tag = "continue_after_explanation_button"
        )
    }
}
