package com.kemalakkus.easyfood.repository

import android.app.Application
import com.kemalakkus.easyfood.db.MealDao
import com.kemalakkus.easyfood.db.MealDatabase
import com.kemalakkus.easyfood.models.Meal

class RepositoryDao(application: Application) {

    val db = MealDatabase(application)

    suspend fun insertMeal(meal : Meal){
        db.mealDao().upsert(meal)
    }
    suspend fun deleteMeal(meal: Meal) {
        db.mealDao().delete(meal)
    }
    fun getSaveMeal() = db.mealDao().getAllMeal()

}