package com.creative.isitvegan.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.data.remote.dto.ProductDetails
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var productResponse by mutableStateOf<ProductResponse?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getProduct(barcode: String) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            error = null
            
            // 1. Check Database First
            val localProduct = repository.getProductFromDb(barcode)
            if (localProduct != null) {
                Log.d("TAG", "Database Hit: ${localProduct.name}")
                // Create a mock response to satisfy the current flow
                productResponse = ProductResponse(
                    status = 1,
                    statusVerbose = "Product found in local database",
                    product = ProductDetails(
                        barcode = localProduct.barcode,
                        name = localProduct.name,
                        brands = localProduct.brands,
                        quantity = localProduct.quantity,
                        productType = localProduct.productType,
                        keywords = localProduct.keywords,
                        categories = localProduct.categories,
                        dataSources = localProduct.dataSources,
                        ingredientsAnalysisTags = localProduct.ingredientsAnalysisTags,
                        labelsTags = localProduct.labelsTags,
                        ecoScoreGrade = localProduct.ecoScoreGrade,
                        ecoScore = localProduct.ecoScore,
                        frontSmallUrl = localProduct.frontSmallUrl,
                        frontThumbUrl = localProduct.frontThumbUrl,
                        frontUrl = localProduct.frontUrl,
                        ingredientsSmallUrl = localProduct.ingredientsSmallUrl,
                        ingredientsThumbUrl = localProduct.ingredientsThumbUrl,
                        ingredientsUrl = localProduct.ingredientsUrl,
                        nutritionSmallUrl = localProduct.nutritionSmallUrl,
                        nutritionThumbUrl = localProduct.nutritionThumbUrl,
                        nutritionUrl = localProduct.nutritionUrl,
                        smallUrl = localProduct.smallUrl,
                        thumbUrl = localProduct.thumbUrl,
                        url = localProduct.url,
                        ingredients = localProduct.ingredients
                    )
                )
                isLoading = false
                return@launch
            }

            // 2. Fallback to API
            Log.d("TAG", "Repo Called For: $barcode")
            repository.getProduct(barcode)
                .onSuccess { response ->
                    Log.d("TAG", "VM Success: product=${response.product?.name}")
                    productResponse = response
                    try {
                        saveToDatabase(response)
                    } catch (e: Exception) {
                        Log.e("TAG", "Database Save Error: ${e.message}")
                    }
                    isLoading = false
                }
                .onFailure { e ->
                    Log.d("TAG", "VM Failure: ${e.message}")
                    error = e.message ?: "An unknown error occurred"
                    isLoading = false
                }
        }
    }

    private suspend fun saveToDatabase(response: ProductResponse) {
        val details = response.product ?: return // Don't save if there's no product info

        repository.saveProduct(
            ProductEntity(
                id = null,
                barcode = details.barcode,
                name = details.name,
                brands = details.brands,
                quantity = details.quantity,
                productType = details.productType,
                keywords = details.keywords,
                categories = details.categories,
                dataSources = details.dataSources,
                ingredientsAnalysisTags = details.ingredientsAnalysisTags,
                labelsTags = details.labelsTags,
                ecoScoreGrade = details.ecoScoreGrade,
                ecoScore = details.ecoScore,
                frontSmallUrl = details.frontSmallUrl,
                frontThumbUrl = details.frontThumbUrl,
                frontUrl = details.frontUrl,
                ingredientsSmallUrl = details.ingredientsSmallUrl,
                ingredientsThumbUrl = details.ingredientsThumbUrl,
                ingredientsUrl = details.ingredientsUrl,
                nutritionSmallUrl = details.nutritionSmallUrl,
                nutritionThumbUrl = details.nutritionThumbUrl,
                nutritionUrl = details.nutritionUrl,
                smallUrl = details.smallUrl,
                thumbUrl = details.thumbUrl,
                url = details.url,
                ingredients = details.ingredients
            )
        )
    }
}
