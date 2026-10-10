package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class ScholarmonType(val displayName: String, val color: Color) {
    ALGEBRA("Algebra", DomainMathAlgebra),
    GEOMETRY("Geometry", DomainMathGeometry),
    ADVANCED_MATH("Adv. Math", DomainMathAdvanced),
    VOCABULARY("Vocab", DomainVerbalVocab),
    SYNTAX("Syntax", DomainVerbalSyntax),
    RHETORIC("Rhetoric", DomainVerbalRhetoric)
}

data class Move(
    val name: String,
    val type: ScholarmonType,
    val power: Int,
    val domain: SatDomain,
    val description: String
)

data class Scholarmon(
    val id: String,
    val speciesId: String,
    val name: String,
    val nickname: String = name,
    val type: ScholarmonType,
    val level: Int = 5,
    val currentHp: Int,
    val maxHp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int = 15,
    val exp: Int = 0,
    val maxExp: Int = 100,
    val isWild: Boolean = false,
    val isInParty: Boolean = false,
    val partySlot: Int = -1,
    val moves: List<Move> = emptyList(),
    val lore: String = "",
    val weakness: String = "",
    val resistance: String = "",
    val satTip: String = ""
) {
    val hpFraction: Float
        get() = (currentHp.toFloat() / maxHp.toFloat()).coerceIn(0f, 1f)

    val expFraction: Float
        get() = (exp.toFloat() / maxExp.toFloat()).coerceIn(0f, 1f)

    val isFainted: Boolean
        get() = currentHp <= 0
}

object ScholarmonSpecies {
    val ALGERAY = Scholarmon(
        id = "sp_algeray",
        speciesId = "algeray",
        name = "Algeray",
        type = ScholarmonType.ALGEBRA,
        level = 5,
        currentHp = 35,
        maxHp = 35,
        attack = 18,
        defense = 14,
        speed = 22,
        lore = "A swift electric manta ray that discharges linear equation bursts to solve system problems.",
        weakness = "System of Equations & Elimination",
        resistance = "Basic Arithmetic",
        satTip = "Check for scalar multiples in system equations to find infinite or zero solution cases quickly.",
        moves = listOf(
            Move("Slope Strike", ScholarmonType.ALGEBRA, 20, SatDomain.MATH, "Attacks with y=mx+b force."),
            Move("System Shock", ScholarmonType.ALGEBRA, 30, SatDomain.MATH, "Solves for x and y simultaneously.")
        )
    )

    val VOCABULARVA = Scholarmon(
        id = "sp_vocabularva",
        speciesId = "vocabularva",
        name = "Vocabula",
        type = ScholarmonType.VOCABULARY,
        level = 5,
        currentHp = 32,
        maxHp = 32,
        attack = 16,
        defense = 16,
        speed = 14,
        lore = "An inquisitive caterpillar adorned with lexicon markings. Evolves by digesting context clues.",
        weakness = "Pivot Words & Tone Contrasts",
        resistance = "Dictionary Definitions",
        satTip = "Focus on surrounding contrast transitions like 'although', 'nonetheless', and 'rather than' to pinpoint secondary meanings.",
        moves = listOf(
            Move("Context Jab", ScholarmonType.VOCABULARY, 18, SatDomain.VERBAL, "Strikes by analyzing surrounding prose."),
            Move("Lexicon Spore", ScholarmonType.VOCABULARY, 26, SatDomain.VERBAL, "Scatters SAT tone and connotation dust.")
        )
    )

    val PYTHAGORUFF = Scholarmon(
        id = "sp_pythagoruff",
        speciesId = "pythagoruff",
        name = "Pythagoruff",
        type = ScholarmonType.GEOMETRY,
        level = 6,
        currentHp = 40,
        maxHp = 40,
        attack = 20,
        defense = 22,
        speed = 16,
        lore = "A loyal pup with triangular armor plates. Its bark resonates at precisely 90-degree right angles.",
        weakness = "Special Right Triangles (30-60-90, 45-45-90)",
        resistance = "Perimeter calculations",
        satTip = "Memorize the 3-4-5 and 5-12-13 Pythagorean triples and remember sin(A) = cos(90°-A).",
        moves = listOf(
            Move("Hypotenuse Tackle", ScholarmonType.GEOMETRY, 22, SatDomain.MATH, "Calculates c = √(a² + b²) on impact."),
            Move("Right-Angle Guard", ScholarmonType.GEOMETRY, 28, SatDomain.MATH, "Defends using congruent proofs.")
        )
    )

