package com.kemalakkus.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kemalakkus.easyfood.R
import com.kemalakkus.easyfood.databinding.ActivityFoodBinding
import com.kemalakkus.easyfood.fragments.HomeFragment
import com.kemalakkus.easyfood.models.FoodList
import com.kemalakkus.easyfood.models.Meal
import com.kemalakkus.easyfood.viewmodels.MealRandomDetailViewModel


class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private lateinit var mealThumb:String
    private lateinit var mealName:String
    private lateinit var mealId:String
    private lateinit var youtubeLink: String
    private lateinit var viewModel: MealRandomDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()

        setInformationInViews()
        loadingCase()

        viewModel = ViewModelProvider(this)[MealRandomDetailViewModel::class.java]

        viewModel.getRandomMealDetail(mealId)



        observerRandomMealDetail()
        onYoutubeImageClick()
        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealx?.let {
                viewModel.insertMeal(mealx!!)
                Toast.makeText(this,"Meal Saved", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private var mealx : Meal? = null
    private fun observerRandomMealDetail() {
        viewModel.randomMealDetail.observe(this, Observer { meal->
            meal?.let {

                mealx = meal.meals[0]
                onResponseCase()
                binding.tvCategory.text = "Category : ${meal!!.meals.get(0).strCategory}"
                binding.tvArea.text = "Area : ${meal!!.meals.get(0).strArea}"
                binding.tinstructionsSteps.text = meal.meals.get(0).strInstructions

                youtubeLink = meal.meals.get(0).strYoutube.toString()
            }
        })
    }


    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetails)

        binding.collapsingTollbar.title = mealName
        binding.collapsingTollbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingTollbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
    }


}