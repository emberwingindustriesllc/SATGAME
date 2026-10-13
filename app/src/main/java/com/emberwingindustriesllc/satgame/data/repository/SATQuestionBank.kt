package com.emberwingindustriesllc.satgame.data.repository

import com.emberwingindustriesllc.satgame.data.model.SATQuestion
import com.emberwingindustriesllc.satgame.data.model.SatDifficulty
import com.emberwingindustriesllc.satgame.data.model.SatDomain

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
        ),
        // === NEW MATH: SYSTEMS OF EQUATIONS (Substitution) ===
        SATQuestion(
            id = "m_alg_05",
            domain = SatDomain.MATH,
            subtopic = "Systems of Equations",
            prompt = "If 2x + y = 10 and x - y = 2, what is the value of x?",
            formulaOrSnippet = "2x + y = 10\nx - y = 2",
            options = listOf("4", "6", "2", "8"),
            correctIndex = 0,
            explanation = "Add the two equations to eliminate y:\n(2x + y) + (x - y) = 10 + 2\n3x = 12\nx = 4.",
            hint = "Adding the two equations eliminates y, allowing you to solve for x directly.",
            difficulty = SatDifficulty.EASY
        ),
        // === NEW MATH: LINEAR INEQUALITIES ===
        SATQuestion(
            id = "m_alg_06",
            domain = SatDomain.MATH,
            subtopic = "Linear Inequalities",
            prompt = "If 5 - 3x > 14, which of the following represents the solution set?",
            formulaOrSnippet = "5 - 3x > 14",
            options = listOf("x < -3", "x > -3", "x < 3", "x > 3"),
            correctIndex = 0,
            explanation = "Subtract 5 from both sides: -3x > 9.\nDivide both sides by -3 (remember to flip the inequality sign when dividing by a negative number): x < -3.",
            hint = "When dividing or multiplying both sides of an inequality by a negative number, flip the inequality sign.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW MATH: SYSTEMS OF EQUATIONS (No Solution) ===
        SATQuestion(
            id = "m_alg_07",
            domain = SatDomain.MATH,
            subtopic = "Systems of Equations",
            prompt = "For which value of k does the following system of equations have no solution?\n2x + 3y = 7\n4x + ky = 10",
            formulaOrSnippet = "2x + 3y = 7\n4x + ky = 10",
            options = listOf("6", "3", "12", "4"),
            correctIndex = 0,
            explanation = "For a system to have no solution, the lines must be parallel (same slope, different y-intercepts).\nThe ratio of x-coefficients is 2/4 = 1/2.\nFor parallel lines: 3/k = 1/2, so k = 6.\nCheck: 2/4 = 3/6 = 1/2, but 7/10 \u2260 1/2. The lines are parallel and distinct, so there is no solution.",
            hint = "Parallel lines have proportional coefficients for x and y but a different constant ratio.",
            difficulty = SatDifficulty.HARD
        ),
        // === NEW MATH: QUADRATIC MAXIMUM ===
        SATQuestion(
            id = "m_adv_05",
            domain = SatDomain.MATH,
            subtopic = "Quadratic Functions",
            prompt = "A ball is thrown upward from a platform 5 feet high with an initial velocity of 64 feet per second. The height h (in feet) after t seconds is given by h = -16t\u00b2 + 64t + 5. What is the maximum height reached by the ball?",
            formulaOrSnippet = "h = -16t\u00b2 + 64t + 5",
            options = listOf("69 feet", "64 feet", "72 feet", "65 feet"),
            correctIndex = 0,
            explanation = "The maximum height occurs at the vertex of the parabola.\nt = -b/(2a) = -64/(2 \u00d7 -16) = -64/(-32) = 2 seconds.\nh(2) = -16(4) + 64(2) + 5 = -64 + 128 + 5 = 69 feet.",
            hint = "The maximum or minimum of a quadratic occurs at t = -b/(2a).",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW MATH: EXPONENTIAL GROWTH ===
        SATQuestion(
            id = "m_adv_06",
            domain = SatDomain.MATH,
            subtopic = "Exponential Growth",
            prompt = "A bacteria culture triples every 2 hours. If the culture starts with 100 bacteria, how many bacteria will there be after 6 hours?",
            formulaOrSnippet = "P(t) = 100 \u00d7 3^(t/2)",
            options = listOf("2,700", "900", "8,100", "300"),
            correctIndex = 0,
            explanation = "In 6 hours, there are 6/2 = 3 tripling periods.\nAfter 6 hours: 100 \u00d7 3\u00b3 = 100 \u00d7 27 = 2,700 bacteria.",
            hint = "Count the number of tripling periods in 6 hours, then multiply.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW MATH: QUADRATIC FACTORING ===
        SATQuestion(
            id = "m_adv_07",
            domain = SatDomain.MATH,
            subtopic = "Quadratic Functions",
            prompt = "If x\u00b2 - 9x + 20 = 0, what are the solutions?",
            formulaOrSnippet = "x\u00b2 - 9x + 20 = 0",
            options = listOf("x = 4 and x = 5", "x = -4 and x = -5", "x = 2 and x = 10", "x = -2 and x = -10"),
            correctIndex = 0,
            explanation = "Factor the quadratic: find two numbers that multiply to 20 and add to -9.\nThose numbers are -4 and -5.\n(x - 4)(x - 5) = 0, so x = 4 or x = 5.",
            hint = "Find two numbers that multiply to +20 and add to -9.",
            difficulty = SatDifficulty.EASY
        ),
        // === NEW MATH: CIRCLE CENTER ===
        SATQuestion(
            id = "m_geom_04",
            domain = SatDomain.MATH,
            subtopic = "Circle Equations",
            prompt = "The equation x\u00b2 + y\u00b2 - 6x + 8y = 0 represents a circle in the xy-plane. What are the coordinates of its center?",
            formulaOrSnippet = "x\u00b2 + y\u00b2 - 6x + 8y = 0",
            options = listOf("(3, -4)", "(-3, 4)", "(3, 4)", "(-3, -4)"),
            correctIndex = 0,
            explanation = "Complete the square for both x and y:\n(x\u00b2 - 6x + 9) + (y\u00b2 + 8y + 16) = 0 + 9 + 16\n(x - 3)\u00b2 + (y + 4)\u00b2 = 25\nThe center is (3, -4).",
            hint = "Complete the square for the x and y terms to find the center (h, k).",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW MATH: TRIGONOMETRY (Quadrant) ===
        SATQuestion(
            id = "m_geom_05",
            domain = SatDomain.MATH,
            subtopic = "Trigonometry",
            prompt = "If sin(\u03b8) = -3/5 and \u03b8 is an angle in the fourth quadrant, what is the value of cos(\u03b8)?",
            formulaOrSnippet = "sin\u00b2(\u03b8) + cos\u00b2(\u03b8) = 1",
            options = listOf("4/5", "-4/5", "3/5", "-3/5"),
            correctIndex = 0,
            explanation = "Use the Pythagorean identity: sin\u00b2(\u03b8) + cos\u00b2(\u03b8) = 1.\n(-3/5)\u00b2 + cos\u00b2(\u03b8) = 1\n9/25 + cos\u00b2(\u03b8) = 1\ncos\u00b2(\u03b8) = 16/25\ncos(\u03b8) = \u00b14/5.\nSince \u03b8 is in the fourth quadrant, where cosine is positive, cos(\u03b8) = 4/5.",
            hint = "In the fourth quadrant, sine is negative and cosine is positive.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW MATH: DATA ANALYSIS ===
        SATQuestion(
            id = "m_data_01",
            domain = SatDomain.MATH,
            subtopic = "Data Analysis",
            prompt = "A student's five test scores are: 78, 82, 85, 90, 94. What is the difference between the mean and the median of these scores?",
            formulaOrSnippet = "Mean = sum / count; Median = middle value",
            options = listOf("0.8", "0.5", "1.0", "0.2"),
            correctIndex = 0,
            explanation = "Mean = (78 + 82 + 85 + 90 + 94) / 5 = 429 / 5 = 85.8.\nMedian (middle value of ordered data) = 85.\nDifference = 85.8 - 85 = 0.8.",
            hint = "Calculate the mean by dividing the sum by 5, then find the middle value for the median.",
            difficulty = SatDifficulty.EASY
        ),
        // === NEW MATH: RATIOS & PROPORTIONS ===
        SATQuestion(
            id = "m_data_02",
            domain = SatDomain.MATH,
            subtopic = "Ratios & Proportions",
            prompt = "A recipe calls for 3 cups of flour for every 2 cups of sugar. If a baker uses 12 cups of flour, how many cups of sugar are needed to maintain the same ratio?",
            formulaOrSnippet = "3/2 = 12/x",
            options = listOf("8 cups", "6 cups", "18 cups", "10 cups"),
            correctIndex = 0,
            explanation = "Set up a proportion: 3/2 = 12/x.\nCross-multiply: 3x = 24.\nDivide by 3: x = 8 cups.",
            hint = "Set up a proportion and cross-multiply to solve for the unknown.",
            difficulty = SatDifficulty.EASY
        ),
        // === NEW VERBAL: WORDS IN CONTEXT ===
        SATQuestion(
            id = "v_voc_05",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "The CEO's ______ leadership style, which prioritized consensus over decisive action, often left the company ______ in rapidly changing markets where competitors could pivot more quickly.",
            prompt = "Which choice completes the text with the most logical and precise pair of words?",
            options = listOf("resolute ... stagnant", "deliberative ... vulnerable", "authoritarian ... adaptable", "impulsive ... resilient"),
            correctIndex = 1,
            explanation = "'Deliberative' means careful and slow in decision-making, which aligns with prioritizing consensus over speed. 'Vulnerable' fits because a slow, consensus-driven approach would leave a company exposed in fast-changing markets.",
            hint = "The first word should describe slow, careful decision-making; the second should describe a weakness caused by that slowness.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: WORDS IN CONTEXT ===
        SATQuestion(
            id = "v_voc_06",
            domain = SatDomain.VERBAL,
            subtopic = "Words in Context",
            contextPassage = "Although the historian's earlier works were criticized for being ______, her latest biography has been widely praised for its ______ archival research and meticulous attention to primary sources.",
            prompt = "Which choice completes the text with the most logical and precise pair of words?",
            options = listOf("superficial ... exhaustive", "thorough ... perfunctory", "meticulous ... cursory", "groundbreaking ... derivative"),
            correctIndex = 0,
            explanation = "The word 'Although' signals a contrast. The first blank should describe a negative quality (criticism) and the second a positive one (praise). 'Superficial' (lacking depth) contrasts perfectly with 'exhaustive' (thorough and comprehensive).",
            hint = "Look for a contrast: the first word is a criticism, the second is a praise. They should be near-opposites.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: PUNCTUATION (Dashes) ===
        SATQuestion(
            id = "v_punc_04",
            domain = SatDomain.VERBAL,
            subtopic = "Punctuation",
            contextPassage = "The three primary colors ______ red, blue, and yellow ______ form the basis of traditional color theory.",
            prompt = "Which choice completes the text so that it conforms to the conventions of Standard Written English?",
            options = listOf("\u2014 \u2026 \u2014", ", \u2026 ,", "; \u2026 ;", ": \u2026 :"),
            correctIndex = 0,
            explanation = "Dashes are required here because the interrupting phrase 'red, blue, and yellow' contains internal commas. Using commas to set off this phrase would create confusing punctuation. Dashes clearly mark the boundaries of the interruption.",
            hint = "When an interrupting phrase contains commas, use dashes to set it off instead of commas.",
            difficulty = SatDifficulty.HARD
        ),
        // === NEW VERBAL: PUNCTUATION (Semicolons) ===
        SATQuestion(
            id = "v_punc_05",
            domain = SatDomain.VERBAL,
            subtopic = "Punctuation",
            contextPassage = "The committee reviewed the proposal ______ after extensive deliberation, they approved the revised budget.",
            prompt = "Which choice completes the text so that it conforms to the conventions of Standard Written English?",
            options = listOf(";", ",", "\u2014", ":"),
            correctIndex = 0,
            explanation = "Two independent clauses ('The committee reviewed the proposal' and 'after extensive deliberation, they approved the revised budget') must be joined by a semicolon. A comma alone would create a comma splice.",
            hint = "Two complete sentences joined without a conjunction require a semicolon.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: TRANSITIONS ===
        SATQuestion(
            id = "v_trans_04",
            domain = SatDomain.VERBAL,
            subtopic = "Transitions",
            contextPassage = "The new policy aims to reduce carbon emissions by 40% over the next decade. ______, critics argue that the timeline is unrealistic given current infrastructure limitations and the slow pace of renewable energy adoption.",
            prompt = "Which choice completes the text with the most logical transition?",
            options = listOf("Therefore", "Moreover", "Nevertheless", "Specifically"),
            correctIndex = 2,
            explanation = "'Nevertheless' signals a contrast between the policy's ambitious goal and the critics' skepticism about its feasibility. The second sentence challenges rather than supports the first.",
            hint = "Does the second sentence support, challenge, or illustrate the first? Look for a contrast.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: TRANSITIONS ===
        SATQuestion(
            id = "v_trans_05",
            domain = SatDomain.VERBAL,
            subtopic = "Transitions",
            contextPassage = "Research has consistently shown that regular exercise improves cardiovascular health. ______, a recent study published in the Journal of Medicine found that even 30 minutes of moderate walking per day can significantly reduce the risk of heart disease.",
            prompt = "Which choice completes the text with the most logical transition?",
            options = listOf("In contrast", "For example", "However", "Therefore"),
            correctIndex = 1,
            explanation = "'For example' introduces a specific study that illustrates and supports the general claim made in the first sentence. The second sentence provides concrete evidence for the first.",
            hint = "The second sentence provides a specific study that supports the general claim in the first. Is this an example, a contrast, or a conclusion?",
            difficulty = SatDifficulty.EASY
        ),
        // === NEW VERBAL: EXPRESSION OF IDEAS ===
        SATQuestion(
            id = "v_expr_01",
            domain = SatDomain.VERBAL,
            subtopic = "Expression of Ideas",
            contextPassage = "The ancient Romans built an extensive network of roads throughout their empire. These roads facilitated trade, communication, and military movement across vast distances.",
            prompt = "Which choice most effectively combines the two sentences at the underlined portion?",
            options = listOf(
                "The ancient Romans built an extensive network of roads throughout their empire, and these roads facilitated trade, communication, and military movement across vast distances.",
                "The ancient Romans built an extensive network of roads throughout their empire that facilitated trade, communication, and military movement across vast distances.",
                "The ancient Romans built an extensive network of roads throughout their empire; these roads facilitated trade, communication, and military movement across vast distances.",
                "Building an extensive network of roads throughout their empire, trade, communication, and military movement were facilitated across vast distances by the ancient Romans."
            ),
            correctIndex = 1,
            explanation = "Option B combines the sentences into a single, concise sentence using a relative clause ('that facilitated...'). This effectively shows the relationship between the roads and their purpose without unnecessary repetition of 'these roads.'",
            hint = "Look for the most concise option that preserves the meaning and shows the relationship between the roads and their function.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: EXPRESSION OF IDEAS ===
        SATQuestion(
            id = "v_expr_02",
            domain = SatDomain.VERBAL,
            subtopic = "Expression of Ideas",
            contextPassage = "The final outcome of the negotiations was a mutually beneficial agreement that was advantageous to both parties involved in the discussions.",
            prompt = "Which choice most effectively eliminates redundancy while maintaining the meaning of the sentence?",
            options = listOf(
                "The outcome of the negotiations was a mutually advantageous agreement.",
                "The final outcome of the negotiations was an agreement that was beneficial to both parties.",
                "The outcome of the negotiations was a mutually beneficial agreement for both parties.",
                "The final result of the negotiations was a mutually beneficial agreement that was advantageous to both parties."
            ),
            correctIndex = 0,
            explanation = "Option A eliminates all redundancy: 'outcome' already implies finality (making 'final' unnecessary), and 'mutually advantageous' is more concise than 'mutually beneficial agreement that was advantageous to both parties.'",
            hint = "Identify words that repeat the same idea and choose the most concise version.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: COMMAND OF EVIDENCE ===
        SATQuestion(
            id = "v_ev_01",
            domain = SatDomain.VERBAL,
            subtopic = "Command of Evidence",
            contextPassage = "A pharmaceutical company claims that its new drug significantly reduces symptoms of chronic pain. To evaluate this claim, researchers conducted a double-blind, placebo-controlled clinical trial involving 500 participants over 12 weeks.",
            prompt = "Which choice provides the best evidence for the claim that the new drug is effective?",
            options = listOf(
                "The drug was developed over a ten-year period and targets a specific protein associated with pain signaling.",
                "In clinical trials, 78% of participants receiving the drug showed significant symptom improvement compared to 23% in the placebo group.",
                "The drug has received approval from several international regulatory agencies.",
                "Many pain specialists believe the drug represents a major breakthrough in chronic pain management."
            ),
            correctIndex = 1,
            explanation = "Option B provides quantitative data (78% vs. 23%) from a controlled clinical trial, which constitutes the strongest empirical evidence for the drug's effectiveness. The other options describe the drug's development, regulatory status, or expert opinions, which are not direct evidence of efficacy.",
            hint = "The strongest evidence for a claim about effectiveness is quantitative data from a controlled experiment.",
            difficulty = SatDifficulty.MEDIUM
        ),
        // === NEW VERBAL: COMMAND OF EVIDENCE ===
        SATQuestion(
            id = "v_ev_02",
            domain = SatDomain.VERBAL,
            subtopic = "Command of Evidence",
            contextPassage = "An urban planner argues that increasing the number of green spaces in cities improves residents' mental health. To support this claim, the planner cites research on the psychological effects of exposure to natural environments.",
            prompt = "Which finding, if true, would most directly support the urban planner's claim?",
            options = listOf(
                "Cities with more parks tend to have higher property values and lower crime rates.",
                "Residents living within 500 meters of a park report 30% lower stress levels than those living more than 1 kilometer from green spaces.",
                "Urban parks require regular maintenance funding from city budgets.",
                "Many famous urban parks, such as Central Park, were designed in the nineteenth century."
            ),
            correctIndex = 1,
            explanation = "Option B directly links proximity to green spaces with a measurable improvement in mental health (30% lower stress levels), which is the strongest support for the planner's claim. The other options address property values, safety, maintenance costs, or historical facts, none of which directly support the mental health claim.",
            hint = "Look for a finding that directly connects green spaces to a measurable mental health outcome.",
            difficulty = SatDifficulty.MEDIUM
        ),
    )

    fun getRandomQuestion(domain: SatDomain? = null, subtopic: String? = null): SATQuestion {
        val pool = questions.filter { q ->
            (domain == null || q.domain == domain) &&
            (subtopic == null || q.subtopic.contains(subtopic, ignoreCase = true))
        }
        return pool.randomOrNull() ?: questions.random()
    }
}
