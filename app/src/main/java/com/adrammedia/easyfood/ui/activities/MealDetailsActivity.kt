package com.adrammedia.easyfood.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.adrammedia.easyfood.R
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.database.room.MealDb
import com.adrammedia.easyfood.databinding.ActivityMealBinding
import com.adrammedia.easyfood.utils.Constants
import com.adrammedia.easyfood.viewmodel.MealDetailsViewModel
import com.adrammedia.easyfood.viewmodel.MealDetailsViewModelFactory
import com.bumptech.glide.Glide

class MealDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealDetailsViewModel: MealDetailsViewModel
    private lateinit var meal: Meal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDb = MealDb.getInstance(this)
        val mealDetailsViewModelFactory = MealDetailsViewModelFactory(mealDb)
        mealDetailsViewModel = ViewModelProvider(this, mealDetailsViewModelFactory)[MealDetailsViewModel::class.java]
        val intent = intent
        mealId = intent.getStringExtra(Constants.MEAL_ID).toString()
        //Api
         getMealDetails()
        //Room
        binding.btnSave.setOnClickListener {
            insertMeal(meal)
            Toast.makeText(this, "Meal saved successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun insertMeal(meal: Meal) {
        mealDetailsViewModel.upsertMeal(meal)
        Log.d("TAG", "insertMeal: ${meal.strMeal}")
    }

    //Api
    private fun getMealDetails() {
        mealDetailsViewModel.getMealDetails(mealId)
        mealDetailsViewModel.mealDetailsLiveData.observe(this@MealDetailsActivity) {meal->
            this.meal = meal
            binding.apply {
                collapsingToolbar.title = meal.strMeal
                collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white, null))
                collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white, null))
               imgYoutube.setOnClickListener {
                   val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
                   startActivity(intent)
               }

            }
            Glide.with(applicationContext)
                .load(meal.strMealThumb)
                .into(binding.imgMealDetail)
        }
    }

}