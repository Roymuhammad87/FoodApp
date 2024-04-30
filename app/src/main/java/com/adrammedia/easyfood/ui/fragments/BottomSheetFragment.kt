package com.adrammedia.easyfood.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adrammedia.easyfood.databinding.FragmentBottomSheetBinding
import com.adrammedia.easyfood.ui.activities.MainActivity
import com.adrammedia.easyfood.ui.activities.MealDetailsActivity
import com.adrammedia.easyfood.utils.Constants
import com.adrammedia.easyfood.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEALID = "param1"

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var homeViewModel: HomeViewModel
    private var mealId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEALID)

        }
        homeViewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { homeViewModel.getMealDetails(it) }
        homeViewModel.mealDetailsLiveData.observe(this){meal->
            binding.apply {
                tvBtmshfragDetails.setOnClickListener {
                    val intent = Intent(activity, MealDetailsActivity::class.java)
                    intent.putExtra(Constants.MEAL_ID, meal.idMeal)
                    startActivity(intent)
                }
                tvBtmshfragArea.text = meal.strArea
                tvBtmshfragCategory.text = meal.strCategory
                tvBtmshfragTitle.text = meal.strMeal
            }
            Glide.with(this).load(meal.strMealThumb).into(binding.ivBtmshfragImg)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEALID, param1)
                }
            }
    }
}