    val SYNTAXPIDER = Scholarmon(
        id = "sp_syntaxpider",
        speciesId = "syntaxpider",
        name = "Syntaxpider",
        type = ScholarmonType.SYNTAX,
        level = 6,
        currentHp = 34,
        maxHp = 34,
        attack = 21,
        defense = 15,
        speed = 20,
        lore = "Spins intricate webs of dependent and independent clauses. Fixes run-on sentences in a snap.",
        weakness = "Semicolons & FANBOYS Conjunctions",
        resistance = "Parenthetical Dashes",
        satTip = "A semicolon can only join two complete sentences (independent clauses) without a coordinating conjunction.",
        moves = listOf(
            Move("Semicolon Snare", ScholarmonType.SYNTAX, 22, SatDomain.VERBAL, "Separates clauses without a conjunction."),
            Move("Comma Splice Slash", ScholarmonType.SYNTAX, 28, SatDomain.VERBAL, "Cuts away faulty punctuation errors.")
        )
    )

    val PARABOLON = Scholarmon(
        id = "sp_parabolon",
        speciesId = "parabolon",
        name = "Parabolon",
        type = ScholarmonType.ADVANCED_MATH,
        level = 7,
        currentHp = 38,
        maxHp = 38,
        attack = 24,
        defense = 18,
        speed = 18,
        lore = "A cosmic vertex creature that arcs gracefully through quadratic orbits and roots.",
        weakness = "Vertex Form: y = a(x - h)² + k",
        resistance = "Standard Form Factoring",
        satTip = "The vertex x-coordinate is always -b / (2a), and the axis of symmetry splits the two x-intercepts evenly.",
        moves = listOf(
            Move("Vertex Blast", ScholarmonType.ADVANCED_MATH, 25, SatDomain.MATH, "Hits at the peak of the quadratic curve."),
            Move("Quadratic Surge", ScholarmonType.ADVANCED_MATH, 32, SatDomain.MATH, "Unleashes the full ±√(b² - 4ac) force.")
        )
    )

    val THESAURON = Scholarmon(
        id = "sp_thesauron",
        speciesId = "thesauron",
        name = "Thesauron",
        type = ScholarmonType.RHETORIC,
        level = 8,
        currentHp = 42,
        maxHp = 42,
        attack = 26,
        defense = 20,
        speed = 21,
        lore = "A majestic winged dragon that commands transitions, logical contrasts, and rhetorical emphasis.",
        weakness = "Cause-and-Effect Transitions ('Consequently', 'Moreover')",
        resistance = "Chronological sequences",
        satTip = "Determine if the relationship is contrast ('However'), addition ('Furthermore'), or result ('Therefore') before reading choices.",
        moves = listOf(
            Move("Pivot Flame", ScholarmonType.RHETORIC, 26, SatDomain.VERBAL, "Shifts perspective using 'However' and 'Conversely'."),
            Move("Evidence Roar", ScholarmonType.RHETORIC, 35, SatDomain.VERBAL, "Summons textual evidence from lines 40-52.")
        )
    )

    val HYPOTENOTTER = Scholarmon(
        id = "sp_hypotenotter",
        speciesId = "hypotenotter",
        name = "Hypotenotter",
        type = ScholarmonType.GEOMETRY,
        level = 6,
        currentHp = 36,
        maxHp = 36,
        attack = 19,
        defense = 17,
        speed = 19,
        lore = "Swims gracefully in circular radian arcs. Uses πr² to measure its feeding ponds.",
        weakness = "Arc Length & Radians: s = r · θ",
        resistance = "Diameter calculations",
        satTip = "Remember π radians equals 180 degrees. To convert degrees to radians, multiply by π / 180.",
        moves = listOf(
            Move("Radian Wave", ScholarmonType.GEOMETRY, 22, SatDomain.MATH, "Converts 180° into π radians on rush."),
            Move("Chord Slice", ScholarmonType.GEOMETRY, 28, SatDomain.MATH, "Slices across circles with tangent precision.")
        )
    )

    val LOGARITHMOTH = Scholarmon(
        id = "sp_logarithmoth",
        speciesId = "logarithmoth",
        name = "Logarithmoth",
        type = ScholarmonType.ADVANCED_MATH,
        level = 7,
        currentHp = 33,
        maxHp = 33,
        attack = 27,
        defense = 14,
        speed = 25,
        lore = "Fluttering moth attracted to exponential lights. Flattens exponential curves into logarithms.",
        weakness = "Exponent Rules: (xᵃ)ᵇ = xᵃᵇ and xᵃ · xᵇ = xᵃ⁺ᵇ",
        resistance = "Linear equations",
        satTip = "When solving exponential equations with equal bases, you can equate their exponents directly.",
        moves = listOf(
            Move("Exponent Gust", ScholarmonType.ADVANCED_MATH, 24, SatDomain.MATH, "Scales damage with exponential growth."),
            Move("Natural Log Powder", ScholarmonType.ADVANCED_MATH, 30, SatDomain.MATH, "Calms chaos with base e Euler balance.")
        )
    )

    val allSpecies = listOf(
        ALGERAY, VOCABULARVA, PYTHAGORUFF, SYNTAXPIDER, PARABOLON, THESAURON, HYPOTENOTTER, LOGARITHMOTH
    )
}
