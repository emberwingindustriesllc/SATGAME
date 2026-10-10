package com.example.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.RetroAudioEngine
import com.example.data.model.ScholarmonType
import com.example.ui.theme.*

fun triggerHapticFeedback(context: Context) {
    try {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(18, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(18)
        }
    } catch (_: Exception) {}
}

@Composable
fun RetroHpBar(
    currentHp: Int,
    maxHp: Int,
    modifier: Modifier = Modifier
) {
    val fraction = (currentHp.toFloat() / maxHp.toFloat()).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 600, easing = EaseInOutCubic),
        label = "hp_fill"
    )

    val targetColor = when {
        fraction > 0.5f -> RetroHpGreen
        fraction > 0.2f -> RetroHpYellow
        else -> RetroHpRed
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(400),
        label = "hp_color"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color(0xFF27272A), RoundedCornerShape(4.dp))
            .border(1.5.dp, Color(0xFF09090B), RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        // "HP" Tag
        Box(
            modifier = Modifier
                .background(Color(0xFFF59E0B), RoundedCornerShape(2.dp))
                .padding(horizontal = 4.dp, vertical = 1.dp)
        ) {
            Text(
                text = "HP",
                style = Typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    color = Color(0xFF451A03),
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Bar Track
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(Color(0xFF3F3F46), RoundedCornerShape(2.dp))
                .border(0.8.dp, Color(0xFF18181B), RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .background(animatedColor, RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun RetroExpBar(
    currentExp: Int,
    maxExp: Int,
    modifier: Modifier = Modifier
) {
    val fraction = (currentExp.toFloat() / maxExp.toFloat()).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(400),
        label = "exp_fill"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color(0xFF18181B), RoundedCornerShape(3.dp))
            .padding(horizontal = 3.dp, vertical = 1.dp)
    ) {
        Text(
            text = "EXP",
            style = Typography.labelSmall.copy(
                fontFamily = PixelFontFamily,
                color = Color(0xFF60A5FA),
                fontSize = 6.sp
            )
        )
        Spacer(modifier = Modifier.width(3.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .background(Color(0xFF27272A), RoundedCornerShape(1.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .background(RetroExpBlue, RoundedCornerShape(1.dp))
            )
        }
    }
}

@Composable
fun RetroDialogueBox(
    text: String,
    modifier: Modifier = Modifier,
    promptContinue: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "arrow_bounce")
    val arrowOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arrow_offset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(RetroDialogBackground, RoundedCornerShape(8.dp))
            .border(3.dp, RetroDialogBorder, RoundedCornerShape(8.dp))
            .border(1.dp, RetroDialogShadow, RoundedCornerShape(6.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(12.dp)
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(
                color = RetroFontPrimary,
                fontFamily = MonoFontFamily,
                fontSize = 15.sp,
                lineHeight = 22.sp
            ),
            modifier = Modifier.padding(end = if (promptContinue) 16.dp else 0.dp)
        )

        if (promptContinue) {
            Text(
                text = "▶",
                style = Typography.titleMedium.copy(
                    color = RetroFontPrimary,
                    fontFamily = PixelFontFamily,
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = arrowOffset.dp)
            )
        }
    }
}

@Composable
fun RetroButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4F46E5),
    textColor: Color = Color.White,
    tag: String = "retro_button"
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val yOffset = if (isPressed) 2.dp else 0.dp
    val shadowDepth = if (isPressed) 1.dp else 3.dp

    Box(
        modifier = modifier
            .testTag(tag)
            .offset(y = yOffset)
            .shadow(shadowDepth, RoundedCornerShape(6.dp))
            .background(color, RoundedCornerShape(6.dp))
            .border(1.5.dp, Color(0xFF1E1B4B), RoundedCornerShape(6.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                triggerHapticFeedback(context)
                RetroAudioEngine.playSelect()
                onClick()
            }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.labelLarge.copy(
                color = textColor,
                fontFamily = PixelFontFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun TypeBadge(
    type: ScholarmonType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(type.color, RoundedCornerShape(4.dp))
            .border(1.dp, Color(0xFF0F172A), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = type.displayName.uppercase(),
            style = Typography.labelSmall.copy(
                fontFamily = PixelFontFamily,
                color = Color.White,
                fontSize = 7.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun MonsterBattleStatusCard(
    name: String,
    level: Int,
    currentHp: Int,
    maxHp: Int,
    type: ScholarmonType,
    currentExp: Int? = null,
    maxExp: Int? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(3.dp, RoundedCornerShape(8.dp))
            .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
            .border(2.5.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    style = Typography.titleSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFF0F172A),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = ":L$level",
                    style = Typography.labelSmall.copy(
                        fontFamily = PixelFontFamily,
                        color = Color(0xFF475569),
                        fontSize = 9.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(3.dp))
            TypeBadge(type = type)
            Spacer(modifier = Modifier.height(4.dp))

            RetroHpBar(currentHp = currentHp, maxHp = maxHp)

            Spacer(modifier = Modifier.height(3.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$currentHp/$maxHp",
                    style = Typography.labelSmall.copy(
                        fontFamily = MonoFontFamily,
                        color = Color(0xFF1E293B),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            if (currentExp != null && maxExp != null) {
                Spacer(modifier = Modifier.height(2.dp))
                RetroExpBar(currentExp = currentExp, maxExp = maxExp)
            }
        }
    }
}
