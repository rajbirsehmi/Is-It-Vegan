package com.creative.isitvegan.core.testing.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class LoadingRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyLoadingMessageDisplayed() {
        assertTextDisplayed("Analyzing ingredients...")
    }
}

fun loadingRobot(composeTestRule: ComposeContentTestRule, block: LoadingRobot.() -> Unit) =
    LoadingRobot(composeTestRule).apply(block)
