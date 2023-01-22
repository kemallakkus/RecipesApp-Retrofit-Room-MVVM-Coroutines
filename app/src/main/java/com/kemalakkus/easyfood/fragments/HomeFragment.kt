package com.kemalakkus.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kemalakkus.easyfood.R
import com.kemalakkus.easyfood.activities.CategoryMealsActivity
import com.kemalakkus.easyfood.activities.FoodActivity
import com.kemalakkus.easyfood.adapter.CategoryAdapter
import com.kemalakkus.easyfood.adapter.MostPopularAdapter
import com.kemalakkus.easyfood.databinding.FragmentHomeBinding
import com.kemalakkus.easyfood.fragments.bottomsheet.MealsBottomSheetFragment
import com.kemalakkus.easyfood.models.PopularMeals
import com.kemalakkus.easyfood.models.Meal
import com.kemalakkus.easyfood.viewmodels.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private val popularAdapter = MostPopularAdapter()
    private lateinit var categoryAdapter: CategoryAdapter

    companion object{
        const val MEAL_ID = "com.kemalakkus.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.kemalakkus.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.kemalakkus.easyfood.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.kemalakkus.easyfood.fragments.categoryName"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recViewMealsPopular.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.recViewMealsPopular.adapter = popularAdapter

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getRandomFood()
        observerRandomMeal()
        onRandomMealClick()


        viewModel.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()

        onPopularItenLongClick()

        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchMealsFragment)
        }
    }

    private fun onPopularItenLongClick() {
        popularAdapter.onLongItemClick = {
            val mealsBottomSheetFragment = MealsBottomSheetFragment.newInstance(it.idMeal)
            mealsBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = {
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {categories ->
            categories?.let {
                    categoryAdapter.setCategoryList(categories)

            }
        })
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = {
            val intent = Intent(activity,FoodActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }

    }

    private fun observerPopularItemsLiveData() {
        viewModel.popularFoodCategory.observe(viewLifecycleOwner, Observer {
            it?.let {
                popularAdapter.setMeals(mealsList = it as ArrayList<PopularMeals>)
            }
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity,FoodActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {

        viewModel.randomFood.observe(viewLifecycleOwner, Observer {
            it?.let {
                Glide.with(this@HomeFragment)
                    .load(it.meals.get(0).strMealThumb)
                    .into(binding.imgRandomMeal)
            }
            randomMeal = it.meals[0]

        })
    }


}