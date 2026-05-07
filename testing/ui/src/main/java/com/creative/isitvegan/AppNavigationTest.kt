package com.creative.isitvegan

import android.Manifest
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.creative.isitvegan.robot.HomeRobot
import com.creative.isitvegan.robot.ScanRobot
import com.creative.isitvegan.robot.launchRobot
import com.creative.isitvegan.ui.navgraph.NavGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppNavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
        // Disable animations
        InstrumentationRegistry.getInstrumentation().uiAutomation.apply {
            executeShellCommand("settings put global window_animation_scale 0")
            executeShellCommand("settings put global transition_animation_scale 0")
            executeShellCommand("settings put global animator_duration_scale 0")
        }
    }

    @Test
    fun testStartScanningFromHome() {
        launchRobot<HomeRobot>(composeTestRule) {
            verifyTitleVisible()
            clickScanButton()
            grantPermission(Manifest.permission.CAMERA)
        }

        launchRobot<ScanRobot>(composeTestRule) {
            verifyScannerVisible()
        }
    }

    @Test
    fun testCameraPermissionFlow() {
        launchRobot<HomeRobot>(composeTestRule) {
            // 1. Reset permissions to ensure we start from a clean state
            resetPermissions()

            // 2. Try to scan, should fail and show toast
            clickScanButton()
            denyPermission()
//            verifyToastVisible("Permission is required to scan the product")

            // 3. Grant permission and try again
            clickScanButton()
            grantPermission(Manifest.permission.CAMERA)
        }

        // 4. Verify scanner is now visible
        launchRobot<ScanRobot>(composeTestRule) {
            verifyScannerVisible()
        }
    }
}
