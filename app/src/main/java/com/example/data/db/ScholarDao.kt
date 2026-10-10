package com.example.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScholarDao {
    @Query("SELECT * FROM scholarmon ORDER BY partySlot ASC, level DESC")
    fun getAllScholarmon(): Flow<List<ScholarmonEntity>>

    @Query("SELECT * FROM scholarmon WHERE isInParty = 1 ORDER BY partySlot ASC")
    fun getParty(): Flow<List<ScholarmonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScholarmon(scholarmon: ScholarmonEntity)

    @Update
    suspend fun updateScholarmon(scholarmon: ScholarmonEntity)

    @Query("UPDATE scholarmon SET currentHp = :currentHp, exp = :exp, level = :level WHERE id = :id")
    suspend fun updateBattleStats(id: String, currentHp: Int, exp: Int, level: Int)

    @Query("DELETE FROM scholarmon WHERE id = :id")
    suspend fun deleteScholarmon(id: String)

    @Query("SELECT * FROM player_progress WHERE id = 1 LIMIT 1")
    fun getPlayerProgress(): Flow<PlayerProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerProgress(progress: PlayerProgressEntity)

    @Update
    suspend fun updatePlayerProgress(progress: PlayerProgressEntity)
}
