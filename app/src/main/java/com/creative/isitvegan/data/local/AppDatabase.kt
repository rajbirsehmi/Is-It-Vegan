package com.creative.isitvegan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.creative.isitvegan.data.local.dao.IngredientDao
import com.creative.isitvegan.data.local.entity.IngredientEntity

@Database(entities = [IngredientEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
}
