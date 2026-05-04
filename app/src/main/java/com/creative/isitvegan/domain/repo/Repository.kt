package com.creative.isitvegan.domain.repo

import com.creative.isitvegan.data.remote.dto.ProductResponse

interface Repository {
    suspend fun getProduct(barcode: String): Result<ProductResponse>

}