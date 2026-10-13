package com.emberwingindustriesllc.satgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emberwingindustriesllc.satgame.audio.RetroAudioEngine
import com.emberwingindustriesllc.satgame.ui.theme.PixelFontFamily

@Composable
fun HandheldConsoleWrapper(
    isHandheldMode: Boolean,
    onToggleMode: () -> Unit,
    onDpadPress: (direction: String) -> Unit = {},
    onButtonAPress: () -> Unit = {},
    onButtonBPress: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (!isHandheldMode) {
        // Modern Full-screen mode
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    } else {
        // Retro GBA / Game Boy Handheld Enclosure
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF28205C), Color(0xFF1B153F), Color(0xFF130E2E))
                    )
                )
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top GBA Branding bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFF22C55E), CircleShape)
                            .border(1.dp, Color(0xFF15803D), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "POWER",
                        fontFamily = PixelFontFamily,
                        fontSize = 7.sp,
                        color = Color(0xFF94A3B8)
                    )
                }

                Text(
                    text = "SCHOLAR ADVANCE SP",
                    fontFamily = PixelFontFamily,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE2E8F0)
                )

                Text(
                    text = "FIT SCREEN",
                    fontFamily = PixelFontFamily,
                    fontSize = 7.sp,
                    color = Color(0xFF93C5FD),
                    modifier = Modifier
                        .clickable { onToggleMode() }
                        .padding(4.dp)
                )
            }

            // GBA Screen Bezel & Glass
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .shadow(12.dp, RoundedCornerShape(14.dp))
                    .background(Color(0xFF110E1D), RoundedCornerShape(14.dp))
                    .border(4.dp, Color(0xFF3B336A), RoundedCornerShape(14.dp))
                    .border(2.dp, Color(0xFF090710), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                // Game Screen
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE8ECE5))
                ) {
                    content()
                }
            }

            // Bottom Handheld Controller Pad (D-Pad, A/B buttons)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                // D-Pad
                RetroDPad(onDirectionPress = onDpadPress)

                // Speaker Grille & Select/Start
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PillButton(text = "SELECT", onClick = { onToggleMode() })
                        PillButton(text = "START", onClick = { onButtonAPress() })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // Speaker dots
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(5) {
                            Box(
                                modifier = Modifier
                                    .size(3.5.dp)
                                    .background(Color(0xFF130E2E), CircleShape)
                            )
                        }
                    }
                }

                // Action Buttons A and B (tilted layout)
                RetroActionButtons(
                    onPressA = onButtonAPress,
                    onPressB = onButtonBPress
                )
            }
        }
    }
}

@Composable
private fun RetroDPad(onDirectionPress: (String) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.size(90.dp),
        contentAlignment = Alignment.Center
    ) {
        // Cross background
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(84.dp)
                .background(Color(0xFF1E1B2E), RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFF090710), RoundedCornerShape(4.dp))
        )
        Box(
            modifier = Modifier
                .width(84.dp)
                .height(28.dp)
                .background(Color(0xFF1E1B2E), RoundedCornerShape(4.dp))
                .border(2.dp, Color(0xFF090710), RoundedCornerShape(4.dp))
        )

        // Up
        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.TopCenter)
                .clickable {
                    triggerHapticFeedback(context)
                    RetroAudioEngine.playSelect()
                    onDirectionPress("UP")
                }
        )
        // Down
        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    triggerHapticFeedback(context)
                    RetroAudioEngine.playSelect()
                    onDirectionPress("DOWN")
                }
        )
        // Left
        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    triggerHapticFeedback(context)
                    RetroAudioEngine.playSelect()
                    onDirectionPress("LEFT")
                }
        )
        // Right
        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    triggerHapticFeedback(context)
                    RetroAudioEngine.playSelect()
                    onDirectionPress("RIGHT")
                }
        )
    }
}

@Composable
private fun RetroActionButtons(
    onPressA: () -> Unit,
    onPressB: () -> Unit
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // B Button (Lower)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .offset(y = 10.dp)
                    .size(38.dp)
                    .shadow(3.dp, CircleShape)
                    .background(Color(0xFF7C3AED), CircleShape)
                    .border(2.dp, Color(0xFF4C1D95), CircleShape)
                    .clickable {
                        triggerHapticFeedback(context)
                        RetroAudioEngine.playSelect()
                        onPressB()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "B",
                    fontFamily = PixelFontFamily,
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // A Button (Higher)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .offset(y = (-6).dp)
                    .size(38.dp)
                    .shadow(3.dp, CircleShape)
                    .background(Color(0xFFEF4444), CircleShape)
                    .border(2.dp, Color(0xFF991B1B), CircleShape)
                    .clickable {
                        triggerHapticFeedback(context)
                        RetroAudioEngine.playConfirm()
                        onPressA()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    fontFamily = PixelFontFamily,
                    fontSize = 11.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PillButton(text: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(26.dp)
                .height(9.dp)
                .background(Color(0xFF334155), RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF0F172A), RoundedCornerShape(4.dp))
                .clickable {
                    triggerHapticFeedback(context)
                    RetroAudioEngine.playSelect()
                    onClick()
                }
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = text,
            fontFamily = PixelFontFamily,
            fontSize = 5.5.sp,
            color = Color(0xFF94A3B8)
        )
    }
}
