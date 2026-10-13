package com.emberwingindustriesllc.satgame.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emberwingindustriesllc.satgame.audio.BgmTrack
import com.emberwingindustriesllc.satgame.audio.RetroAudioEngine
import com.emberwingindustriesllc.satgame.data.model.AcademiaMap
import com.emberwingindustriesllc.satgame.data.model.ZoneLocation
import com.emberwingindustriesllc.satgame.ui.components.RealmExpressAnimatedMap
import com.emberwingindustriesllc.satgame.ui.components.RetroButton
import com.emberwingindustriesllc.satgame.ui.theme.*
import com.emberwingindustriesllc.satgame.ui.viewmodel.AppScreen
import com.emberwingindustriesllc.satgame.ui.viewmodel.ScholarViewModel

@Composable
fun OverworldMapScreen(
    viewModel: ScholarViewModel,
    modifier: Modifier = Modifier
) {
    val playerProgress by viewModel.playerProgress.collectAsState()
    val partyCreatures by viewModel.partyCreatures.collectAsState()

    var selectedStationIndex by remember { mutableStateOf(0) }
    var showCabinView by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        RetroAudioEngine.startBgm(BgmTrack.MAP_EXPRESS)
    }

    val currentSelectedZone = AcademiaMap.ZONES[selectedStationIndex.coerceIn(0, AcademiaMap.ZONES.size - 1)]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        // === Top Banner: Scholars' Realm Express ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF312E81), Color(0xFF1E1B4B))
                    )
                )
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "🚂 SCHOLARS' REALM EXPRESS",
                        style = Typography.titleMedium.copy(
                            fontFamily = PixelFontFamily,
                            color = Color(0xFFFDE047),
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Learn • Grow • Achieve",
                        style = Typography.bodySmall.copy(
                            color = Color(0xFF93C5FD),
                            fontSize = 10.sp
                        )
                    )
                }

                // Next Stop Pill Ticker
                Box(
                    modifier = Modifier
                        .shadow(3.dp, RoundedCornerShape(6.dp))
                        .background(Color(0xFF0F172A), RoundedCornerShape(6.dp))
                        .border(1.dp, Color(0xFFFDE047), RoundedCornerShape(6.dp))
                        .clickable {
                            RetroAudioEngine.playTrainWhistle()
                            selectedStationIndex = (selectedStationIndex + 1) % AcademiaMap.ZONES.size
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "NEXT: ${currentSelectedZone.name.take(14)} ▶",
                            fontFamily = PixelFontFamily,
                            fontSize = 7.sp,
                            color = Color(0xFFFDE047)
                        )
                    }
                }
            }
        }

        // === Middle Animated Railway Map Canvas ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .shadow(6.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
        ) {
            RealmExpressAnimatedMap(
                selectedStationIndex = selectedStationIndex,
                onSelectStation = { idx ->
                    RetroAudioEngine.playTrainWhistle()
                    selectedStationIndex = idx
                },
                modifier = Modifier.fillMaxSize()
            )

            // Floating Train Whistle Button
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .shadow(3.dp, CircleShape)
                    .background(Color(0xFFDC2626), CircleShape)
                    .border(1.5.dp, Color(0xFFFDE047), CircleShape)
                    .clickable {
                        RetroAudioEngine.playTrainWhistle()
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "🔔 WHISTLE",
                    fontFamily = PixelFontFamily,
                    fontSize = 7.sp,
                    color = Color.White
                )
            }
        }

        // === Bottom Station & Cabin Console (Scrollable) ===
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Owl Guide & On-Board Express Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(8.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
                            ),
                            RoundedCornerShape(8.dp)
                        )
                        .border(1.5.dp, Color(0xFF6366F1), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Archimedes the Owl avatar
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color(0xFF312E81), RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFFFDE047), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🦉",
                                fontSize = 26.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ARCHIMEDES (SCHOLAR GUIDE):",
                                fontFamily = PixelFontFamily,
                                fontSize = 7.5.sp,
                                color = Color(0xFFFDE047)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "\"Great job! Solving SAT problems powers our train to new knowledge stops!\"",
                                fontFamily = MonoFontFamily,
                                fontSize = 11.sp,
                                color = Color(0xFFE2E8F0)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        RetroButton(
                            text = if (showCabinView) "MAP" else "CABIN",
                            color = Color(0xFF3B82F6),
                            onClick = { showCabinView = !showCabinView },
                            tag = "toggle_cabin_view"
                        )
                    }
                }
            }

            // Passenger Window Cabin View (Collapsible)
            if (showCabinView) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                            .background(Color(0xFF2E1065), RoundedCornerShape(8.dp))
                            .border(2.dp, Color(0xFFEAB308), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "🚂 ON BOARD THE SCHOLARS' REALM EXPRESS",
                                fontFamily = PixelFontFamily,
                                fontSize = 8.sp,
                                color = Color(0xFFFDE047)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val goals = listOf(
                                "✔ Build vocabulary with context clues",
                                "✔ Master linear equations & quadratics",
                                "✔ Sharpen punctuation & sentence boundaries",
                                "✔ Automatically capture wild Scholarmon"
                            )
                            goals.forEach { g ->
                                Text(
                                    text = g,
                                    fontFamily = MonoFontFamily,
                                    fontSize = 11.sp,
                                    color = Color(0xFFF1F5F9),
                                    modifier = Modifier.padding(vertical = 1.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Active Selected Station Card
            item {
                ActiveStationCard(
                    zone = currentSelectedZone,
                    onBoardAndBattle = {
                        viewModel.startEncounter(currentSelectedZone)
                    }
                )
            }

            // Campus Library Healer
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(8.dp))
                        .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color(0xFF38BDF8), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "🏛️ CAMPUS DEPOT & INFIRMARY",
                                style = Typography.labelSmall.copy(
                                    fontFamily = PixelFontFamily,
                                    color = Color(0xFF38BDF8),
                                    fontSize = 8.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Rest your Scholarmon party to full HP.",
                                style = Typography.bodySmall.copy(
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 11.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        RetroButton(
                            text = "HEAL ALL",
                            color = Color(0xFF059669),
                            onClick = {
                                RetroAudioEngine.playEnergyRecover()
                                viewModel.healParty()
                            },
                            tag = "library_heal_button"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveStationCard(
    zone: ZoneLocation,
    onBoardAndBattle: () -> Unit
) {
    val gradientColors = when (zone.domainFocus.name) {
        "MATH" -> listOf(Color(0xFF431407), Color(0xFF1E1B2E))
        else -> listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Brush.horizontalGradient(gradientColors), RoundedCornerShape(8.dp))
            .border(2.dp, Color(0xFFFDE047), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(0xFF090D16), RoundedCornerShape(6.dp))
                            .border(1.dp, Color(0xFFFDE047), RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = zone.iconEmoji,
                            fontFamily = PixelFontFamily,
                            fontSize = 11.sp,
                            color = Color(0xFFFDE047)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = zone.name.uppercase(),
                            style = Typography.titleSmall.copy(
                                fontFamily = PixelFontFamily,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = zone.subtitle,
                            style = Typography.bodySmall.copy(
                                color = Color(0xFF93C5FD),
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "TARGET: ${zone.difficultyTier}",
                    fontFamily = PixelFontFamily,
                    fontSize = 7.5.sp,
                    color = Color(0xFF38BDF8)
                )
                Text(
                    text = "FOCUS: ${zone.domainFocus.label}",
                    fontFamily = PixelFontFamily,
                    fontSize = 7.5.sp,
                    color = Color(0xFFF59E0B)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            RetroButton(
                text = "BOARD EXPRESS & BATTLE ▶",
                color = Color(0xFFDC2626),
                onClick = onBoardAndBattle,
                modifier = Modifier.fillMaxWidth(),
                tag = "board_express_button"
            )
        }
    }
}
