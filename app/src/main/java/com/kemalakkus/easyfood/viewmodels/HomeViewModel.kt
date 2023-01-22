package com.kemalakkus.easyfood.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalakkus.easyfood.db.MealDatabase
import com.kemalakkus.easyfood.models.Category
import com.kemalakkus.easyfood.models.PopularMeals
import com.kemalakkus.easyfood.models.FoodList
import com.kemalakkus.easyfood.models.Meal
import com.kemalakkus.easyfood.repository.FoodRepository
import com.kemalakkus.easyfood.repository.RepositoryDao
import kotlinx.coroutines.launch

class HomeViewModel(app : Application) : AndroidViewModel(app) {
    val repository = FoodRepository()
    var randomFood = MutableLiveData<FoodList>()
    val popularFoodCategory = MutableLiveData<List<PopularMeals>>()
    val categoriesLiveData = MutableLiveData<List<Category>>()
    val bottomSheetLiveData = MutableLiveData<Meal>()
    val searchMealsLiveData = MutableLiveData<List<Meal>>()

    val repositoryDao : RepositoryDao
    init {
        val dao = MealDatabase.invoke(app).mealDao()
        repositoryDao = RepositoryDao(app)
    }

    val favoritesMealsLiveData = repositoryDao.getSaveMeal()

    fun getRandomFood() = viewModelScope.launch {

        val response = repository.getRandomFood()
        if (response.isSuccessful){
            response.body()?.let {
                randomFood.value = it
            }
        }
    }

    fun getPopularItems() = viewModelScope.launch {
        val response = repository.getPopularItems("Seafood")
        if (response.isSuccessful){
            response.body()?.let {
                popularFoodCategory.value = it.meals
            }
        }
    }

    fun getMealId(id : String) = viewModelScope.launch{
        val response = repository.getRandomMealDetail(id)
        if (response.isSuccessful){
            response.body()?.meals?.first()?.let {
                bottomSheetLiveData.value = it
            }
        }
    }

    fun getCategories() = viewModelScope.launch {
        val response = repository.getCategories()
        if (response.isSuccessful){
            response.body()?.let {
                categoriesLiveData.value = it.categories
            }
        }
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        repositoryDao.deleteMeal(meal)
    }

    fun insertMeal(meal:Meal) = viewModelScope.launch {
        repositoryDao.insertMeal(meal)
    }

    fun getSearchMeals(searchQuery : String) = viewModelScope.launch {
        val response = repository.getSearchMeals(searchQuery)
        if (response.isSuccessful){
            response.body()?.let {
                searchMealsLiveData.value = it.meals
            }
        }
    }



}