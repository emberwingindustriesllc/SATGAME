package com.example.data.repository

import com.example.data.model.SATQuestion
import com.example.data.model.SatDifficulty
import com.example.data.model.SatDomain

object SATQuestionBank {

    val questions: List<SATQuestion> = listOf(
        // === SAT MATH: ALGEBRA & LINEAR EQUATIONS ===
        SATQuestion(
            id = "m_alg_01",
            domain = SatDomain.MATH,
            subtopic = "Linear Equations",
            prompt = "If 3x + 7 = 28, what is the value of 6x - 5?",
            formulaOrSnippet = "3x + 7 = 28",
            options = listOf("37", "42", "21", "35"),
            correctIndex = 0,
            explanation = "Step 1: Solve for x:\n3x = 28 - 7 = 21 -> x = 7.\nStep 2: Substitute x = 7 into 6x - 5:\n6(7) - 5 = 42 - 5 = 37.",
            hint = "First isolate x by subtracting 7, then substitute into 6x - 5.",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "m_alg_02",
            domain = SatDomain.MATH,
            subtopic = "Systems of Equations",
            prompt = "For which value of k does the system of equations have infinitely many solutions?\n2x - 4y = 8\n6x - ky = 24",
            formulaOrSnippet = "2x - 4y = 8\n6x - ky = 24",
            options = listOf("12", "-12", "6", "18"),
            correctIndex = 0,
            explanation = "For infinitely many solutions, the two linear equations must be scalar multiples of each other. Notice the second equation's x-coefficient is 6, which is 3 times the first equation's (3 * 2x = 6x). Multiplying the first equation by 3 gives: 6x - 12y = 24. Thus, k = 12.",
            hint = "Multiply the first equation by 3 to match the x-coefficients and constants.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "m_alg_03",
            domain = SatDomain.MATH,
            subtopic = "Linear Inequalities",
            prompt = "A tutor charges a flat registration fee of $35 plus $45 per hour. If a student's budget is at most $260, what is the maximum whole number of hours of tutoring they can purchase?",
            formulaOrSnippet = "35 + 45h ≤ 260",
            options = listOf("5 hours", "6 hours", "4 hours", "7 hours"),
            correctIndex = 0,
            explanation = "Set up the inequality: 35 + 45h ≤ 260.\nSubtract 35: 45h ≤ 225.\nDivide by 45: h ≤ 5.\nThe maximum whole number of hours is 5.",
            hint = "Subtract the flat fee first, then divide by the hourly rate.",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "m_alg_04",
            domain = SatDomain.MATH,
            subtopic = "Slope & Intercepts",
            prompt = "Line L passes through (2, -3) and (6, 5). What is the y-intercept of a line perpendicular to L that passes through (0, 4)?",
            formulaOrSnippet = "m_perp = -1 / m",
            options = listOf("4", "-1/2", "2", "-4"),
            correctIndex = 0,
            explanation = "Step 1: Slope of L = (5 - (-3)) / (6 - 2) = 8 / 4 = 2.\nStep 2: Perpendicular slope = -1/2.\nStep 3: The line passes through (0, 4), which by definition is on the y-axis, making its y-intercept exactly 4!",
            hint = "Any point with an x-coordinate of 0 represents the y-intercept directly.",
            difficulty = SatDifficulty.MEDIUM
        ),

        // === SAT MATH: ADVANCED MATH & QUADRATICS ===
        SATQuestion(
            id = "m_adv_01",
            domain = SatDomain.MATH,
            subtopic = "Quadratics & Roots",
            prompt = "What is the sum of the solutions to the equation x² - 8x + 12 = 0?",
            formulaOrSnippet = "Sum of roots = -b / a",
            options = listOf("8", "12", "-8", "4"),
            correctIndex = 0,
            explanation = "By Vieta's formulas, for ax² + bx + c = 0, the sum of roots is -b / a. Here, a = 1 and b = -8, so the sum is -(-8)/1 = 8. (Or factoring: (x-2)(x-6)=0 -> roots are 2 and 6; 2 + 6 = 8).",
            hint = "Recall Vieta's formula: sum of roots = -b/a.",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "m_adv_02",
            domain = SatDomain.MATH,
            subtopic = "Quadratic Vertex Form",
            prompt = "The graph of y = (x - 3)² - 16 intersects the x-axis at points (a, 0) and (b, 0). What is the distance between these two intercepts?",
            formulaOrSnippet = "y = (x - h)² + k",
            options = listOf("8", "16", "6", "10"),
            correctIndex = 0,
            explanation = "Set y = 0:\n(x - 3)² - 16 = 0 -> (x - 3)² = 16.\nTake square roots: x - 3 = ±4.\nSo x = 7 or x = -1.\nThe distance between (7, 0) and (-1, 0) is 7 - (-1) = 8.",
            hint = "Set y = 0, take the square root of both sides, and find the difference between the two x values.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "m_adv_03",
            domain = SatDomain.MATH,
            subtopic = "Discriminant & Solutions",
            prompt = "For what value of c does the quadratic equation 4x² - 12x + c = 0 have exactly one real solution?",
            formulaOrSnippet = "Discriminant: Δ = b² - 4ac",
            options = listOf("9", "36", "3", "12"),
            correctIndex = 0,
            explanation = "A quadratic has exactly one real solution when its discriminant b² - 4ac = 0.\nHere, a = 4, b = -12.\n(-12)² - 4(4)(c) = 0\n144 - 16c = 0 -> 16c = 144 -> c = 9.",
            hint = "Set b² - 4ac = 0 for exactly one real root.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "m_adv_04",
            domain = SatDomain.MATH,
            subtopic = "Exponents & Radicals",
            prompt = "If 2^(3x - 1) = 32, what is the value of x?",
            formulaOrSnippet = "32 = 2⁵",
            options = listOf("2", "3", "5/3", "4"),
            correctIndex = 0,
            explanation = "Rewrite 32 as a power of 2: 32 = 2⁵.\nSince the bases match, equate exponents: 3x - 1 = 5.\n3x = 6 -> x = 2.",
            hint = "Express 32 as 2 to what power?",
            difficulty = SatDifficulty.EASY
        ),

        // === SAT MATH: GEOMETRY & TRIGONOMETRY ===
        SATQuestion(
            id = "m_geom_01",
            domain = SatDomain.MATH,
            subtopic = "Right Triangles",
            prompt = "In right triangle ABC with right angle at C, if sin(A) = 3/5, what is the value of cos(B)?",
            formulaOrSnippet = "Complementary angle theorem: sin(A) = cos(90° - A)",
            options = listOf("3/5", "4/5", "3/4", "4/3"),
            correctIndex = 0,
            explanation = "In any right triangle, the acute angles A and B are complementary (A + B = 90°). By trigonometric identity, sin(A) = cos(B). Therefore, cos(B) = 3/5.",
            hint = "Recall that for complementary acute angles in a right triangle, sin(A) = cos(B).",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "m_geom_02",
            domain = SatDomain.MATH,
            subtopic = "Circle Equations",
            prompt = "A circle in the xy-plane has equation (x + 4)² + (y - 7)² = 36. What is the area of this circle?",
            formulaOrSnippet = "(x - h)² + (y - k)² = r²",
            options = listOf("36π", "6π", "12π", "72π"),
            correctIndex = 0,
            explanation = "In the standard circle formula (x - h)² + (y - k)² = r², r² = 36. The area of a circle is A = πr². Since r² = 36, Area = 36π.",
            hint = "Area of a circle is πr². Notice the equation directly provides r² on the right-hand side.",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "m_geom_03",
            domain = SatDomain.MATH,
            subtopic = "Radians & Arc Length",
            prompt = "A circle has a radius of 6 cm. What is the length of an arc intercepted by a central angle of 2π/3 radians?",
            formulaOrSnippet = "s = r · θ",
            options = listOf("4π cm", "6π cm", "2π cm", "12π cm"),
            correctIndex = 0,
            explanation = "Arc length formula with angle in radians is s = r · θ.\nHere r = 6 and θ = 2π/3.\ns = 6 · (2π / 3) = 12π / 3 = 4π cm.",
            hint = "Use arc length formula: s = radius × angle in radians.",
            difficulty = SatDifficulty.MEDIUM
        ),

        // === SAT READING & WRITING: WORDS IN CONTEXT ===
        SATQuestion(
            id = "v_voc_01",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "Although the scientist's initial hypothesis was met with widespread skepticism, subsequent rigorous trials produced evidence that made her findings entirely ______ to even her harshest critics.",
            prompt = "Which choice completes the text with the most logical and precise word?",
            options = listOf("irrefutable", "superficial", "contentious", "ephemeral"),
            correctIndex = 0,
            explanation = "'Irrefutable' means impossible to deny or disprove. The contrast word 'Although' and the progression from skepticism to convincing evidence show the critics could no longer dispute the findings.",
            hint = "Look for a word that contrasts with 'skepticism' and means impossible to disprove.",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "v_voc_02",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "Unlike her predecessor, who preferred vague generalizations, the new city planner approached zoning ordinances with ______ attention to historical records and infrastructure blueprints.",
            prompt = "Which choice completes the text with the most logical and precise word?",
            options = listOf("scrupulous", "indifferent", "arbitrary", "sporadic"),
            correctIndex = 0,
            explanation = "'Scrupulous' means diligent, thorough, and attentive to details. The contrast 'Unlike her predecessor, who preferred vague generalizations' demands an adjective emphasizing meticulous precision.",
            hint = "Contrasts with 'vague generalizations' — means extremely thorough.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "v_voc_03",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "The poet's verse was celebrated for its ______ clarity; listeners with no background in literature immediately grasped its emotional resonance.",
            prompt = "Which choice completes the text with the most logical and precise word?",
            options = listOf("lucid", "cryptic", "convoluted", "pedantic"),
            correctIndex = 0,
            explanation = "'Lucid' means expressed clearly and easy to understand. Since listeners immediately grasped the poem's meaning, 'lucid' perfectly complements clarity.",
            hint = "Which word means clear and easy to understand?",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "v_voc_04",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "Despite suffering repeated setbacks during the arctic expedition, the crew remained ______, refusing to abandon their mission until the survey was complete.",
            prompt = "Which choice completes the text with the most logical and precise word?",
            options = listOf("tenacious", "complacent", "vacillating", "aprehensive"),
            correctIndex = 0,
            explanation = "'Tenacious' means persistent and determined. 'Despite setbacks' and 'refusing to abandon' shows unwavering determination.",
            hint = "Look for an adjective meaning stubbornly persistent or determined.",
            difficulty = SatDifficulty.MEDIUM
        ),

        // === SAT READING & WRITING: BOUNDARIES & PUNCTUATION ===
        SATQuestion(
            id = "v_punc_01",
            domain = SatDomain.VERBAL,
            subtopic = "Punctuation & Clauses",
            contextPassage = "During the Renaissance, botanists cataloged hundreds of newly discovered medicinal herbs ______ however, many of their classifications relied on folklore rather than rigorous observation.",
            prompt = "Which choice completes the text so that it conforms to the conventions of Standard Written English?",
            options = listOf("; however,", ", however,", " however;", ". However"),
            correctIndex = 0,
            explanation = "Two independent clauses joined by a conjunctive adverb ('however') must be punctuated with a semicolon before the conjunctive adverb and a comma after it: '; however,'. A comma alone creates a comma splice.",
            hint = "Joining two complete independent sentences with 'however' requires a semicolon before and comma after.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "v_punc_02",
            domain = SatDomain.VERBAL,
            subtopic = "Colon Usage",
            contextPassage = "Architect Maya Lin's design for the memorial emphasized a single unifying principle ______ the monument should invite personal reflection rather than celebrate militaristic triumph.",
            prompt = "Which choice completes the text so that it conforms to the conventions of Standard Written English?",
            options = listOf(": the", "; the", ", the", " the"),
            correctIndex = 0,
            explanation = "A colon (:) must follow an independent clause and introduces an explanation, elaboration, or definition of what was just named ('a single unifying principle').",
            hint = "A colon is used after a complete sentence to introduce an explanation or elaboration.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "v_punc_03",
            domain = SatDomain.VERBAL,
            subtopic = "Nonessential Elements",
            contextPassage = "Marie Curie ______ who conducted pioneering research on radioactivity, remains the only person to win Nobel Prizes in two distinct scientific fields.",
            prompt = "Which choice completes the text so that it conforms to the conventions of Standard Written English?",
            options = listOf(", ", "—", " ;", " ("),
            correctIndex = 0,
            explanation = "The relative clause 'who conducted pioneering research on radioactivity' is nonessential and ends with a comma. It must therefore begin with a matching comma.",
            hint = "Nonessential interrupting clauses must be enclosed in a pair of commas.",
            difficulty = SatDifficulty.EASY
        ),

        // === SAT READING & WRITING: TRANSITIONS ===
        SATQuestion(
            id = "v_trans_01",
            domain = SatDomain.VERBAL,
            subtopic = "Transitions",
            contextPassage = "Solar panels generate peak electrical output during clear summer afternoons when sunlight is most direct. ______, residential energy consumption typically surges in the early evening as families return home and turn on appliances.",
            prompt = "Which choice completes the text with the most logical transition?",
            options = listOf("However", "Furthermore", "Consequently", "Specifically"),
            correctIndex = 0,
            explanation = "'However' is correct because there is a contrast between when solar energy is generated (afternoon) and when energy demand peaks (evening). This mismatch illustrates a challenge in solar grid storage.",
            hint = "Compare the timing: peak generation is afternoon vs peak demand is evening. Is this an agreement or a contrast?",
            difficulty = SatDifficulty.EASY
        ),
        SATQuestion(
            id = "v_trans_02",
            domain = SatDomain.VERBAL,
            subtopic = "Transitions",
            contextPassage = "Deep-sea anglerfish live in complete darkness where visual cues are nonexistent. ______, they rely on bioluminescent lures and sensitive lateral line organs to detect nearby prey.",
            prompt = "Which choice completes the text with the most logical transition?",
            options = listOf("Consequently", "Conversely", "Meanwhile", "Nonetheless"),
            correctIndex = 0,
            explanation = "'Consequently' indicates a cause-and-effect relationship. Because they live in total darkness without visual cues, as a result (consequently), they rely on bioluminescence and sensory organs.",
            hint = "The second sentence explains the biological adaptation that resulted from the darkness.",
            difficulty = SatDifficulty.MEDIUM
        ),
        SATQuestion(
            id = "v_trans_03",
            domain = SatDomain.VERBAL,
            subtopic = "Transitions",
            contextPassage = "Some educators argue that open-notes exams fail to measure student retention. Supporters counter that such assessments test analytical reasoning; ______, they argue that open-notes exams better mirror real-world professional environments where resources are readily accessible.",
            prompt = "Which choice completes the text with the most logical transition?",
            options = listOf("moreover", "in contrast", "instead", "finally"),
            correctIndex = 0,
            explanation = "'Moreover' adds an additional supporting argument to the same side of the debate (both points defend open-notes exams).",
            hint = "The second argument reinforces the first argument in support of open-notes testing.",
            difficulty = SatDifficulty.MEDIUM
        )
    )

    fun getRandomQuestion(domain: SatDomain? = null, subtopic: String? = null): SATQuestion {
        val pool = questions.filter { q ->
            (domain == null || q.domain == domain) &&
            (subtopic == null || q.subtopic.contains(subtopic, ignoreCase = true))
        }
        return pool.randomOrNull() ?: questions.random()
    }
}
