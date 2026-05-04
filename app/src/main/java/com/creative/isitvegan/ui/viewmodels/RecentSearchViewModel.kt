package com.creative.isitvegan.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.creative.isitvegan.domain.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

}