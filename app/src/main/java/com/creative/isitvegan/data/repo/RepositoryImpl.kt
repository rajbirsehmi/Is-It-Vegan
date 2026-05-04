package com.creative.isitvegan.data.repo

import com.creative.isitvegan.data.local.AppDatabase
import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.data.remote.OpenFoodFactsApi
import com.creative.isitvegan.data.remote.dto.ProductResponse
import com.creative.isitvegan.domain.repo.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: OpenFoodFactsApi,
    private val database: AppDatabase
) : Repository {

    override suspend fun getProduct(barcode: String): Result<ProductResponse> {
        return try {
            val response = api.getProduct(barcode)
            if (response.status == 1) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.statusVerbose ?: "Product not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveProduct(product: ProductEntity): Long {
        return database.productDao().insertProduct(product)
    }
}