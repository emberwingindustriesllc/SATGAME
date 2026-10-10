package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.sin

enum class SpriteAnimationState {
    IDLE,
    ATTACKING,
    TAKING_DAMAGE,
    CAPTURED,
    FAINTED
}

@Composable
fun MonsterPixelSprite(
    speciesId: String,
    isBackSprite: Boolean = false,
    animationState: SpriteAnimationState = SpriteAnimationState.IDLE,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "idle_bounce")
    val idleBob by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_bob"
    )

    // Damage shake
    val damageFlashAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(90, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "damage_flash"
    )

    Canvas(modifier = modifier) {
        val yOffset = when (animationState) {
            SpriteAnimationState.IDLE -> idleBob
            SpriteAnimationState.ATTACKING -> if (isBackSprite) -12f else 12f
            SpriteAnimationState.TAKING_DAMAGE -> idleBob
            SpriteAnimationState.CAPTURED -> 20f
            SpriteAnimationState.FAINTED -> size.height * 0.8f
        }

        val alpha = if (animationState == SpriteAnimationState.TAKING_DAMAGE) damageFlashAlpha else 1f

        drawMonsterPixelArt(
            speciesId = speciesId,
            isBackSprite = isBackSprite,
            yOffset = yOffset,
            alpha = alpha
        )
    }
}

private fun DrawScope.drawMonsterPixelArt(
    speciesId: String,
    isBackSprite: Boolean,
    yOffset: Float,
    alpha: Float
) {
    val pixelSize = (size.width / 16f).coerceAtLeast(2f)
    val startX = (size.width - pixelSize * 16) / 2f
    val startY = (size.height - pixelSize * 16) / 2f + yOffset

    when (speciesId) {
        "algeray" -> drawAlgeraySprite(startX, startY, pixelSize, isBackSprite, alpha)
        "vocabularva" -> drawVocabularvaSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "pythagoruff" -> drawPythagoruffSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "syntaxpider" -> drawSyntaxpiderSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "parabolon" -> drawParabolonSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "thesauron" -> drawThesauronSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "hypotenotter" -> drawHypotenotterSprite(startX, startY, pixelSize, isBackSprite, alpha)
        "logarithmoth" -> drawLogarithmothSprite(startX, startY, pixelSize, isBackSprite, alpha)
        else -> drawAlgeraySprite(startX, startY, pixelSize, isBackSprite, alpha)
    }
}

// 16x16 Pixel Grid Drawing Utilities
private fun DrawScope.drawPixel(
    gridX: Int,
    gridY: Int,
    startX: Float,
    startY: Float,
    pixelSize: Float,
    color: Color,
    alpha: Float = 1f
) {
    drawRect(
        color = color.copy(alpha = alpha),
        topLeft = Offset(startX + gridX * pixelSize, startY + gridY * pixelSize),
        size = Size(pixelSize, pixelSize)
    )
}

// Algeray: Electric / Algebra Manta Ray
private fun DrawScope.drawAlgeraySprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cBody = Color(0xFFF97316)
    val cBelly = Color(0xFFFED7AA)
    val cDark = Color(0xFFC2410C)
    val cGlow = Color(0xFFFACC15)
    val cOutline = Color(0xFF431407)
    val cEye = Color(0xFF1E1B4B)

    // Body wings
    for (x in 4..11) {
        for (y in 5..10) {
            drawPixel(x, y, startX, startY, p, cBody, alpha)
        }
    }
    // Wings span
    for (y in 6..8) {
        drawPixel(2, y, startX, startY, p, cDark, alpha)
        drawPixel(3, y, startX, startY, p, cBody, alpha)
        drawPixel(12, y, startX, startY, p, cBody, alpha)
        drawPixel(13, y, startX, startY, p, cDark, alpha)
    }
    // Electric X mark on back / belly
    drawPixel(6, 6, startX, startY, p, cGlow, alpha)
    drawPixel(9, 6, startX, startY, p, cGlow, alpha)
    drawPixel(7, 7, startX, startY, p, cGlow, alpha)
    drawPixel(8, 7, startX, startY, p, cGlow, alpha)
    drawPixel(6, 8, startX, startY, p, cGlow, alpha)
    drawPixel(9, 8, startX, startY, p, cGlow, alpha)

    // Tail
    drawPixel(7, 11, startX, startY, p, cDark, alpha)
    drawPixel(8, 11, startX, startY, p, cDark, alpha)
    drawPixel(7, 12, startX, startY, p, cGlow, alpha)
    drawPixel(8, 13, startX, startY, p, cGlow, alpha)

    // Eyes
    if (!isBack) {
        drawPixel(5, 5, startX, startY, p, cEye, alpha)
        drawPixel(10, 5, startX, startY, p, cEye, alpha)
        drawPixel(5, 4, startX, startY, p, Color.White, alpha)
        drawPixel(10, 4, startX, startY, p, Color.White, alpha)
    } else {
        // Dorsal fin & academic crest
        drawPixel(7, 4, startX, startY, p, cOutline, alpha)
        drawPixel(8, 4, startX, startY, p, cOutline, alpha)
    }
}

