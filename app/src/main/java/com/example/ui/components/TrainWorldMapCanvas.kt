package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RealmExpressAnimatedMap(
    selectedStationIndex: Int,
    onSelectStation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "map_anim")

    // Train movement along the track (0f to 1f loop)
    val trainProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "train_pos"
    )

    // Billowing steam puffs
    val steamPulse by infiniteTransition.animateFloat(
        initialValue = 2f,
        targetValue = 9f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseOutQuad),
            repeatMode = RepeatMode.Restart
        ),
        label = "steam_pulse"
    )

    // Ocean water wave shimmer
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    )

    // Crystal glow
    val crystalGlow by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "crystal_glow"
    )

    // Lighthouse beam angle
    val lighthouseAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "lighthouse_beam"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Ocean Base & animated waves
        drawOceanAndWaves(w, h, waveOffset)

        // 2. Island Coastline & Sandy Beaches
        drawIslandTerrain(w, h)

        // 3. Mountains & Waterfalls
        drawMountainsAndCaves(w, h, crystalGlow)

        // 4. Forests & Vegetation
        drawForestClusters(w, h)

        // 5. Winding Railway Tracks
        drawRailwayTracks(w, h)

        // 6. Landmark Destinations
        drawLandmarkIcons(w, h, selectedStationIndex, crystalGlow)

        // 7. Coastal Lighthouse
        drawLighthouse(w * 0.12f, h * 0.35f, lighthouseAngle)

        // 8. Scholars' Realm Express Train in Motion
        drawAnimatedTrain(w, h, trainProgress, steamPulse)
    }
}

private fun DrawScope.drawOceanAndWaves(w: Float, h: Float, waveOffset: Float) {
    // Deep to bright cyan coastal gradient
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF0284C7), Color(0xFF0369A1), Color(0xFF075985))
        ),
        size = Size(w, h)
    )

    // Waves ripple lines
    for (i in 0..10) {
        val y = h * (0.08f + i * 0.09f)
        val phase = waveOffset + i * 0.7f
        val xShift = sin(phase) * 8f
        drawLine(
            color = Color(0xFF38BDF8).copy(alpha = 0.35f),
            start = Offset(10f + xShift, y),
            end = Offset(w - 10f + xShift, y),
            strokeWidth = 2f
        )
    }
}

private fun DrawScope.drawIslandTerrain(w: Float, h: Float) {
    val sandColor = Color(0xFFFDE68A)
    val grassColor = Color(0xFF4ADE80)
    val darkGrass = Color(0xFF22C55E)

    // Beach sand base path
    val islandPath = Path().apply {
        moveTo(w * 0.18f, h * 0.20f)
        cubicTo(w * 0.35f, h * 0.08f, w * 0.75f, h * 0.06f, w * 0.92f, h * 0.25f)
        cubicTo(w * 0.98f, h * 0.45f, w * 0.94f, h * 0.75f, w * 0.85f, h * 0.88f)
        cubicTo(w * 0.65f, h * 0.96f, w * 0.35f, h * 0.92f, w * 0.15f, h * 0.78f)
        cubicTo(w * 0.05f, h * 0.55f, w * 0.08f, h * 0.30f, w * 0.18f, h * 0.20f)
        close()
    }

    // Outer sandy shore
    drawPath(path = islandPath, color = sandColor, style = Fill)
    drawPath(path = islandPath, color = Color(0xFFF59E0B), style = Stroke(width = 4f))

    // Inner lush green island plate
    val innerGrassPath = Path().apply {
        moveTo(w * 0.22f, h * 0.23f)
        cubicTo(w * 0.37f, h * 0.12f, w * 0.73f, h * 0.10f, w * 0.88f, h * 0.28f)
        cubicTo(w * 0.93f, h * 0.45f, w * 0.90f, h * 0.72f, w * 0.81f, h * 0.84f)
        cubicTo(w * 0.63f, h * 0.91f, w * 0.37f, h * 0.88f, w * 0.19f, h * 0.74f)
        cubicTo(w * 0.10f, h * 0.53f, w * 0.13f, h * 0.32f, w * 0.22f, h * 0.23f)
        close()
    }
    drawPath(path = innerGrassPath, color = grassColor, style = Fill)
    drawPath(path = innerGrassPath, color = darkGrass, style = Stroke(width = 3f))
}

