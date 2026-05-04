package com.creative.isitvegan.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("code") val code: String? = null,
    @SerialName("product") val product: ProductDetails? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("status_verbose") val statusVerbose: String? = null
)
