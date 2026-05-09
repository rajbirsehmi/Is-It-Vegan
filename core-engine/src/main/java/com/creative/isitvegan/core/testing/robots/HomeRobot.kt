package com.creative.isitvegan.core.testing.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import com.creative.isitvegan.core.testing.BaseRobot
import org.junit.Test
import java.util.regex.Pattern

class HomeRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyHomeTitleDisplayed() {
        assertTagDisplayed("top_bar_title")
    }

    fun verifyScanButtonDisplayed() {
        assertTagDisplayed("btn_scan")
    }

    fun clickStartScanning() {
        clickText("Start Scanning")
    }

    fun clickFabScan() {
        clickContentDescription("Scan")
    }

    fun verifyProductInRecentList(productName: String) {
        assertTextDisplayed(productName)
    }

    fun clickProduct(productName: String) {
        clickText(productName)
    }
    
    fun verifyEmptyState() {
        assertTagDisplayed("text_main")
        assertTagDisplayed("text_sub_main")
    }
    
    fun grantPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        
        val pattern = Pattern.compile("(?i)Allow|While using the app|Only this time")
        val allowButton = device.wait(Until.findObject(By.text(pattern)), 5000)
        
        allowButton?.click()
    }

    fun denyPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)

        // 1. Try matching common English text patterns
        val pattern = Pattern.compile("(?i)Deny|Don't allow|Don’t allow")
        var denyButton = device.wait(Until.findObject(By.text(pattern)), 3000)

        // 2. Fallback: Try common system resource IDs
        if (denyButton == null) {
            denyButton = device.findObject(By.res("com.android.permissioncontroller:id/permission_deny_button"))
                ?: device.findObject(By.res("android:id/button2"))
        }

        // 3. Last Resort: Click the button that is NOT "Allow" (usually index 0 or 1)
        if (denyButton == null) {
            // Find all buttons and click the one that doesn't match 'Allow'
            val buttons = device.findObjects(By.clazz("android.widget.Button"))
            denyButton = buttons.find { it.text?.contains("Allow", ignoreCase = true) == false }
        }

        denyButton?.click()
        device.waitForIdle() // Ensure the click is processed
    }
}

fun homeRobot(composeTestRule: ComposeContentTestRule, block: HomeRobot.() -> Unit) =
    HomeRobot(composeTestRule).apply(block)
