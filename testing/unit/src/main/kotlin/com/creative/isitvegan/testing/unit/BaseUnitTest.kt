package com.creative.isitvegan.testing.unit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before

/**
 * Base class for unit tests that use coroutines.
 * Automatically handles setting and resetting the Main dispatcher.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseUnitTest {

    protected val testDispatcher = StandardTestDispatcher()

    @Before
    fun baseSetup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun baseTearDown() {
        Dispatchers.resetMain()
    }
}
