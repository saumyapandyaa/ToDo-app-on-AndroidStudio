package com.example.todo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented UI test for Jetpack Compose.
 * Launches HomeActivity, clicks the FAB, enters a task,
 * presses Done, and verifies that the task card appears.
 */
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<HomeActivity>()

    @Test
    fun addTask_navigatesAndDisplays() {
        // 1. Tap the Floating Action Button to go to New Task screen
        composeRule.onNodeWithContentDescription("Add Task").performClick()

        // 2. Fill the form fields
        composeRule.onNodeWithText("Title").performTextInput("Test Task")
        composeRule.onNodeWithText("Deadline").performTextInput("Tomorrow")
        composeRule.onNodeWithText("Description").performTextInput("Sample description")

        // 3. Tap Done to return to Home
        composeRule.onNodeWithText("Done").performClick()

        // 4. Verify that the new task is shown on the Home screen
        composeRule.onNodeWithText("Test Task").assertIsDisplayed()
    }
}
