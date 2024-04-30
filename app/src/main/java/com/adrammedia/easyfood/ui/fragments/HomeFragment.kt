package com.adrammedia.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrammedia.easyfood.R
import com.adrammedia.easyfood.adapter.CategoriesAdapter
import com.adrammedia.easyfood.adapter.PopularAdapter
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.databinding.FragmentHomeBinding
import com.adrammedia.easyfood.ui.activities.MainActivity
import com.adrammedia.easyfood.ui.activities.MealDetailsActivity
import com.adrammedia.easyfood.utils.Constants
import com.adrammedia.easyfood.viewmodel.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var randomMeal:Meal
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     homeViewModel = (activity as MainActivity).homeViewModel
        popularAdapter = PopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RANDOM MEAL
         homeViewModel.getRandomMeal()
         observeRandomMeal()
         mealsDetails()

        //POPULAR MEALS
        setupRecyclerView()
        homeViewModel.getPopularMeals("Seafood")
        observePopularMeals()
        popularAdapter.onItemClick = {
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, it.idMeal)
            startActivity(intent)
        }
        popularAdapter.onItemLongClick = {
            val bottomSheetFragment = BottomSheetFragment.newInstance(it.idMeal)
            bottomSheetFragment.show(childFragmentManager, "")
        }

        //Get Categories
        getCategoriesList()
        categoriesAdapter.onCategoryClick = {
            homeViewModel.getPopularMeals(it.strCategory)
            observePopularMeals()
            binding.tvOverPupItems.text = it.strCategory +" Popular items"
        }
        //SEARCH FOR MEAL
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    //Get Categories
    private fun getCategoriesList() {
        setupCategoriesRecyclerView()
        homeViewModel.getCategoriesList()
        homeViewModel.categoriesLiveData.observe(viewLifecycleOwner){
            categoriesAdapter.differ.submitList(it)
        }
    }


    private fun setupCategoriesRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = categoriesAdapter
        }
    }

    private fun setupRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observePopularMeals() {
        homeViewModel.popularMealLiveData.observe(viewLifecycleOwner){
            popularAdapter.differ.submitList(it)
        }
    }

    private fun mealsDetails() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(Constants.MEAL_ID, randomMeal.idMeal)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeViewModel.randomMealLiveData.observe(viewLifecycleOwner){meal->
            this.randomMeal = meal
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)
        }
    }

}