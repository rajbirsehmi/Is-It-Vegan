package com.creative.isitvegan.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.creative.isitvegan.ui.theme.IsItVeganTheme
import com.creative.isitvegan.ui.viewmodels.LoadProductViewModel
import com.creative.isitvegan.ui.viewmodels.ScanItemViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    IsItVeganTheme {
        LoadingScreen()
    }
}