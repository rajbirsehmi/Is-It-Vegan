package com.creative.isitvegan.data.remote

import com.creative.isitvegan.data.remote.dto.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenFoodFactsApi {

    /**
     * Get product details by barcode.
     * Endpoint: https://world.openfoodfacts.net/api/v2/product/[barcode]
     */
    @GET("api/v2/product/{barcode}")
    suspend fun getProduct(
        @Path("barcode") barcode: String,
        @Query("fields") fields: String? = null // Optional: filter fields to reduce payload size
    ): ProductResponse

    companion object {
        const val BASE_URL = "https://world.openfoodfacts.net/"
    }
}
