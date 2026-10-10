package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Scholarmon
import com.example.data.model.ScholarmonSpecies
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.ScholarViewModel

@Composable
fun PartyScreen(
    viewModel: ScholarViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Party, 1: ScholarDex
    val partyCreatures by viewModel.partyCreatures.collectAsState()
    val allCreatures by viewModel.allCreatures.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        // Tab Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1B4B))
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (selectedTab == 0) Color(0xFF4338CA) else Color(0xFF1E293B),
                        RoundedCornerShape(6.dp)
                    )
                    .border(
                        1.5.dp,
                        if (selectedTab == 0) Color(0xFF818CF8) else Color(0xFF334155),
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { selectedTab = 0 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ACTIVE TEAM (${partyCreatures.size})",
                    fontFamily = PixelFontFamily,
                    fontSize = 9.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (selectedTab == 1) Color(0xFF4338CA) else Color(0xFF1E293B),
                        RoundedCornerShape(6.dp)
                    )
                    .border(
                        1.5.dp,
                        if (selectedTab == 1) Color(0xFF818CF8) else Color(0xFF334155),
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { selectedTab = 1 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SCHOLARDEX",
                    fontFamily = PixelFontFamily,
                    fontSize = 9.sp,
                    color = Color.White
                )
            }
        }

        if (selectedTab == 0) {
            // Party Team List
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(partyCreatures.size) { index ->
                    val monster = partyCreatures[index]
                    PartyMonsterDetailCard(
                        monster = monster,
                        onUsePotion = { viewModel.usePotion() }
                    )
                }
            }
        } else {
            // ScholarDex Catalog
            val caughtSpeciesIds = allCreatures.map { it.speciesId }.toSet()
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(ScholarmonSpecies.allSpecies.size) { index ->
                    val species = ScholarmonSpecies.allSpecies[index]
                    val isCaught = caughtSpeciesIds.contains(species.speciesId)
                    ScholarDexEntryCard(species = species, isCaught = isCaught)
                }
            }
        }
    }
}

@Composable
fun PartyMonsterDetailCard(
    monster: Scholarmon,
    onUsePotion: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(8.dp))
            .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
            .border(2.dp, Color(0xFF475569), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Animated Sprite
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
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${monster.nickname} (${monster.name})",
                        style = Typography.titleSmall.copy(
                            fontFamily = PixelFontFamily,
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        text = "LV.${monster.level}",
                        style = Typography.labelSmall.copy(
                            fontFamily = PixelFontFamily,
                            color = Color(0xFFFDE047),
                            fontSize = 9.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))
                TypeBadge(type = monster.type)
                Spacer(modifier = Modifier.height(4.dp))

                RetroHpBar(currentHp = monster.currentHp, maxHp = monster.maxHp)
                Spacer(modifier = Modifier.height(3.dp))
                RetroExpBar(currentExp = monster.exp, maxExp = monster.maxExp)

                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "ATK: ${monster.attack}",
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFFF87171)
                    )
                    Text(
                        text = "DEF: ${monster.defense}",
                        fontFamily = MonoFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFF60A5FA)
                    )
                }
            }
        }
    }
}

@Composable
fun ScholarDexEntryCard(
    species: Scholarmon,
    isCaught: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(8.dp))
            .background(if (isCaught) Color(0xFF1E293B) else Color(0xFF111827), RoundedCornerShape(8.dp))
            .border(
                1.5.dp,
                if (isCaught) Color(0xFF22C55E) else Color(0xFF374151),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFF090D16), RoundedCornerShape(6.dp))
                    .border(1.dp, Color(0xFF1F2937), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isCaught) {
                    MonsterPixelSprite(
                        speciesId = species.speciesId,
                        isBackSprite = false,
                        modifier = Modifier.size(56.dp)
                    )
                } else {
                    Text(
                        text = "?",
                        fontFamily = PixelFontFamily,
                        fontSize = 24.sp,
                        color = Color(0xFF475569)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isCaught) species.name else "???",
                        fontFamily = PixelFontFamily,
                        fontSize = 11.sp,
                        color = if (isCaught) Color.White else Color(0xFF94A3B8)
                    )
                    Text(
                        text = if (isCaught) "CAUGHT ✓" else "UNSEEN",
                        fontFamily = PixelFontFamily,
                        fontSize = 7.sp,
                        color = if (isCaught) Color(0xFF4ADE80) else Color(0xFF64748B)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                TypeBadge(type = species.type)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (isCaught) species.lore else "Explore wild Academia zones to discover and solve problems to catch this Scholarmon.",
                    style = Typography.bodySmall.copy(
                        color = if (isCaught) Color(0xFFCBD5E1) else Color(0xFF64748B),
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
