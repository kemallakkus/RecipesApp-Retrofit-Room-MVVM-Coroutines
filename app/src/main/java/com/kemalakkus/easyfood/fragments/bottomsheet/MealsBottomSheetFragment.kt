package com.kemalakkus.easyfood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kemalakkus.easyfood.R
import com.kemalakkus.easyfood.activities.FoodActivity
import com.kemalakkus.easyfood.databinding.FragmentMealsBottomSheetBinding
import com.kemalakkus.easyfood.fragments.HomeFragment
import com.kemalakkus.easyfood.viewmodels.HomeViewModel

private const val MEAL_ID = "param1"

class MealsBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding : FragmentMealsBottomSheetBinding
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealsBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mealId?.let {
            viewModel.getMealId(it)
        }

        observeMealBottomSheet()
        onBottomSheetDialogClick()

    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){
                val intent = Intent(activity, FoodActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName : String? = null
    private var mealThumb : String? = null
    private fun observeMealBottomSheet() {
        viewModel.bottomSheetLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Glide.with(this).load(it.strMealThumb).into(binding.imgBottomSheet)
                binding.tvBottomSheetArea.text = it.strArea
                binding.tvBottomSheetCategory.text = it.strCategory
                binding.tvBottomSheetMealName.text = it.strMeal

                mealName = it.strMeal
                mealThumb = it.strMealThumb
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}