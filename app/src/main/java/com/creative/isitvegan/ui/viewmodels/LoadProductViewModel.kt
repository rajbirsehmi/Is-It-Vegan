package com.creative.isitvegan.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadProductViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var productResponse by mutableStateOf<ProductResponse?>(null)
        private set

    fun getProduct(barcode: String) {
        viewModelScope.launch {
            try {
                // 3. getOrThrow() extracts the value or throws the failure
                productResponse = repository.getProduct(barcode).getOrThrow()
                Log.d("TAG", "getProduct: " + productResponse!!.product)
                saveToDatabase()
            } catch (e: Exception) {
                // 4. On failure, it throws (or you can handle it here)
                productResponse = null
                throw e
            }
        }
    }

    suspend fun saveToDatabase() {
        repository.saveProduct(ProductEntity(
            id = null,
            barcode = productResponse!!.product!!.barcode,
            name = productResponse!!.product!!.name,
            brands = productResponse!!.product!!.brands,
            quantity = productResponse!!.product!!.quantity,
            productType = productResponse!!.product!!.productType,
            keywords = productResponse!!.product!!.keywords,
            categories = productResponse!!.product!!.categories,
            dataSources = productResponse!!.product!!.dataSources,
            ingredientsAnalysisTags = productResponse!!.product!!.ingredientsAnalysisTags,
            labelsTags = productResponse!!.product!!.labelsTags,
            ecoScoreGrade = productResponse!!.product!!.ecoScoreGrade,
            ecoScore = productResponse!!.product!!.ecoScore,
            frontSmallUrl = productResponse!!.product!!.frontSmallUrl,
            frontThumbUrl = productResponse!!.product!!.frontThumbUrl,
            frontUrl = productResponse!!.product!!.frontUrl,
            ingredientsSmallUrl = productResponse!!.product!!.ingredientsSmallUrl,
            ingredientsThumbUrl = productResponse!!.product!!.ingredientsThumbUrl,
            ingredientsUrl = productResponse!!.product!!.ingredientsUrl,
            nutritionSmallUrl = productResponse!!.product!!.nutritionSmallUrl,
            nutritionThumbUrl = productResponse!!.product!!.nutritionSmallUrl,
            nutritionUrl = productResponse!!.product!!.nutritionUrl,
            smallUrl = productResponse!!.product!!.smallUrl,
            thumbUrl = productResponse!!.product!!.thumbUrl,
            url = productResponse!!.product!!.url,
            ingredients = productResponse!!.product!!.ingredients
        ))
    }
}