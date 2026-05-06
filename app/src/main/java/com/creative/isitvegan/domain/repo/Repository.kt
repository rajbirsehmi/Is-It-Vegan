package com.creative.isitvegan.domain.repo

import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.data.remote.dto.ProductResponse

interface Repository {
    suspend fun getProduct(barcode: String): Result<ProductResponse>
    suspend fun saveProduct(product: ProductEntity): Long
    suspend fun getProductFromDb(barcode: String): ProductEntity?
    fun getAllProducts(): kotlinx.coroutines.flow.Flow<List<ProductEntity>>
    suspend fun deleteProduct(product: ProductEntity)
}