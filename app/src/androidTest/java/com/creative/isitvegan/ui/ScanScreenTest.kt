package com.creative.isitvegan.ui

import android.util.Log
import com.creative.isitvegan.MainActivity
import com.creative.isitvegan.core.testing.BaseRobotTest
import com.creative.isitvegan.core.testing.robots.homeRobot
import com.creative.isitvegan.core.testing.robots.scanRobot
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ScanScreenTest : BaseRobotTest<MainActivity>(MainActivity::class.java) {

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
    fun scanScreen_isDisplayedAfterClickingStartScanning() {
        homeRobot(composeTestRule) {
            clickStartScanning()
            grantPermission()
        }

        scanRobot(composeTestRule) {
            verifyScanningState()
        }
    }

    @Test
    fun scanScreen_canBeClosed() {
        homeRobot(composeTestRule) {
            Log.d("TAG", "scanScreen_canBeClosed: Clicking Camera")
            clickStartScanning()

            Log.d("TAG", "scanScreen_canBeClosed: Granting Permission")
            grantPermission()
        }

        scanRobot(composeTestRule) {
            Log.d("TAG", "scanScreen_canBeClosed: Scan Robot Loaded")
            clickClose()
            Log.d("TAG", "scanScreen_canBeClosed: Scan Robot Completed")
        }

        homeRobot(composeTestRule) {
            Log.d("TAG", "scanScreen_canBeClosed: Verifying Empty State")
            verifyEmptyState()
        }
    }
}
