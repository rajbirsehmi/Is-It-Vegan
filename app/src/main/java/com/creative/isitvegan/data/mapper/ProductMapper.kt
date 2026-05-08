package com.creative.isitvegan.data.mapper

import com.creative.isitvegan.data.local.entity.ProductEntity
import com.creative.isitvegan.data.remote.dto.ProductDetails
import com.creative.isitvegan.data.remote.dto.ProductResponse

fun ProductDetails.toEntity(): ProductEntity {
    return ProductEntity(
        id = null,
        barcode = barcode,
        name = name,
        brands = brands,
        quantity = quantity,
        productType = productType,
        keywords = keywords,
        categories = categories,
        dataSources = dataSources,
        ingredientsAnalysisTags = ingredientsAnalysisTags,
        labelsTags = labelsTags,
        ecoScoreGrade = ecoScoreGrade,
        ecoScore = ecoScore,
        frontSmallUrl = frontSmallUrl,
        frontThumbUrl = frontThumbUrl,
        frontUrl = frontUrl,
        ingredientsSmallUrl = ingredientsSmallUrl,
        ingredientsThumbUrl = ingredientsThumbUrl,
        ingredientsUrl = ingredientsUrl,
        nutritionSmallUrl = nutritionSmallUrl,
        nutritionThumbUrl = nutritionThumbUrl,
        nutritionUrl = nutritionUrl,
        smallUrl = smallUrl,
        thumbUrl = thumbUrl,
        url = url,
        ingredients = ingredients
    )
}

fun ProductEntity.toResponse(): ProductResponse {
    return ProductResponse(
        code = barcode,
        status = 1,
        statusVerbose = "Found in local database",
        product = ProductDetails(
            barcode = barcode,
            name = name,
            brands = brands,
            quantity = quantity,
            productType = productType,
            keywords = keywords,
            categories = categories,
            dataSources = dataSources,
            ingredientsAnalysisTags = ingredientsAnalysisTags,
            labelsTags = labelsTags,
            ecoScoreGrade = ecoScoreGrade,
            ecoScore = ecoScore,
            frontSmallUrl = frontSmallUrl,
            frontThumbUrl = frontThumbUrl,
            frontUrl = frontUrl,
            ingredientsSmallUrl = ingredientsSmallUrl,
            ingredientsThumbUrl = ingredientsThumbUrl,
            ingredientsUrl = ingredientsUrl,
            nutritionSmallUrl = nutritionSmallUrl,
            nutritionThumbUrl = nutritionThumbUrl,
            nutritionUrl = nutritionUrl,
            smallUrl = smallUrl,
            thumbUrl = thumbUrl,
            url = url,
            ingredients = ingredients
        )
    )
}
