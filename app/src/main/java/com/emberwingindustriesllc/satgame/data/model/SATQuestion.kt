package com.emberwingindustriesllc.satgame.data.model

enum class SatDomain(val label: String) {
    MATH("Math"),
    VERBAL("Reading & Writing")
}

enum class SatDifficulty {
    EASY, MEDIUM, HARD
}

data class SATQuestion(
    val id: String,
    val domain: SatDomain,
    val subtopic: String,
    val prompt: String,
    val contextPassage: String? = null,
    val formulaOrSnippet: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val hint: String,
    val difficulty: SatDifficulty = SatDifficulty.MEDIUM
)
