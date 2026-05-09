package com.creative.isitvegan.core.testing.robots

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.creative.isitvegan.core.testing.BaseRobot

class ProductRobot(composeTestRule: ComposeContentTestRule) : BaseRobot(composeTestRule) {

    fun verifyProductDetails(name: String, brand: String) {
        assertTextDisplayed(name)
        assertTextDisplayed(brand)
    }

    fun verifyVeganStatus() {
        assertTextDisplayed("Certified Vegan Friendly")
    }

    fun verifyNonVeganStatus() {
        assertTextDisplayed("Contains Non-Vegan Ingredients")
    }

    fun clickBack() {
        clickContentDescription("Back")
    }
}

fun productRobot(composeTestRule: ComposeContentTestRule, block: ProductRobot.() -> Unit) =
    ProductRobot(composeTestRule).apply(block)
