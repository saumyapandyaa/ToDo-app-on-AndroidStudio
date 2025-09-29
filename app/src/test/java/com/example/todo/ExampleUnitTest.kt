package com.example.todo

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example unit test for a pure Kotlin helper function.
 * This runs on the JVM only—no emulator or device needed.
 */

// A simple function to format a deadline date.
fun formatDeadline(year: Int, month: Int, day: Int): String =
    "%02d/%02d/%d".format(month, day, year)

class TaskUtilsTest {

    @Test
    fun formatDeadline_isCorrect() {
        val result = formatDeadline(2025, 9, 30)
        assertEquals("09/30/2025", result)
    }
}
