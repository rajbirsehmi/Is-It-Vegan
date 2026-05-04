package com.creative.isitvegan.ui.navgraph

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.creative.isitvegan.ui.screens.HomeScaffolding
import com.creative.isitvegan.ui.screens.LoadingScreen
import com.creative.isitvegan.ui.screens.ScanItemScreen
import com.creative.isitvegan.ui.viewmodels.RecentSearchViewModel
import com.creative.isitvegan.ui.viewmodels.ScanItemViewModel

@OptIn(ExperimentalGetImage::class)
@Composable
fun NavGraph() {
    LocalContext.current
    val navController = rememberNavController()
    val recentSearchViewModel = hiltViewModel<RecentSearchViewModel>()
    val scanItemViewModel = hiltViewModel<ScanItemViewModel>()
    val searches = recentSearchViewModel.products
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) navController.navigate(Routes.SCAN)
        else Toast.makeText(context, "Permission is required to scan the product", Toast.LENGTH_SHORT).show()
    }

    val barcodeResult by scanItemViewModel.barcodeResult.collectAsStateWithLifecycle()

    LaunchedEffect(barcodeResult) {
        if (barcodeResult?.isNotEmpty() == true) {
            navController.navigate(Routes.LOADING) {
                popUpTo(Routes.SCAN) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScaffolding(
                searches = searches,
                onScanClick = {
                    val hasPermission = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                    if (hasPermission) navController.navigate(Routes.SCAN)
                    else permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        }
        composable(Routes.SCAN) {
            ScanItemScreen(
                viewModel = scanItemViewModel,
                onCloseClick = {
                    scanItemViewModel.resetScanner()
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Routes.LOADING
        ) {
            LoadingScreen()
        }
        composable(Routes.PRODUCT) {
        }
    }
}
