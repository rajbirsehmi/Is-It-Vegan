package com.creative.isitvegan.ui.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class ErrorRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyErrorMessageDisplayed() {
        assertTextDisplayed("Something went wrong")
        assertTextDisplayed("Unable to find the product.")
    }

    fun clickBackToHome() {
        clickText("Back to Home")
    }
}

fun errorRobot(composeTestRule: ComposeContentTestRule, block: ErrorRobot.() -> Unit) =
    ErrorRobot(composeTestRule).apply(block)