// Vocabularva: Lexicon Caterpillar
private fun DrawScope.drawVocabularvaSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cBody = Color(0xFF22C55E)
    val cRing = Color(0xFF86EFAC)
    val cAccent = Color(0xFF3B82F6)
    val cEye = Color(0xFF0F172A)

    // Segment 1 (Head)
    for (x in 4..8) for (y in 4..7) drawPixel(x, y, startX, startY, p, cBody, alpha)
    // Segment 2
    for (x in 7..10) for (y in 6..9) drawPixel(x, y, startX, startY, p, cRing, alpha)
    // Segment 3
    for (x in 9..12) for (y in 8..11) drawPixel(x, y, startX, startY, p, cBody, alpha)
    // Tail
    drawPixel(12, 11, startX, startY, p, cAccent, alpha)
    drawPixel(13, 10, startX, startY, p, cAccent, alpha)

    // Antennae (Quill shape)
    drawPixel(4, 2, startX, startY, p, cAccent, alpha)
    drawPixel(5, 3, startX, startY, p, cAccent, alpha)
    drawPixel(7, 2, startX, startY, p, cAccent, alpha)
    drawPixel(6, 3, startX, startY, p, cAccent, alpha)

    if (!isBack) {
        drawPixel(4, 5, startX, startY, p, cEye, alpha)
        drawPixel(6, 5, startX, startY, p, cEye, alpha)
    }
}

// Pythagoruff: Geometric Armor Pup
private fun DrawScope.drawPythagoruffSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cPup = Color(0xFFD97706)
    val cEar = Color(0xFF92400E)
    val cArmor = Color(0xFF38BDF8)
    val cEye = Color(0xFF1E293B)

    // Head
    for (x in 5..10) for (y in 4..8) drawPixel(x, y, startX, startY, p, cPup, alpha)
    // Ears (Triangles)
    drawPixel(4, 3, startX, startY, p, cEar, alpha)
    drawPixel(5, 4, startX, startY, p, cEar, alpha)
    drawPixel(11, 3, startX, startY, p, cEar, alpha)
    drawPixel(10, 4, startX, startY, p, cEar, alpha)

    // Body
    for (x in 4..11) for (y in 9..12) drawPixel(x, y, startX, startY, p, cPup, alpha)

    // Triangular 90° shield emblem on chest/back
    drawPixel(7, 9, startX, startY, p, cArmor, alpha)
    drawPixel(7, 10, startX, startY, p, cArmor, alpha)
    drawPixel(8, 10, startX, startY, p, cArmor, alpha)
    drawPixel(7, 11, startX, startY, p, cArmor, alpha)
    drawPixel(8, 11, startX, startY, p, cArmor, alpha)
    drawPixel(9, 11, startX, startY, p, cArmor, alpha)

    // Legs
    drawPixel(5, 13, startX, startY, p, cEar, alpha)
    drawPixel(6, 13, startX, startY, p, cEar, alpha)
    drawPixel(9, 13, startX, startY, p, cEar, alpha)
    drawPixel(10, 13, startX, startY, p, cEar, alpha)

    if (!isBack) {
        drawPixel(6, 5, startX, startY, p, cEye, alpha)
        drawPixel(9, 5, startX, startY, p, cEye, alpha)
        drawPixel(7, 7, startX, startY, p, Color.Black, alpha)
        drawPixel(8, 7, startX, startY, p, Color.Black, alpha)
    }
}

