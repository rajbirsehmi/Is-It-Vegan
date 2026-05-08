package com.creative.isitvegan.data.repo

import android.util.Log
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

    private val TAG = "RepositoryImpl"

    override suspend fun getProduct(barcode: String): Result<ProductResponse> {
        return try {
            val response = api.getProduct(barcode)
            if (response.isFound) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.statusVerbose ?: "Product not found"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching product $barcode: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun saveProduct(product: ProductEntity): Long {
        return database.productDao().insertProduct(product)
    }

    override suspend fun getProductFromDb(barcode: String): ProductEntity? {
        return database.productDao().getProductByBarcode(barcode)
    }

    override fun getAllProducts(): kotlinx.coroutines.flow.Flow<List<ProductEntity>> {
        return database.productDao().getAllProducts()
    }

    override suspend fun deleteProduct(product: ProductEntity) {
        database.productDao().deleteProduct(product)
    }
}