package com.creative.isitvegan.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.EMPTY) {
        }
        composable(Routes.HOME) {
        }
        composable(Routes.SCANNING) {
        }
        composable(Routes.LOADING) {
        }
        composable(Routes.PRODUCT) {
        }
    }
}