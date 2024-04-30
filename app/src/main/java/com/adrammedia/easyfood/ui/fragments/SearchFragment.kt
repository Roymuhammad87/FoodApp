package com.adrammedia.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.adrammedia.easyfood.R
import com.adrammedia.easyfood.adapter.FavoriteAdapter
import com.adrammedia.easyfood.databinding.FragmentBottomSheetBinding
import com.adrammedia.easyfood.databinding.FragmentSearchBinding
import com.adrammedia.easyfood.ui.activities.MainActivity
import com.adrammedia.easyfood.ui.activities.MealDetailsActivity
import com.adrammedia.easyfood.utils.Constants
import com.adrammedia.easyfood.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      homeViewModel = (activity as MainActivity).homeViewModel
        favoriteAdapter = FavoriteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        getSearchedMeals()
        favoriteAdapter.onItemClick = {
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, it.idMeal)
            startActivity(intent)
        }
    }
     var searchJop:Job? =null
    private fun getSearchedMeals() {
        binding.edSearch.addTextChangedListener {
            searchJop?.cancel()
            searchJop = lifecycleScope.launch {
                if (it.toString().isNotEmpty()){
                    delay(500)
                    homeViewModel.getMealDetailsByName(it.toString())
                    homeViewModel.mealDetailsByNameLiveData.observe(viewLifecycleOwner){
                        favoriteAdapter.differ.submitList(it)
                    }
                } else favoriteAdapter.differ.submitList(null)
            }
        }
    }

    private fun setupRv() {
        binding.rvSearchFrg.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = favoriteAdapter
        }
    }

}