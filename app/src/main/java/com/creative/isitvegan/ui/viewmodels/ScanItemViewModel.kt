package com.creative.isitvegan.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScanItemViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isScanComplete = MutableStateFlow(false)
    val isScanComplete = _isScanComplete.asStateFlow()

    private val _barcodeResult = MutableStateFlow<String?>(null)
    val barcodeResult = _barcodeResult.asStateFlow()

    fun onBarcodeDetected(code: String) {
        if (!_isScanComplete.value) {
            _isScanComplete.value = true
            _barcodeResult.value = code
        }
    }

    fun resetScanner() {
        _isScanComplete.value = false
        _barcodeResult.value = null
    }
}