package com.creative.isitvegan.ui.navgraph

// Routes.kt
object Routes {
    const val HOME = "home"
    const val LOADING = "loading/{barcode}"
    const val PRODUCT = "product/{barcode}"
    const val ERROR = "error/{barcode}"
    const val SCAN = "scanning"

    fun getLoadingRoute(barcode: String) = "loading/$barcode"
    fun getProductRoute(barcode: String) = "product/$barcode"
    fun getErrorRoute(barcode: String) = "error/$barcode"
}
