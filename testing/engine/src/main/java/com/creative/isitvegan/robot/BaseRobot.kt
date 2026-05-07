package com.creative.isitvegan.robot

import android.util.Log
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import java.util.regex.Pattern

/**
 * BaseRobot provides a set of common actions and assertions for Compose Testing.
 * It abstracts the ComposeTestRule to provide a cleaner API for Robot implementations.
 */
abstract class BaseRobot(protected val rule: ComposeContentTestRule) {

    companion object {
        fun createRule() = createComposeRule()
    }

    // --- Chaining ---

    fun <R : BaseRobot> nextRobot(
        nextRobotFactory: (ComposeContentTestRule) -> R,
        block: R.() -> Unit
    ): R {
        val nextRobot = nextRobotFactory(rule)
        nextRobot.apply(block)
        return nextRobot
    }

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

    fun assertIsDisplayedByText(text: String) {
        onNodeWithText(text).assertIsDisplayed()
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

    fun waitUntilNodeExists(tag: String, timeoutMillis: Long = 15_000) {
        rule.waitUntil(timeoutMillis) {
            rule.onAllNodes(hasTestTag(tag)).fetchSemanticsNodes().isNotEmpty()
        }
    }

    // --- Permissions & System ---

    /**
     * Attempts to grant permission.
     * Uses UI Automator with multiple text options, resource IDs, and Espresso fallback.
     */
    fun grantPermission(permission: String) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        
        // Try common resource IDs first (more stable across languages)
        val resIds = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "android:id/button1"
        )
        
        for (resId in resIds) {
            val button = device.findObject(By.res(resId))
            if (button != null) {
                button.click()
                return
            }
        }
        
        // Try text options
        val texts = listOf("Allow", "While using the app", "Only this time", "Allow while using the app")
        for (text in texts) {
            val button = device.wait(Until.findObject(By.text(Pattern.compile("(?i)$text"))), 1000)
            if (button != null) {
                button.click()
                return
            }
        }
        
        // Espresso fallback
        try {
            onView(withText(Pattern.compile("(?i)Allow|While using the app").toString())).perform(click())
        } catch (e: Exception) { /* ignore */ }
    }

    /**
     * Attempts to deny permission.
     */
    fun denyPermission() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        
        val resIds = listOf(
            "com.android.permissioncontroller:id/permission_deny_button",
            "android:id/button2"
        )
        
        for (resId in resIds) {
            val button = device.findObject(By.res(resId))
            if (button != null) {
                button.click()
                return
            }
        }
        
        val texts = listOf("Deny", "Don't allow", "Don’t allow")
        for (text in texts) {
            val button = device.wait(Until.findObject(By.text(Pattern.compile("(?i)$text"))), 1000)
            if (button != null) {
                button.click()
                return
            }
        }
        
        try {
            onView(withText(Pattern.compile("(?i)Deny|Don't allow").toString())).perform(click())
        } catch (e: Exception) { /* ignore */ }
    }

    fun resetPermissions() {
        val packageName = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("pm reset-permissions $packageName")
        Thread.sleep(1000) // Wait for system to settle
    }

    /**
     * Disables system animations to make tests more reliable.
     */
    fun disableAnimations() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.uiAutomation.executeShellCommand("settings put global window_animation_scale 0")
        instrumentation.uiAutomation.executeShellCommand("settings put global transition_animation_scale 0")
        instrumentation.uiAutomation.executeShellCommand("settings put global animator_duration_scale 0")
    }

    /**
     * Enables system animations back to default.
     */
    fun enableAnimations() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.uiAutomation.executeShellCommand("settings put global window_animation_scale 1")
        instrumentation.uiAutomation.executeShellCommand("settings put global transition_animation_scale 1")
        instrumentation.uiAutomation.executeShellCommand("settings put global animator_duration_scale 1")
    }

    fun verifyToastVisible(text: String) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Use By.text with a quoted pattern for better reliability and case-insensitivity
        // Pattern.quote ensures that special characters in the text don't break the regex
        val pattern = Pattern.compile(".*${Pattern.quote(text)}.*", Pattern.CASE_INSENSITIVE or Pattern.DOTALL)
        val toast = device.wait(Until.findObject(By.text(pattern)), 5000)
        assertTrue("Toast with text '$text' was not found within 5 seconds", toast != null)
    }
}
