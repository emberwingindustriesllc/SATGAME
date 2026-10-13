package com.emberwingindustriesllc.satgame.data.repository

import com.emberwingindustriesllc.satgame.data.db.PlayerProgressEntity
import com.emberwingindustriesllc.satgame.data.db.ScholarDao
import com.emberwingindustriesllc.satgame.data.db.ScholarmonEntity
import com.emberwingindustriesllc.satgame.data.model.Scholarmon
import com.emberwingindustriesllc.satgame.data.model.ScholarmonSpecies
import com.emberwingindustriesllc.satgame.data.model.ScholarmonType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class ScholarRepository(private val dao: ScholarDao) {

    val allCreatures: Flow<List<Scholarmon>> = dao.getAllScholarmon().map { entities ->
        entities.map { it.toDomain() }
    }

    val partyCreatures: Flow<List<Scholarmon>> = dao.getParty().map { entities ->
        entities.map { it.toDomain() }
    }

    val playerProgress: Flow<PlayerProgressEntity> = dao.getPlayerProgress().map { entity ->
        entity ?: PlayerProgressEntity()
    }

    suspend fun ensureInitialData() {
        // If no player progress, insert starter profile
        dao.insertPlayerProgress(
            PlayerProgressEntity(
                id = 1,
                trainerName = "Scholar Alex",
                trainerLevel = 1,
                scholarBalls = 10,
                potions = 5,
                formulaScrolls = 3
            )
        )

        // Seed initial starter Scholarmon if none exists
        val starterAlgeray = ScholarmonEntity(
            id = "starter_algeray_01",
            speciesId = "algeray",
            name = "Algeray",
            nickname = "SparkX",
            typeName = ScholarmonType.ALGEBRA.name,
            level = 5,
            currentHp = 35,
            maxHp = 35,
            attack = 18,
            defense = 14,
            exp = 20,
            maxExp = 100,
            isInParty = true,
            partySlot = 0
        )
        dao.insertScholarmon(starterAlgeray)

        val starterVocab = ScholarmonEntity(
            id = "starter_vocab_02",
            speciesId = "vocabularva",
            name = "Vocabula",
            nickname = "Lexi",
            typeName = ScholarmonType.VOCABULARY.name,
            level = 5,
            currentHp = 32,
            maxHp = 32,
            attack = 16,
            defense = 16,
            exp = 10,
            maxExp = 100,
            isInParty = true,
            partySlot = 1
        )
        dao.insertScholarmon(starterVocab)
    }

    suspend fun saveCapturedMonster(species: Scholarmon, nickname: String = species.name): Scholarmon {
        val newId = "caught_" + UUID.randomUUID().toString().take(8)
        val entity = ScholarmonEntity(
            id = newId,
            speciesId = species.speciesId,
            name = species.name,
            nickname = nickname,
            typeName = species.type.name,
            level = species.level,
            currentHp = species.maxHp,
            maxHp = species.maxHp,
            attack = species.attack,
            defense = species.defense,
            exp = 0,
            maxExp = 100,
            isInParty = true,
            partySlot = 2,
            isWild = false
        )
        dao.insertScholarmon(entity)
        return entity.toDomain()
    }

    suspend fun updateScholarmonHpAndExp(id: String, newHp: Int, newExp: Int, newLevel: Int) {
        dao.updateBattleStats(id, newHp, newExp, newLevel)
    }

    suspend fun healAllParty() {
        val creatures = dao.getParty().first()
        creatures.forEach { entity ->
            val species = ScholarmonSpecies.allSpecies.firstOrNull { it.speciesId == entity.speciesId }
            val maxHp = species?.maxHp ?: entity.maxHp
            dao.updateBattleStats(entity.id, maxHp, entity.exp, entity.level)
        }
    }

    suspend fun updateProgress(entity: PlayerProgressEntity) {
        dao.updatePlayerProgress(entity)
    }

    private fun ScholarmonEntity.toDomain(): Scholarmon {
        val baseSpecies = ScholarmonSpecies.allSpecies.firstOrNull { it.speciesId == speciesId }
            ?: ScholarmonSpecies.ALGERAY
        val type = try {
            ScholarmonType.valueOf(typeName)
        } catch (_: Exception) {
            ScholarmonType.ALGEBRA
        }

        return Scholarmon(
            id = id,
            speciesId = speciesId,
            name = name,
            nickname = nickname,
            type = type,
            level = level,
            currentHp = currentHp,
            maxHp = maxHp,
            attack = attack,
            defense = defense,
            exp = exp,
            maxExp = maxExp,
            isWild = isWild,
            isInParty = isInParty,
            partySlot = partySlot,
            moves = baseSpecies.moves,
            lore = baseSpecies.lore,
            weakness = baseSpecies.weakness,
            resistance = baseSpecies.resistance,
            satTip = baseSpecies.satTip,
            speed = baseSpecies.speed
        )
    }
}
