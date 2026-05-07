package com.creative.isitvegan.robot

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class ScanRobot(rule: ComposeContentTestRule) : BaseRobot(rule) {

    fun verifyScannerVisible() {
        waitUntilNodeExists("camera_preview")
        assertIsDisplayed("camera_preview")
    }

    fun clickCloseScanner() {
        onNodeWithContentDescription("Close").performClick()
    }
}
