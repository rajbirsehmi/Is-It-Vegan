package com.creative.isitvegan.ui

import com.creative.isitvegan.MainActivity
import com.creative.isitvegan.core.testing.BaseRobotTest
import com.creative.isitvegan.core.testing.robots.homeRobot
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest : BaseRobotTest<MainActivity>(MainActivity::class.java) {

    @Inject
    lateinit var database: com.creative.isitvegan.data.local.AppDatabase

    @Before
    fun setup() {
        hiltRule.inject()
        runBlocking {
            database.clearAllTables()
        }
    }

    @Test
    fun homeScreen_initialState_showsEmptyState() {
        homeRobot(composeTestRule) {
            verifyHomeTitleDisplayed()
            verifyEmptyState()
        }
    }

    @Test
    fun homeScreen_emptyState_hasStartScanningButton() {
        homeRobot(composeTestRule) {
            // Verifies that the button with "Start Scanning" is displayed
           verifyScanButtonDisplayed()
        }
    }
}
