package com.emberwingindustriesllc.satgame.data.model

data class PlayerInventory(
    val scholarBalls: Int = 10,
    val potions: Int = 5,
    val formulaScrolls: Int = 3
)

data class ZoneLocation(
    val id: String,
    val name: String,
    val subtitle: String,
    val domainFocus: SatDomain,
    val difficultyTier: String,
    val wildSpeciesIds: List<String>,
    val badgeName: String,
    val iconEmoji: String,
    val minLevel: Int
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false
)

object AcademiaMap {
    val ZONES = listOf(
        ZoneLocation(
            id = "algebra_archipelago",
            name = "Algebra Archipelago",
            subtitle = "Linear equations & systems",
            domainFocus = SatDomain.MATH,
            difficultyTier = "Level 4 - 8",
            wildSpeciesIds = listOf("algeray", "parabolon"),
            badgeName = "Linear Prism Badge",
            iconEmoji = "x=y",
            minLevel = 1
        ),
        ZoneLocation(
            id = "verbal_vale",
            name = "Verbal Vale",
            subtitle = "Context clues & vocabulary",
            domainFocus = SatDomain.VERBAL,
            difficultyTier = "Level 5 - 9",
            wildSpeciesIds = listOf("vocabularva", "thesauron"),
            badgeName = "Lexicon Crest",
            iconEmoji = "ABC",
            minLevel = 2
        ),
        ZoneLocation(
            id = "geometric_gorge",
            name = "Geometric Gorge",
            subtitle = "Triangles, radians & circles",
            domainFocus = SatDomain.MATH,
            difficultyTier = "Level 6 - 10",
            wildSpeciesIds = listOf("pythagoruff", "hypotenotter"),
            badgeName = "Euler Compass",
            iconEmoji = "📐",
            minLevel = 3
        ),
        ZoneLocation(
            id = "syntactic_citadel",
            name = "Syntactic Citadel",
            subtitle = "Semicolons, clauses & transitions",
            domainFocus = SatDomain.VERBAL,
            difficultyTier = "Level 7 - 11",
            wildSpeciesIds = listOf("syntaxpider", "thesauron"),
            badgeName = "Punctuation Sigil",
            iconEmoji = ";",
            minLevel = 4
        ),
        ZoneLocation(
            id = "deans_tower",
            name = "Chancellor's Summit",
            subtitle = "Hard multi-domain SAT trials",
            domainFocus = SatDomain.MATH,
            difficultyTier = "Boss Level 10 - 15",
            wildSpeciesIds = listOf("parabolon", "logarithmoth", "thesauron"),
            badgeName = "Grand Scholar Trophy",
            iconEmoji = "🏛️",
            minLevel = 5
        )
    )
}
