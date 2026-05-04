package com.creative.isitvegan

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HiltSampleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun someTest() {
        assertTrue(true)
    }
}
