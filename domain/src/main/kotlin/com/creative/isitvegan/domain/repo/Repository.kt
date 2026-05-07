package com.creative.isitvegan.domain.repo

import com.creative.isitvegan.domain.model.ProductEntity
import com.creative.isitvegan.domain.model.ProductResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getProduct(barcode: String): Result<ProductResponse>
    suspend fun saveProduct(product: ProductEntity): Long
    suspend fun getProductFromDb(barcode: String): ProductEntity?
    fun getAllProducts(): Flow<List<ProductEntity>>
    suspend fun deleteProduct(product: ProductEntity)
}
