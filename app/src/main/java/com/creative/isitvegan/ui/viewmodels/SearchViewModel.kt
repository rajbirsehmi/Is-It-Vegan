package com.creative.isitvegan.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var productResponse by mutableStateOf<ProductResponse?>(null)
        private set

    fun getProduct(barcode: String) {
        viewModelScope.launch {
            try {
                // 3. getOrThrow() extracts the value or throws the failure
                productResponse = repository.getProduct(barcode).getOrThrow()
            } catch (e: Exception) {
                // 4. On failure, it throws (or you can handle it here)
                productResponse = null
                throw e
            }
        }
    }
}