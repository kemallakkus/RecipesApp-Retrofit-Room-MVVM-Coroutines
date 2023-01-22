package com.kemalakkus.easyfood.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemalakkus.easyfood.models.Category
import com.kemalakkus.easyfood.models.PopularMeals
import com.kemalakkus.easyfood.repository.FoodRepository
import kotlinx.coroutines.launch

class CategoryMealsViewModel() : ViewModel() {

    val repository = FoodRepository()
    val mealsLiveData = MutableLiveData<List<PopularMeals>>()

    fun getMealByCategory(categoryName : String) = viewModelScope.launch {
        val response = repository.getMealByCategory(categoryName)
        if (response.isSuccessful){
            response.body()?.let {
                mealsLiveData.postValue(it.meals)
            }
        }
    }


}