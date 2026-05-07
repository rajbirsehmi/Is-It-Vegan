package com.creative.isitvegan.robot

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class ProductRobot(rule: ComposeContentTestRule) : BaseRobot(rule) {

    fun verifyProductName(name: String) {
        // Assuming the name is displayed in a specific tag or just by text
        onNodeWithText(name).assertIsDisplayed()
    }

    fun verifyIsVegan() {
        onNodeWithText("Vegan").assertIsDisplayed()
    }

    fun verifyIsNotVegan() {
        onNodeWithText("Non-Vegan").assertIsDisplayed()
    }
}
