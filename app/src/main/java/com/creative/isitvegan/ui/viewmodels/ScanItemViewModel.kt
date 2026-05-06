package com.creative.isitvegan.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanItemViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isScanComplete = MutableStateFlow(false)
    val isScanComplete = _isScanComplete.asStateFlow()

    private val _barcodeResult = MutableStateFlow<String?>(null)
    val barcodeResult = _barcodeResult.asStateFlow()

    private val _existsInDb = MutableStateFlow<Boolean?>(null)
    val existsInDb = _existsInDb.asStateFlow()

    fun onBarcodeDetected(code: String) {
        if (!_isScanComplete.value) {
            _isScanComplete.value = true
            viewModelScope.launch {
                val product = repository.getProductFromDb(code)
                _existsInDb.value = product != null
                _barcodeResult.value = code
            }
        }
    }

    fun resetScanner() {
        _isScanComplete.value = false
        _barcodeResult.value = null
        _existsInDb.value = null
    }

    fun clearBarcodeResult() {
        _barcodeResult.value = null
        // Note: we keep _existsInDb state until resetScanner is called for a new scan
    }
}
