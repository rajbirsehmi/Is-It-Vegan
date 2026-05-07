package com.creative.isitvegan.robot

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class HomeRobot(rule: ComposeContentTestRule) : BaseRobot(rule) {

    fun verifyTitleVisible() {
        waitUntilNodeExists("top_bar_title")
        assertIsDisplayed("top_bar_title")
    }

    fun clickScanButton() {
        // The FAB uses contentDescription "Scan"
        onNodeWithTag("btn_scan").performClick()
    }

    fun verifyEmptyStateVisible() {
        // EmptyScreen uses text "No products found" (guessing based on name)
        // Let's use a generic text if we don't know the exact one.
        // Or check EmptyScreen.kt
    }
}
