package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ScholarmonSpecies
import com.example.data.repository.SATQuestionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context matches Scholar's Journey`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Scholar's Journey", appName)
  }

  @Test
  fun `verify SAT question bank contains Math and Verbal questions`() {
    assertTrue(SATQuestionBank.questions.isNotEmpty())
    val mathCount = SATQuestionBank.questions.count { it.domain.name == "MATH" }
    val verbalCount = SATQuestionBank.questions.count { it.domain.name == "VERBAL" }
    assertTrue(mathCount >= 10)
    assertTrue(verbalCount >= 10)
  }

  @Test
  fun `verify species catalog is loaded`() {
    assertTrue(ScholarmonSpecies.allSpecies.isNotEmpty())
    val algeray = ScholarmonSpecies.allSpecies.first { it.speciesId == "algeray" }
    assertEquals("Algeray", algeray.name)
  }
}
