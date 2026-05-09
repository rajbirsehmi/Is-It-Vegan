package com.creative.isitvegan.ui.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class HomeRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyHomeTitleDisplayed() {
        assertTagDisplayed("top_bar_title")
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
        assertTextDisplayed("Is It Vegan?")
        assertTextDisplayed("Scan any product to find out if it's vegan-friendly instantly.")
    }
}

fun homeRobot(composeTestRule: ComposeContentTestRule, block: HomeRobot.() -> Unit) =
    HomeRobot(composeTestRule).apply(block)
