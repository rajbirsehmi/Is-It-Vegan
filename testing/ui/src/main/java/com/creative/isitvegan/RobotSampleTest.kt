package com.creative.isitvegan

import androidx.compose.ui.test.junit4.createComposeRule
import com.creative.isitvegan.robot.HomeRobot
import com.creative.isitvegan.robot.launchRobot
import com.creative.isitvegan.ui.screens.HomeScaffolding
import com.creative.isitvegan.ui.theme.IsItVeganTheme
import org.junit.Rule
import org.junit.Test

class RobotSampleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeTitleIsVisible() {
        // Setup
        composeTestRule.setContent {
            IsItVeganTheme {
                HomeScaffolding(
                    searches = emptyList(),
                    onScanClick = {},
                    onProductClick = {}
                )
            }
        }

        // Use the Robot Engine
        launchRobot<HomeRobot>(composeTestRule) {
            verifyTitleVisible()
        }
    }
}
