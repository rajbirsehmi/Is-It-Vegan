package com.creative.isitvegan.robot

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

/**
 * BaseRobot provides a set of common actions and assertions for Compose Testing.
 * It abstracts the ComposeTestRule to provide a cleaner API for Robot implementations.
 */
abstract class BaseRobot(protected val rule: ComposeContentTestRule) {

    // --- Node Selectors ---

    protected fun onNodeWithTag(tag: String, useUnmergedTree: Boolean = false) =
        rule.onNode(hasTestTag(tag), useUnmergedTree)

    protected fun onNodeWithText(text: String, useUnmergedTree: Boolean = false, ignoreCase: Boolean = false) =
        rule.onNode(hasText(text, ignoreCase = ignoreCase), useUnmergedTree)

    protected fun onNodeWithContentDescription(description: String, useUnmergedTree: Boolean = false) =
        rule.onNode(hasContentDescription(description), useUnmergedTree)

    protected fun onAllNodesWithTag(tag: String, useUnmergedTree: Boolean = false) =
        rule.onAllNodes(hasTestTag(tag), useUnmergedTree)

    // --- Actions ---

    fun clickOnTag(tag: String) {
        onNodeWithTag(tag).performClick()
    }

    fun clickOnText(text: String) {
        onNodeWithText(text).performClick()
    }

    fun enterText(tag: String, text: String, clearFirst: Boolean = true) {
        val node = onNodeWithTag(tag)
        if (clearFirst) node.performTextClearance()
        node.performTextInput(text)
    }

    fun scrollToIndex(tag: String, index: Int) {
        onNodeWithTag(tag).performScrollToIndex(index)
    }

    fun scrollToKey(tag: String, key: Any) {
        onNodeWithTag(tag).performScrollToKey(key)
    }

    fun performActionOnTag(tag: String, action: SemanticsNodeInteraction.() -> Unit) {
        onNodeWithTag(tag).apply(action)
    }

    // --- Assertions ---

    fun assertExists(tag: String) {
        onNodeWithTag(tag).assertExists()
    }

    fun assertDoesNotExist(tag: String) {
        onNodeWithTag(tag).assertDoesNotExist()
    }

    fun assertIsDisplayed(tag: String) {
        onNodeWithTag(tag).assertIsDisplayed()
    }

    fun assertTextEquals(tag: String, text: String) {
        onNodeWithTag(tag).assertTextEquals(text)
    }

    fun assertTextContains(tag: String, text: String, substring: Boolean = true) {
        onNodeWithTag(tag).assertTextContains(text, substring = substring)
    }

    fun assertIsSelected(tag: String) {
        onNodeWithTag(tag).assertIsSelected()
    }

    fun assertIsNotSelected(tag: String) {
        onNodeWithTag(tag).assertIsNotSelected()
    }

    // --- Utils ---

    fun waitForIdle() {
        rule.waitForIdle()
    }

    fun waitUntilNodeExists(tag: String, timeoutMillis: Long = 5_000) {
        rule.waitUntil(timeoutMillis) {
            rule.onAllNodes(hasTestTag(tag)).fetchSemanticsNodes().isNotEmpty()
        }
    }
}
