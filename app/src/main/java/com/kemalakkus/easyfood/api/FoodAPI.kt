package com.kemalakkus.easyfood.api

import com.kemalakkus.easyfood.models.Category
import com.kemalakkus.easyfood.models.CategoryList
import com.kemalakkus.easyfood.models.PopularList
import com.kemalakkus.easyfood.models.FoodList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {

    @GET("random.php")
    suspend fun getRandomFood():Response<FoodList>

    @GET("lookup.php")
    suspend fun getRandomMealDetail(
        @Query("i") i : String) : Response<FoodList>

    @GET("filter.php")
    suspend fun getPopularItems(
        @Query("c") categoryName : String) : Response<PopularList>

    @GET("categories.php")
    suspend fun getCategories() : Response<CategoryList>

    @GET("filter.php")
    suspend fun getMealByCategory(
        @Query("c") categoryName : String) : Response<PopularList>

    @GET("search.php")
    suspend fun searchMeal(
        @Query("s") searchQuery : String) : Response<FoodList>

}