package com.creative.isitvegan.core.testing

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

abstract class BaseRobot(protected val composeTestRule: ComposeContentTestRule) {

    fun click(tag: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag(tag).performClick()
    }
    
    fun clickText(text: String) {
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText(text).isDisplayed()
        }
        composeTestRule.onNodeWithText(text).performClick()
    }

    fun clickContentDescription(desc: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription(desc).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithContentDescription(desc).performClick()
    }

    fun enterText(tag: String, text: String) {
        composeTestRule.onNodeWithTag(tag).performTextClearance()
        composeTestRule.onNodeWithTag(tag).performTextInput(text)
    }

    fun assertTagDisplayed(tag: String) {
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
    }
    
    fun assertTextDisplayed(text: String) {
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText(text).isDisplayed()
        }
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun waitForTag(tag: String, timeout: Long = 5000) {
        composeTestRule.waitUntil(timeout) {
            composeTestRule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()
        }
    }
}
