package com.kemalakkus.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalakkus.easyfood.R
import com.kemalakkus.easyfood.adapter.FavoritesMealsAdapter
import com.kemalakkus.easyfood.databinding.FragmentSearchMealsBinding
import com.kemalakkus.easyfood.viewmodels.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMealsFragment : Fragment() {

    private lateinit var binding : FragmentSearchMealsBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var searchRecyclerViewAdapter : FavoritesMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMealsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        prepareRecyclerView()

        binding.imgSearchMeals.setOnClickListener { searchMeals() }

        observeSearchMealsLiveData()

        var searchJob : Job? = null
            binding.searchBox.addTextChangedListener { searchQuery ->
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(500)
                    viewModel.getSearchMeals(searchQuery.toString())
                }
            }
    }

    private fun observeSearchMealsLiveData() {
        viewModel.searchMealsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                searchRecyclerViewAdapter.differ.submitList(it)
            }
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.searchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.getSearchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = FavoritesMealsAdapter()
        binding.rvSearchMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter
        }
    }

}