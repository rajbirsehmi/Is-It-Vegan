package com.creative.isitvegan.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    var products by mutableStateOf<List<String>>(emptyList())
        private set


}