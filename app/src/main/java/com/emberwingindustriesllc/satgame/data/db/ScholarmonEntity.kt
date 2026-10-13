package com.emberwingindustriesllc.satgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scholarmon")
data class ScholarmonEntity(
    @PrimaryKey val id: String,
    val speciesId: String,
    val name: String,
    val nickname: String,
    val typeName: String,
    val level: Int,
    val currentHp: Int,
    val maxHp: Int,
    val attack: Int,
    val defense: Int,
    val exp: Int,
    val maxExp: Int,
    val isInParty: Boolean,
    val partySlot: Int,
    val isWild: Boolean = false,
    val caughtTimestamp: Long = System.currentTimeMillis()
)
