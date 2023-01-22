package com.kemalakkus.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kemalakkus.easyfood.adapter.CategoryMealsAdapter
import com.kemalakkus.easyfood.databinding.ActivityCategoryMealsBinding
import com.kemalakkus.easyfood.fragments.HomeFragment
import com.kemalakkus.easyfood.viewmodels.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)


        observerCategoryMealsLiveData()

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun observerCategoryMealsLiveData() {
        categoryMealsViewModel.mealsLiveData.observe(this, Observer { mealList ->
            mealList?.let {
                binding.tvCategoryCount.text = it.size.toString()
                categoryMealsAdapter.setMealsList(mealList)
            }
        })
    }






}