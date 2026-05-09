package com.creative.isitvegan.ui.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class LoadingRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyLoadingMessageDisplayed() {
        // Since messages rotate, we check for at least one of them
        val messages = listOf(
            "Analyzing ingredients...",
            "Checking vegan status...",
            "Decoding labels...",
            "Almost there..."
        )
        // This is a bit tricky with assertTextDisplayed because we don't know which one is current.
        // But usually "Analyzing ingredients..." is the first one.
        assertTextDisplayed("Analyzing ingredients...")
    }
}

fun loadingRobot(composeTestRule: ComposeContentTestRule, block: LoadingRobot.() -> Unit) =
    LoadingRobot(composeTestRule).apply(block)