private fun DrawScope.drawMountainsAndCaves(w: Float, h: Float, crystalGlow: Float) {
    // Snowy Northern Ridge
    val mountainColor = Color(0xFF64748B)
    val snowColor = Color(0xFFF8FAFC)

    // Mountain 1 (Top Center)
    val peakPath1 = Path().apply {
        moveTo(w * 0.40f, h * 0.18f)
        lineTo(w * 0.52f, h * 0.08f)
        lineTo(w * 0.64f, h * 0.18f)
        close()
    }
    drawPath(peakPath1, mountainColor)
    // Snow cap
    val snowCap1 = Path().apply {
        moveTo(w * 0.47f, h * 0.12f)
        lineTo(w * 0.52f, h * 0.08f)
        lineTo(w * 0.57f, h * 0.12f)
        close()
    }
    drawPath(snowCap1, snowColor)

    // Crystal Canyon Mountain (Upper Right) - Purple Rocky crags
    val crystalCaveColor = Color(0xFF6B21A8)
    drawCircle(crystalCaveColor, radius = 32f, center = Offset(w * 0.72f, h * 0.28f))
    drawCircle(Color(0xFF3B0764), radius = 18f, center = Offset(w * 0.72f, h * 0.29f)) // Cave entrance

    // Glowing Crystals
    val crystalColor = Color(0xFFC084FC).copy(alpha = crystalGlow)
    for (i in -1..1) {
        val cx = w * 0.72f + i * 22f
        val cy = h * 0.24f + (if (i == 0) -8f else 6f)
        drawCircle(crystalColor, radius = 6f, center = Offset(cx, cy))
        drawCircle(Color.White, radius = 2.5f, center = Offset(cx, cy))
    }
}

private fun DrawScope.drawForestClusters(w: Float, h: Float) {
    val treeDark = Color(0xFF15803D)
    val treeLight = Color(0xFF22C55E)

    val forestCenters = listOf(
        Offset(w * 0.28f, h * 0.35f),
        Offset(w * 0.32f, h * 0.38f),
        Offset(w * 0.26f, h * 0.42f),
        Offset(w * 0.42f, h * 0.65f),
        Offset(w * 0.46f, h * 0.68f),
        Offset(w * 0.50f, h * 0.64f),
        Offset(w * 0.78f, h * 0.62f),
        Offset(w * 0.82f, h * 0.66f)
    )

    for (pos in forestCenters) {
        drawCircle(treeDark, radius = 11f, center = pos)
        drawCircle(treeLight, radius = 8f, center = Offset(pos.x - 2f, pos.y - 2f))
        // Trunk
        drawRect(Color(0xFF78350F), topLeft = Offset(pos.x - 2f, pos.y + 7f), size = Size(4f, 6f))
    }
}

private fun DrawScope.drawRailwayTracks(w: Float, h: Float) {
    val railColor = Color(0xFFCBD5E1)
    val tieColor = Color(0xFF78350F)

    // Continuous loop track connecting all 5 stations:
    // P0: Castle (0.35, 0.48)
    // P1: Crystal Canyon (0.68, 0.32)
    // P2: Moon Temple (0.82, 0.45)
    // P3: Observatory (0.65, 0.78)
    // P4: Bridge/Coast (0.28, 0.72)
    val points = listOf(
        Offset(w * 0.35f, h * 0.48f),
        Offset(w * 0.52f, h * 0.38f),
        Offset(w * 0.68f, h * 0.32f),
        Offset(w * 0.78f, h * 0.42f),
        Offset(w * 0.82f, h * 0.55f),
        Offset(w * 0.74f, h * 0.70f),
        Offset(w * 0.62f, h * 0.78f),
        Offset(w * 0.44f, h * 0.80f),
        Offset(w * 0.28f, h * 0.72f),
        Offset(w * 0.24f, h * 0.58f),
        Offset(w * 0.35f, h * 0.48f)
    )

    // Draw wooden ties
    for (i in 0 until points.size - 1) {
        val pA = points[i]
        val pB = points[i + 1]
        for (step in 0..5) {
            val t = step / 5f
            val x = pA.x + (pB.x - pA.x) * t
            val y = pA.y + (pB.y - pA.y) * t
            drawCircle(tieColor, radius = 4f, center = Offset(x, y))
        }
    }

    // Steel rails (dual lines)
    val railPathA = Path().apply {
        moveTo(points[0].x, points[0].y - 3f)
        for (i in 1 until points.size) lineTo(points[i].x, points[i].y - 3f)
    }
    val railPathB = Path().apply {
        moveTo(points[0].x, points[0].y + 3f)
        for (i in 1 until points.size) lineTo(points[i].x, points[i].y + 3f)
    }
    drawPath(railPathA, railColor, style = Stroke(width = 2.5f))
    drawPath(railPathB, railColor, style = Stroke(width = 2.5f))

    // Scenic Arch Bridge over river chasm (between 0.28 and 0.44)
    val bridgeStart = Offset(w * 0.30f, h * 0.74f)
    val bridgeEnd = Offset(w * 0.42f, h * 0.78f)
    drawRect(Color(0xFF94A3B8), topLeft = Offset(bridgeStart.x - 2f, bridgeStart.y - 6f), size = Size(bridgeEnd.x - bridgeStart.x + 4f, 12f))
    drawRect(Color(0xFF475569), topLeft = Offset(bridgeStart.x, bridgeStart.y + 6f), size = Size(bridgeEnd.x - bridgeStart.x, 8f))
}

