package com.emberwingindustriesllc.satgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emberwingindustriesllc.satgame.audio.RetroAudioEngine
import com.emberwingindustriesllc.satgame.ui.components.HandheldConsoleWrapper
import com.emberwingindustriesllc.satgame.ui.screens.BattleScreen
import com.emberwingindustriesllc.satgame.ui.screens.MasteryScreen
import com.emberwingindustriesllc.satgame.ui.screens.OverworldMapScreen
import com.emberwingindustriesllc.satgame.ui.screens.PartyScreen
import com.emberwingindustriesllc.satgame.ui.theme.MyApplicationTheme
import com.emberwingindustriesllc.satgame.ui.theme.PixelFontFamily
import com.emberwingindustriesllc.satgame.ui.viewmodel.AppScreen
import com.emberwingindustriesllc.satgame.ui.viewmodel.ScholarViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ScholarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: ScholarViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isHandheldMode by viewModel.isHandheldMode.collectAsState()

    // Handle back button
    BackHandler(enabled = currentScreen != AppScreen.OVERWORLD) {
        viewModel.navigateTo(AppScreen.OVERWORLD)
    }

    HandheldConsoleWrapper(
        isHandheldMode = isHandheldMode,
        onToggleMode = { viewModel.toggleHandheldMode() },
        onDpadPress = { dir -> viewModel.handleDpad(dir) },
        onButtonAPress = { viewModel.handleButtonA() },
        onButtonBPress = { viewModel.handleButtonB() }
    ) {
        Scaffold(
            bottomBar = {
                RetroBottomNavigationBar(
                    currentScreen = currentScreen,
                    isHandheldMode = isHandheldMode,
                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                    onToggleHandheld = { viewModel.toggleHandheldMode() }
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    AppScreen.BATTLE -> BattleScreen(viewModel = viewModel)
                    AppScreen.OVERWORLD -> OverworldMapScreen(viewModel = viewModel)
                    AppScreen.PARTY -> PartyScreen(viewModel = viewModel)
                    AppScreen.MASTERY -> MasteryScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun RetroBottomNavigationBar(
    currentScreen: AppScreen,
    isHandheldMode: Boolean,
    onNavigate: (AppScreen) -> Unit,
    onToggleHandheld: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF110E1D))
            .border(width = 1.dp, color = Color(0xFF2D264F))
            .navigationBarsPadding()
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        RetroNavTab(
            title = "BATTLE",
            icon = Icons.Default.FlashOn,
            isSelected = currentScreen == AppScreen.BATTLE,
            onClick = { onNavigate(AppScreen.BATTLE) },
            testTag = "nav_battle_tab"
        )
        RetroNavTab(
            title = "WORLD",
            icon = Icons.Default.Explore,
            isSelected = currentScreen == AppScreen.OVERWORLD,
            onClick = { onNavigate(AppScreen.OVERWORLD) },
            testTag = "nav_world_tab"
        )
        RetroNavTab(
            title = "TEAM",
            icon = Icons.Default.CatchingPokemon,
            isSelected = currentScreen == AppScreen.PARTY,
            onClick = { onNavigate(AppScreen.PARTY) },
            testTag = "nav_party_tab"
        )
        RetroNavTab(
            title = "SAT STATS",
            icon = Icons.Default.Analytics,
            isSelected = currentScreen == AppScreen.MASTERY,
            onClick = { onNavigate(AppScreen.MASTERY) },
            testTag = "nav_stats_tab"
        )
        RetroNavTab(
            title = if (isHandheldMode) "FULL" else "GBA",
            icon = Icons.Default.VideogameAsset,
            isSelected = isHandheldMode,
            onClick = { onToggleHandheld() },
            testTag = "nav_toggle_gba_tab"
        )
        RetroNavTab(
            title = if (RetroAudioEngine.musicEnabled) "BGM ♫" else "MUTED",
            icon = if (RetroAudioEngine.musicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
            isSelected = RetroAudioEngine.musicEnabled,
            onClick = {
                RetroAudioEngine.musicEnabled = !RetroAudioEngine.musicEnabled
            },
            testTag = "nav_toggle_bgm_tab"
        )
    }
}

@Composable
private fun RetroNavTab(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val activeColor = Color(0xFFFDE047)
    val inactiveColor = Color(0xFF94A3B8)

    Box(
        modifier = Modifier
            .testTag(testTag)
            .shadow(if (isSelected) 2.dp else 0.dp, RoundedCornerShape(6.dp))
            .background(
                if (isSelected) Color(0xFF28205C) else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .clickable {
                RetroAudioEngine.playSelect()
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) activeColor else inactiveColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = PixelFontFamily,
                    fontSize = 7.sp,
                    color = if (isSelected) activeColor else inactiveColor
                )
            )
        }
    }
}
