package com.creative.isitvegan.core.testing.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class ScanRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyScanningState() {
        assertTextDisplayed("Scan Barcode")
        assertTextDisplayed("Place the barcode inside the frame to scan")
    }

    fun verifyDetectedState() {
        assertTextDisplayed("Barcode Detected!")
        assertTextDisplayed("Processing...")
    }

    fun clickClose() {
        clickContentDescription("Close Camera")
    }
}

fun scanRobot(composeTestRule: ComposeContentTestRule, block: ScanRobot.() -> Unit) =
    ScanRobot(composeTestRule).apply(block)
