package com.kemalakkus.easyfood.repository

import com.kemalakkus.easyfood.api.FoodApiService
import com.kemalakkus.easyfood.models.CategoryList
import com.kemalakkus.easyfood.models.PopularList
import com.kemalakkus.easyfood.models.FoodList
import retrofit2.Response

class FoodRepository {

    val apiService = FoodApiService

    suspend fun getRandomFood() : Response<FoodList>{
        return apiService.api.getRandomFood()
    }

    suspend fun getRandomMealDetail(i:String) : Response<FoodList>{
        return apiService.api.getRandomMealDetail(i)
    }

    suspend fun getPopularItems(categoryName : String) : Response<PopularList>{
        return apiService.api.getPopularItems(categoryName)
    }

    suspend fun getCategories() : Response<CategoryList>{
        return apiService.api.getCategories()
    }

    suspend fun getMealByCategory(categoryName: String) : Response<PopularList>{
        return apiService.api.getMealByCategory(categoryName)
    }

    suspend fun getSearchMeals(searchQuery : String) : Response<FoodList>{
        return apiService.api.searchMeal(searchQuery)
    }

}