package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_progress")
data class PlayerProgressEntity(
    @PrimaryKey val id: Int = 1,
    val trainerName: String = "Red Scholar",
    val trainerLevel: Int = 1,
    val totalBattles: Int = 0,
    val battlesWon: Int = 0,
    val creaturesCaught: Int = 0,
    val scholarBalls: Int = 12,
    val potions: Int = 6,
    val formulaScrolls: Int = 4,
    val questionsAnswered: Int = 0,
    val questionsCorrect: Int = 0,
    val mathTotal: Int = 0,
    val mathCorrect: Int = 0,
    val verbalTotal: Int = 0,
    val verbalCorrect: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val unlockedBadges: String = "" // Comma-separated badge IDs
)
