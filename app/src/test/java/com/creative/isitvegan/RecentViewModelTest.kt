package com.creative.isitvegan

import com.creative.isitvegan.data.remote.dto.ProductDetails
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import com.creative.isitvegan.ui.viewmodels.RecentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecentViewModelTest {

    private lateinit var viewModel: RecentViewModel
    private val repository: Repository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Redirect Dispatchers.Main to our test dispatcher
        Dispatchers.setMain(testDispatcher)
        viewModel = RecentViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProduct success updates productResponse`() = runTest {
        // Given
        val barcode = "123456"
        val expectedResponse = ProductResponse(
            status = 1,
            statusVerbose = "Product found",
            product = ProductDetails(
                id = 123456L,
                barcode = barcode,
                name = "Vegan Milk",
                brands = "Vegan Brand"
            )
        )
        coEvery { repository.getProduct(barcode) } returns Result.success(expectedResponse)

        // When
        viewModel.getProduct(barcode)
        advanceUntilIdle() // Wait for the coroutine to finish

        // Then
        assertEquals(expectedResponse, viewModel.productResponse)
    }

    @Test(expected = Exception::class)
    fun `getProduct failure throws exception and sets productResponse to null`() = runTest {
        // Given
        val barcode = "123456"
        val exception = Exception("Network error")
        coEvery { repository.getProduct(barcode) } returns Result.failure(exception)

        // When
        try {
            viewModel.getProduct(barcode)
            advanceUntilIdle()
        } catch (e: Exception) {
            // Then
            assertNull(viewModel.productResponse)
            throw e // Re-throw to satisfy the @Test(expected)
        }
    }
}