// Syntaxpider: Grammar Spider
private fun DrawScope.drawSyntaxpiderSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cSpider = Color(0xFF6B21A8)
    val cMark = Color(0xFFA855F7)
    val cGlow = Color(0xFF06B6D4)

    // Center Abdomen & Head
    for (x in 6..9) for (y in 5..9) drawPixel(x, y, startX, startY, p, cSpider, alpha)
    // Semicolon marking (; on abdomen)
    drawPixel(7, 6, startX, startY, p, cGlow, alpha)
    drawPixel(8, 6, startX, startY, p, cGlow, alpha)
    drawPixel(7, 8, startX, startY, p, cGlow, alpha)
    drawPixel(6, 9, startX, startY, p, cGlow, alpha)

    // 8 Pixel Legs
    drawPixel(4, 4, startX, startY, p, cSpider, alpha)
    drawPixel(5, 5, startX, startY, p, cMark, alpha)
    drawPixel(11, 4, startX, startY, p, cSpider, alpha)
    drawPixel(10, 5, startX, startY, p, cMark, alpha)

    drawPixel(3, 7, startX, startY, p, cSpider, alpha)
    drawPixel(4, 7, startX, startY, p, cMark, alpha)
    drawPixel(12, 7, startX, startY, p, cSpider, alpha)
    drawPixel(11, 7, startX, startY, p, cMark, alpha)

    drawPixel(4, 10, startX, startY, p, cSpider, alpha)
    drawPixel(5, 9, startX, startY, p, cMark, alpha)
    drawPixel(11, 10, startX, startY, p, cSpider, alpha)
    drawPixel(10, 9, startX, startY, p, cMark, alpha)

    if (!isBack) {
        drawPixel(7, 5, startX, startY, p, Color.Red, alpha)
        drawPixel(8, 5, startX, startY, p, Color.Red, alpha)
    }
}

// Parabolon: Cosmic Arc Creature
private fun DrawScope.drawParabolonSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cCore = Color(0xFF7C3AED)
    val cArc = Color(0xFFE879F9)
    val cStar = Color(0xFFFDE047)

    // Parabolic U curve body
    for (x in 5..10) drawPixel(x, 9, startX, startY, p, cCore, alpha)
    for (x in 4..5) drawPixel(x, 7, startX, startY, p, cArc, alpha)
    for (x in 10..11) drawPixel(x, 7, startX, startY, p, cArc, alpha)
    drawPixel(3, 5, startX, startY, p, cArc, alpha)
    drawPixel(12, 5, startX, startY, p, cArc, alpha)
    drawPixel(2, 3, startX, startY, p, cStar, alpha)
    drawPixel(13, 3, startX, startY, p, cStar, alpha)

    // Center eye / vertex
    drawPixel(7, 8, startX, startY, p, cStar, alpha)
    drawPixel(8, 8, startX, startY, p, cStar, alpha)
    if (!isBack) {
        drawPixel(7, 7, startX, startY, p, Color.White, alpha)
        drawPixel(8, 7, startX, startY, p, Color.White, alpha)
    }
}

// Thesauron: Rhetorical Dragon
private fun DrawScope.drawThesauronSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cDragon = Color(0xFFDC2626)
    val cHorn = Color(0xFFF59E0B)
    val cWing = Color(0xFFF87171)
    val cEye = Color(0xFF0F172A)

    // Head
    for (x in 6..10) for (y in 4..7) drawPixel(x, y, startX, startY, p, cDragon, alpha)
    // Horns
    drawPixel(5, 2, startX, startY, p, cHorn, alpha)
    drawPixel(6, 3, startX, startY, p, cHorn, alpha)
    drawPixel(10, 3, startX, startY, p, cHorn, alpha)
    drawPixel(11, 2, startX, startY, p, cHorn, alpha)

    // Body
    for (x in 5..10) for (y in 8..11) drawPixel(x, y, startX, startY, p, cDragon, alpha)

    // Wings
    drawPixel(2, 6, startX, startY, p, cWing, alpha)
    drawPixel(3, 6, startX, startY, p, cWing, alpha)
    drawPixel(4, 7, startX, startY, p, cWing, alpha)
    drawPixel(11, 7, startX, startY, p, cWing, alpha)
    drawPixel(12, 6, startX, startY, p, cWing, alpha)
    drawPixel(13, 6, startX, startY, p, cWing, alpha)

    // Tail
    drawPixel(7, 12, startX, startY, p, cDragon, alpha)
    drawPixel(8, 13, startX, startY, p, cHorn, alpha)

    if (!isBack) {
        drawPixel(7, 5, startX, startY, p, cEye, alpha)
        drawPixel(9, 5, startX, startY, p, cEye, alpha)
    }
}

