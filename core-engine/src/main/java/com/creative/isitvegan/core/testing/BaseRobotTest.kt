package com.creative.isitvegan.core.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule

abstract class BaseRobotTest<T : ComponentActivity>(activityClass: Class<T>) {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(activityClass)
}
