package com.kemalakkus.easyfood.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalakkus.easyfood.db.MealDao
import com.kemalakkus.easyfood.db.MealDatabase
import com.kemalakkus.easyfood.models.FoodList
import com.kemalakkus.easyfood.models.Meal
import com.kemalakkus.easyfood.repository.FoodRepository
import com.kemalakkus.easyfood.repository.RepositoryDao
import kotlinx.coroutines.launch
import retrofit2.Response

class MealRandomDetailViewModel(app : Application) : AndroidViewModel(app) {

    val repository = FoodRepository()
    val randomMealDetail = MutableLiveData<FoodList>()
    val meal = MutableLiveData<List<Meal>>()

    val repositoryDao : RepositoryDao
    init {
        val dao = MealDatabase.invoke(app).mealDao()
        repositoryDao = RepositoryDao(app)
    }

    fun getRandomMealDetail(i:String) = viewModelScope.launch {
        val response = repository.getRandomMealDetail(i)
        if (response.isSuccessful){
            response.body()?.let {
                randomMealDetail.value = it
            }
        }
    }

    fun insertMeal(meal:Meal) = viewModelScope.launch {
        repositoryDao.insertMeal(meal)
    }



    fun getSaveMeal() : LiveData<List<Meal>>{
        return repositoryDao.getSaveMeal()
    }

}