package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RetroButton
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.ScholarViewModel

@Composable
fun MasteryScreen(
    viewModel: ScholarViewModel,
    modifier: Modifier = Modifier
) {
    val progress by viewModel.playerProgress.collectAsState()

    // Estimated SAT Score Band calculation
    val overallAccuracy = if (progress.questionsAnswered > 0) {
        progress.questionsCorrect.toFloat() / progress.questionsAnswered.toFloat()
    } else 0.5f

    val estimatedScore = (600 + (overallAccuracy * 950).toInt()).coerceIn(400, 1600)
    val mathAcc = if (progress.mathTotal > 0) (progress.mathCorrect * 100 / progress.mathTotal) else 0
    val verbalAcc = if (progress.verbalTotal > 0) (progress.verbalCorrect * 100 / progress.verbalTotal) else 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        // Score Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
                    )
                )
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ESTIMATED DIGITAL SAT SCORE",
                    style = Typography.labelSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFF93C5FD),
                        fontSize = 8.sp
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$estimatedScore / 1600",
                    style = Typography.displayLarge.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFFFDE047),
                        fontSize = 24.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Based on ${progress.questionsAnswered} SAT Battle Questions",
                    style = Typography.bodySmall.copy(
                        color = Color(0xFFCBD5E1),
                        fontSize = 11.sp
                    )
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            item {
                // Section: SAT Domain Accuracy Breakdown
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "DOMAIN ACCURACY",
                            style = Typography.labelSmall.copy(
                                fontFamily = PixelFontFamily,
                                color = Color(0xFFFDE047),
                                fontSize = 8.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Math
                        AccuracyBarRow(
                            label = "SAT MATH",
                            subtext = "Algebra, Quadratics, Geometry",
                            percentage = mathAcc,
                            countText = "${progress.mathCorrect}/${progress.mathTotal}",
                            color = Color(0xFFEA580C)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Verbal
                        AccuracyBarRow(
                            label = "READING & WRITING",
                            subtext = "Vocabulary, Grammar, Transitions",
                            percentage = verbalAcc,
                            countText = "${progress.verbalCorrect}/${progress.verbalTotal}",
                            color = Color(0xFF2563EB)
                        )
                    }
                }
            }

            item {
                // Battle Stats Grid
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "BATTLE ENGAGEMENT STATS",
                            style = Typography.labelSmall.copy(
                                fontFamily = PixelFontFamily,
                                color = Color(0xFFFDE047),
                                fontSize = 8.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            StatMetricCard(title = "BATTLES WON", value = "${progress.battlesWon}", modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(6.dp))
                            StatMetricCard(title = "CAPTURED", value = "${progress.creaturesCaught}", modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(6.dp))
                            StatMetricCard(title = "BEST STREAK", value = "🔥 ${progress.bestStreak}", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            item {
                // Badges Grid
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "SCHOLAR BADGES & RECOGNITION",
                            style = Typography.labelSmall.copy(
                                fontFamily = PixelFontFamily,
                                color = Color(0xFFFDE047),
                                fontSize = 8.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val badges = listOf(
                            Triple("First Encounter", "Defeat your first wild monster", progress.battlesWon >= 1),
                            Triple("Linear Pioneer", "Answer 3 Math questions correctly", progress.mathCorrect >= 3),
                            Triple("Lexicon Master", "Answer 3 Verbal questions correctly", progress.verbalCorrect >= 3),
                            Triple("Scholar-Ball Pro", "Capture at least 1 wild Scholarmon", progress.creaturesCaught >= 1),
                            Triple("Perfectionist", "Achieve a 5-question answer streak", progress.bestStreak >= 5)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            badges.forEach { (name, desc, isEarned) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if (isEarned) Color(0xFF0F172A) else Color(0xFF161E2E), RoundedCornerShape(4.dp))
                                        .border(1.dp, if (isEarned) Color(0xFFEAB308) else Color(0xFF334155), RoundedCornerShape(4.dp))
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = if (isEarned) "🏅" else "🔒",
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = name,
                                            fontFamily = PixelFontFamily,
                                            fontSize = 9.sp,
                                            color = if (isEarned) Color.White else Color(0xFF64748B)
                                        )
                                        Text(
                                            text = desc,
                                            fontFamily = MonoFontFamily,
                                            fontSize = 11.sp,
                                            color = if (isEarned) Color(0xFFCBD5E1) else Color(0xFF475569)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                RetroButton(
                    text = "RESUME EXPEDITION ▶",
                    color = Color(0xFF4F46E5),
                    onClick = { viewModel.navigateTo(AppScreen.OVERWORLD) },
                    modifier = Modifier.fillMaxWidth(),
                    tag = "resume_expedition_button"
                )
            }
        }
    }
}

@Composable
fun AccuracyBarRow(
    label: String,
    subtext: String,
    percentage: Int,
    countText: String,
    color: Color
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontFamily = PixelFontFamily,
                fontSize = 9.sp,
                color = Color.White
            )
            Text(
                text = "$percentage% ($countText)",
                fontFamily = MonoFontFamily,
                fontSize = 12.sp,
                color = Color(0xFFFDE047),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = subtext,
            fontFamily = MonoFontFamily,
            fontSize = 10.sp,
            color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF0F172A), RoundedCornerShape(4.dp))
                .border(0.5.dp, Color(0xFF334155), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage / 100f)
                    .background(color, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun StatMetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF0F172A), RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFF334155), RoundedCornerShape(6.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontFamily = PixelFontFamily,
                fontSize = 11.sp,
                color = Color(0xFFFDE047)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = title,
                fontFamily = PixelFontFamily,
                fontSize = 6.5.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}