// Hypotenotter
private fun DrawScope.drawHypotenotterSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cOtter = Color(0xFF0284C7)
    val cBelly = Color(0xFFBAE6FD)
    for (x in 5..10) for (y in 5..10) drawPixel(x, y, startX, startY, p, cOtter, alpha)
    for (x in 6..9) for (y in 7..9) drawPixel(x, y, startX, startY, p, cBelly, alpha)
    drawPixel(4, 5, startX, startY, p, cOtter, alpha)
    drawPixel(11, 5, startX, startY, p, cOtter, alpha)
    if (!isBack) {
        drawPixel(6, 6, startX, startY, p, Color.Black, alpha)
        drawPixel(9, 6, startX, startY, p, Color.Black, alpha)
    }
}

// Logarithmoth
private fun DrawScope.drawLogarithmothSprite(
    startX: Float,
    startY: Float,
    p: Float,
    isBack: Boolean,
    alpha: Float
) {
    val cBody = Color(0xFF10B981)
    val cWing = Color(0xFFA7F3D0)
    for (x in 7..8) for (y in 5..11) drawPixel(x, y, startX, startY, p, cBody, alpha)
    for (x in 3..6) for (y in 4..8) drawPixel(x, y, startX, startY, p, cWing, alpha)
    for (x in 9..12) for (y in 4..8) drawPixel(x, y, startX, startY, p, cWing, alpha)
    drawPixel(6, 3, startX, startY, p, Color.Black, alpha)
    drawPixel(9, 3, startX, startY, p, Color.Black, alpha)
}

@Composable
fun BattlePlatform(
    color: Color = Color(0xFF4ADE80),
    shadowColor: Color = Color(0xFF166534),
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer shadow
        drawOval(
            color = shadowColor,
            topLeft = Offset(0f, h * 0.25f),
            size = Size(w, h * 0.75f)
        )
        // Upper platform disk
        drawOval(
            color = color,
            topLeft = Offset(w * 0.04f, 0f),
            size = Size(w * 0.92f, h * 0.8f)
        )
        // Highlight rim
        drawOval(
            color = Color.White.copy(alpha = 0.25f),
            topLeft = Offset(w * 0.1f, h * 0.05f),
            size = Size(w * 0.8f, h * 0.5f)
        )
    }
}

@Composable
fun AnimatedScholarBall(
    wobbleCount: Int,
    isCaptured: Boolean,
    modifier: Modifier = Modifier
) {
    val wobbleTransition = rememberInfiniteTransition(label = "wobble")
    val rotation by wobbleTransition.animateFloat(
        initialValue = -18f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(140, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wobble_rot"
    )

    Canvas(modifier = modifier) {
        val effectiveRotation = if (wobbleCount in 1..3 && !isCaptured) rotation else 0f
        rotate(effectiveRotation, pivot = Offset(size.width / 2, size.height / 2)) {
            val r = size.minDimension / 2f
            val center = Offset(size.width / 2, size.height / 2)

            // Outer dark rim
            drawCircle(Color(0xFF0F172A), radius = r, center = center)
            // Top half red
            drawArc(
                color = Color(0xFFDC2626),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(center.x - r + 3f, center.y - r + 3f),
                size = Size((r - 3f) * 2, (r - 3f) * 2)
            )
            // Bottom half white
            drawArc(
                color = Color(0xFFF8FAFC),
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(center.x - r + 3f, center.y - r + 3f),
                size = Size((r - 3f) * 2, (r - 3f) * 2)
            )
            // Center band
            drawRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(center.x - r + 3f, center.y - 3f),
                size = Size((r - 3f) * 2, 6f)
            )
            // Center button ring & core
            drawCircle(Color(0xFF1E293B), radius = r * 0.28f, center = center)
            drawCircle(Color.White, radius = r * 0.20f, center = center)
            drawCircle(
                if (isCaptured) Color(0xFFF59E0B) else Color(0xFFEF4444),
                radius = r * 0.12f,
                center = center
            )
        }
    }
}