private fun DrawScope.drawLandmarkIcons(
    w: Float,
    h: Float,
    selectedIndex: Int,
    glow: Float
) {
    // 5 Stations
    val stations = listOf(
        Pair("Castle of Lexicon", Offset(w * 0.35f, h * 0.48f)),
        Pair("Crystal Canyon", Offset(w * 0.68f, h * 0.32f)),
        Pair("Moon Temple", Offset(w * 0.82f, h * 0.55f)),
        Pair("Starlight Observatory", Offset(w * 0.62f, h * 0.78f)),
        Pair("Chancellor's Summit", Offset(w * 0.28f, h * 0.72f))
    )

    stations.forEachIndexed { index, (name, pos) ->
        val isSelected = (index == selectedIndex)

        // Pulsing station ring
        if (isSelected) {
            drawCircle(
                color = Color(0xFFFDE047).copy(alpha = glow * 0.6f),
                radius = 24f,
                center = pos
            )
        }

        // Base disk
        drawCircle(Color(0xFF0F172A), radius = 14f, center = pos)
        drawCircle(
            color = if (isSelected) Color(0xFFFDE047) else Color(0xFF38BDF8),
            radius = 11f,
            center = pos
        )
        drawCircle(Color(0xFF1E1B4B), radius = 7f, center = pos)

        // Specific landmark icons
        when (index) {
            0 -> {
                // Castle turret
                drawRect(Color(0xFFEF4444), topLeft = Offset(pos.x - 5f, pos.y - 18f), size = Size(10f, 12f))
                drawCircle(Color(0xFFF59E0B), radius = 3f, center = Offset(pos.x, pos.y - 19f))
            }
            1 -> {
                // Crystal diamond
                val diamond = Path().apply {
                    moveTo(pos.x, pos.y - 20f)
                    lineTo(pos.x + 5f, pos.y - 14f)
                    lineTo(pos.x, pos.y - 8f)
                    lineTo(pos.x - 5f, pos.y - 14f)
                    close()
                }
                drawPath(diamond, Color(0xFFC084FC))
            }
            2 -> {
                // Moon crescent
                drawCircle(Color(0xFFFBBF24), radius = 6f, center = Offset(pos.x, pos.y - 14f))
                drawCircle(Color(0xFF1E1B4B), radius = 5f, center = Offset(pos.x + 3f, pos.y - 14f))
            }
            3 -> {
                // Observatory Dome
                drawCircle(Color(0xFF38BDF8), radius = 6f, center = Offset(pos.x, pos.y - 14f))
            }
            4 -> {
                // Summit Star
                drawCircle(Color(0xFFF59E0B), radius = 7f, center = Offset(pos.x, pos.y - 14f))
            }
        }
    }
}

