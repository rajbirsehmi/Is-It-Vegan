package com.creative.isitvegan.ui

import com.creative.isitvegan.MainActivity
import com.creative.isitvegan.core.testing.BaseRobotTest
import com.creative.isitvegan.core.testing.PermissionResetRule
import com.creative.isitvegan.core.testing.robots.homeRobot
import com.creative.isitvegan.core.testing.robots.scanRobot
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NavigationTest : BaseRobotTest<MainActivity>(MainActivity::class.java) {

    @get:Rule(order = -2)
    val permissionResetRule = PermissionResetRule()

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
    fun testStartScanningFlowWithPermissionDenied() {
        homeRobot(composeTestRule) {
            verifyEmptyState()
            clickStartScanning()

            denyPermission()

            verifyEmptyState()
            verifyScanButtonDisplayed()
        }
    }

    @Test
    fun testStartScanningFlowWithPermissionGranted() {
        homeRobot(composeTestRule) {
            verifyEmptyState()
            clickStartScanning()

            grantPermission()
        }

        scanRobot(composeTestRule) {
            verifyScanningState()
            clickClose()
        }

        homeRobot(composeTestRule) {
            verifyEmptyState()
        }
    }
}
