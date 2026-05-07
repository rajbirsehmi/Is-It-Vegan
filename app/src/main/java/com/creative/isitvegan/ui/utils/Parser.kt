package com.creative.isitvegan.ui.utils

import com.creative.isitvegan.domain.model.ProductEntity
import com.creative.isitvegan.domain.model.ProductDetails

fun ProductDetails.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        barcode = this.barcode ?: "",
        name = this.name,
        brands = this.brands,
        quantity = this.quantity,
        productType = this.productType,
        keywords = this.keywords,
        categories = this.categories,
        dataSources = this.dataSources,
        ingredientsAnalysisTags = this.ingredientsAnalysisTags,
        labelsTags = this.labelsTags,
        ecoScoreGrade = this.ecoScoreGrade,
        ecoScore = this.ecoScore,
        frontSmallUrl = this.frontSmallUrl,
        frontThumbUrl = this.frontThumbUrl,
        frontUrl = this.frontUrl,
        ingredientsSmallUrl = this.ingredientsSmallUrl,
        ingredientsThumbUrl = this.ingredientsThumbUrl,
        ingredientsUrl = this.ingredientsUrl,
        nutritionSmallUrl = this.nutritionSmallUrl,
        nutritionThumbUrl = this.nutritionThumbUrl,
        nutritionUrl = this.nutritionUrl,
        smallUrl = this.smallUrl,
        thumbUrl = this.thumbUrl,
        url = this.url,
        ingredients = this.ingredients
    )
}

fun ProductEntity.toDomain(): ProductDetails {
    return ProductDetails(
        id = this.id,
        barcode = this.barcode ?: "",
        name = this.name,
        brands = this.brands,
        quantity = this.quantity,
        productType = this.productType,
        keywords = this.keywords,
        categories = this.categories,
        dataSources = this.dataSources,
        ingredientsAnalysisTags = this.ingredientsAnalysisTags,
        labelsTags = this.labelsTags,
        ecoScoreGrade = this.ecoScoreGrade,
        ecoScore = this.ecoScore,
        frontSmallUrl = this.frontSmallUrl,
        frontThumbUrl = this.frontThumbUrl,
        frontUrl = this.frontUrl,
        ingredientsSmallUrl = this.ingredientsSmallUrl,
        ingredientsThumbUrl = this.ingredientsThumbUrl,
        ingredientsUrl = this.ingredientsUrl,
        nutritionSmallUrl = this.nutritionSmallUrl,
        nutritionThumbUrl = this.nutritionThumbUrl,
        nutritionUrl = this.nutritionUrl,
        smallUrl = this.smallUrl,
        thumbUrl = this.thumbUrl,
        url = this.url,
        ingredients = this.ingredients
    )
}
