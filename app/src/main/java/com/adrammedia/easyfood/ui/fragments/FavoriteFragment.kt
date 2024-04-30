package com.adrammedia.easyfood.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adrammedia.easyfood.R
import com.adrammedia.easyfood.adapter.FavoriteAdapter
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.database.room.MealDb
import com.adrammedia.easyfood.databinding.FragmentFavoriteBinding
import com.adrammedia.easyfood.ui.activities.MainActivity
import com.adrammedia.easyfood.ui.activities.MealDetailsActivity
import com.adrammedia.easyfood.viewmodel.HomeViewModel
import com.adrammedia.easyfood.viewmodel.MealDetailsViewModel
import com.adrammedia.easyfood.viewmodel.MealDetailsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import retrofit2.Callback

class FavoriteFragment : Fragment() {
   private lateinit var binding: FragmentFavoriteBinding
   private lateinit var favoriteAdapter: FavoriteAdapter
   private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       homeViewModel =(activity as MainActivity).homeViewModel
        favoriteAdapter = FavoriteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getAllMeals()
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val mealPosition = viewHolder.adapterPosition
                val deletedMeal = favoriteAdapter.differ.currentList[mealPosition]
                homeViewModel.deleteMeal(deletedMeal)
                try {
                    Snackbar.make(requireView(), "Meal deleted successfully", Snackbar.LENGTH_LONG).setAction(
                        "Undo", View.OnClickListener {
                            homeViewModel.upsertMeal(deletedMeal)
                        }
                    ).show()
                }catch (e:Exception){
                    Log.d("TAG", "onSwiped: ${e.message}")
                }

            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerView)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = favoriteAdapter
        }
    }

    private fun getAllMeals() {
       homeViewModel.getAllMeals().observe(viewLifecycleOwner){
           favoriteAdapter.differ.submitList(it)
       }
    }

}