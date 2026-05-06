package com.creative.isitvegan.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var product by mutableStateOf<ProductEntity?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getProduct(barcode: String) {
        if (isLoading) return
        
        viewModelScope.launch {
            isLoading = true
            val localProduct = repository.getProductFromDb(barcode)
            if (localProduct != null) {
                product = localProduct
                isLoading = false
            } else {
                // If not in DB (unlikely if we come from LoadingScreen), try API as fallback
                repository.getProduct(barcode)
                    .onSuccess { response ->
                        // The LoadingViewModel should have saved it, but just in case
                        product = response.product?.let { details ->
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
                        }
                        isLoading = false
                    }
                    .onFailure {
                        error = it.message
                        isLoading = false
                    }
            }
        }
    }
}