private fun DrawScope.drawLighthouse(x: Float, y: Float, beamAngle: Float) {
    // Rocky islet
    drawCircle(Color(0xFF475569), radius = 16f, center = Offset(x, y))
    // Tower base
    drawRect(Color.White, topLeft = Offset(x - 5f, y - 24f), size = Size(10f, 24f))
    // Red stripes
    drawRect(Color(0xFFDC2626), topLeft = Offset(x - 5f, y - 18f), size = Size(10f, 6f))
    drawRect(Color(0xFFDC2626), topLeft = Offset(x - 5f, y - 6f), size = Size(10f, 6f))
    // Light lantern top
    drawCircle(Color(0xFFFDE047), radius = 4f, center = Offset(x, y - 26f))

    // Rotating sweeping light beam
    val beamRad = Math.toRadians(beamAngle.toDouble())
    val beamEndX = x + cos(beamRad).toFloat() * 50f
    val beamEndY = (y - 26f) + sin(beamRad).toFloat() * 25f
    drawLine(
        color = Color(0xFFFEF08A).copy(alpha = 0.45f),
        start = Offset(x, y - 26f),
        end = Offset(beamEndX, beamEndY),
        strokeWidth = 6f
    )
}

private fun DrawScope.drawAnimatedTrain(
    w: Float,
    h: Float,
    progress: Float,
    steamPulse: Float
) {
    // Interpolate train along path
    val trackPoints = listOf(
        Offset(w * 0.35f, h * 0.48f),
        Offset(w * 0.52f, h * 0.38f),
        Offset(w * 0.68f, h * 0.32f),
        Offset(w * 0.78f, h * 0.42f),
        Offset(w * 0.82f, h * 0.55f),
        Offset(w * 0.74f, h * 0.70f),
        Offset(w * 0.62f, h * 0.78f),
        Offset(w * 0.44f, h * 0.80f),
        Offset(w * 0.28f, h * 0.72f),
        Offset(w * 0.24f, h * 0.58f),
        Offset(w * 0.35f, h * 0.48f)
    )

    val totalSegments = trackPoints.size - 1
    val scaled = (progress * totalSegments).coerceIn(0f, totalSegments.toFloat())
    val segIndex = scaled.toInt().coerceIn(0, totalSegments - 1)
    val segFraction = scaled - segIndex

    val pA = trackPoints[segIndex]
    val pB = trackPoints[segIndex + 1]

    val trainX = pA.x + (pB.x - pA.x) * segFraction
    val trainY = pA.y + (pB.y - pA.y) * segFraction

    // Steam clouds rising from locomotive chimney
    for (i in 0..2) {
        val sOffset = (steamPulse + i * 5f) % 18f
        val steamX = trainX - sOffset * 0.8f
        val steamY = trainY - 14f - sOffset * 0.9f
        val radius = 4f + sOffset * 0.5f
        val alpha = (1f - sOffset / 18f).coerceIn(0f, 0.85f)
        drawCircle(Color.White.copy(alpha = alpha), radius = radius, center = Offset(steamX, steamY))
    }

    // Locomotive Engine (Red & Gold)
    drawRect(Color(0xFFDC2626), topLeft = Offset(trainX - 10f, trainY - 8f), size = Size(20f, 12f))
    drawRect(Color(0xFFF59E0B), topLeft = Offset(trainX - 10f, trainY - 9f), size = Size(20f, 2f)) // Gold roof trim
    // Chimney & Headlight
    drawRect(Color(0xFF1E293B), topLeft = Offset(trainX + 4f, trainY - 13f), size = Size(4f, 5f))
    drawCircle(Color(0xFFFDE047), radius = 2.5f, center = Offset(trainX + 10f, trainY - 2f)) // Golden lamp
    // Wheels
    drawCircle(Color(0xFF0F172A), radius = 3.5f, center = Offset(trainX - 5f, trainY + 5f))
    drawCircle(Color(0xFF0F172A), radius = 3.5f, center = Offset(trainX + 5f, trainY + 5f))

    // Passenger Car trailing behind
    val carX = trainX - 16f
    val carY = trainY
    drawRect(Color(0xFF2563EB), topLeft = Offset(carX - 8f, carY - 7f), size = Size(14f, 10f))
    drawRect(Color(0xFFFDE047), topLeft = Offset(carX - 8f, carY - 8f), size = Size(14f, 1.5f))
    drawRect(Color(0xFFBAE6FD), topLeft = Offset(carX - 5f, carY - 5f), size = Size(4f, 4f)) // Window
    drawCircle(Color(0xFF0F172A), radius = 3f, center = Offset(carX - 4f, carY + 4f))
    drawCircle(Color(0xFF0F172A), radius = 3f, center = Offset(carX + 2f, carY + 4f))
}
