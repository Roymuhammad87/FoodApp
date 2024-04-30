package com.adrammedia.easyfood.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrammedia.easyfood.database.models.mealscategories.CategoriesResponse
import com.adrammedia.easyfood.database.models.mealscategories.Category
import com.adrammedia.easyfood.database.models.popularmeals.PopularMeal
import com.adrammedia.easyfood.database.models.popularmeals.PopularMealsResponse
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.database.models.randommeal.MealDetailsResponse
import com.adrammedia.easyfood.database.models.randommeal.RandomMealResponse
import com.adrammedia.easyfood.database.retrofit.RetrofitInstance
import com.adrammedia.easyfood.database.room.MealDb
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(private val mealDb: MealDb):ViewModel() {

 //GET RANDOM MEAL
    private val _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> =_randomMealLiveData
    //to save state during configuration changes for random meal
    var saveStateLiveData:Meal? =null
    fun getRandomMeal(){
        saveStateLiveData?.let {
            _randomMealLiveData.value = it
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                if (response.body() != null){
                    val randomMeal = response.body()!!.meals[0]
                    _randomMealLiveData.value = randomMeal
                    saveStateLiveData = randomMeal
                } else return

            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }

    //GET POPULAR MEALS
    private val _popularMealLiveData = MutableLiveData<List<PopularMeal>>()
    val popularMealLiveData: LiveData<List<PopularMeal>> =_popularMealLiveData
    fun getPopularMeals(categoryId: String) {
        RetrofitInstance.api.getPopularMeals(categoryId).enqueue(object :Callback<PopularMealsResponse>{
            override fun onResponse(
                call: Call<PopularMealsResponse>, response: Response<PopularMealsResponse>) {
               if (response.body() != null){
                   _popularMealLiveData.value = response.body()!!.meals
               } else return
            }

            override fun onFailure(call: Call<PopularMealsResponse>, t: Throwable) {
                Log.d("TAG", "on popular Failure: ${t.message}")
            }

        })
    }
    //Getting Meals by id from api
    private val _mealDetailsLiveData = MutableLiveData<Meal>()
    val mealDetailsLiveData: LiveData<Meal> = _mealDetailsLiveData
    fun getMealDetails(mealId: String) {
        RetrofitInstance.api.getMealDetails(mealId).enqueue(object :Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                if (response.body() != null) {
                    _mealDetailsLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                Log.d("TAG", "meal details failure: ${t.message}")
            }
        })
    }
    //Getting Meals by name from api
    private val _mealDetailsByNameLiveData = MutableLiveData<List<Meal>>()
    val mealDetailsByNameLiveData: LiveData<List<Meal>> = _mealDetailsByNameLiveData
    fun getMealDetailsByName(mealName: String) {
        RetrofitInstance.api.getMealByName(mealName).enqueue(object :Callback<MealDetailsResponse> {
            override fun onResponse(call: Call<MealDetailsResponse>, response: Response<MealDetailsResponse>) {
                if (response.body() != null) {
                    _mealDetailsByNameLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealDetailsResponse>, t: Throwable) {
                Log.d("TAG", "meal details failure: ${t.message}")
            }
        })
    }

    //GET Categories
    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> =_categoriesLiveData
    fun getCategoriesList(){
        RetrofitInstance.api.getCategoriesList().enqueue(object: Callback<CategoriesResponse>{
            override fun onResponse(call: Call<CategoriesResponse>, response: Response<CategoriesResponse>) {
               if (response.body() != null){
                   _categoriesLiveData.value = response.body()!!.categories
               } else return
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                Log.d("TAG", "on categories Failure: ${t.message}")
            }
        })
    }
    //Getting all meals from local db
    fun getAllMeals():LiveData<List<Meal>> {
        return  mealDb.getMealDao().getAllMeals()
    }
    //Deleting a meal from Room
    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        mealDb.getMealDao().deleteMeal(meal)
    }
    //Upsert meal
    fun upsertMeal(meal: Meal){
        viewModelScope.launch {
            mealDb.getMealDao().upsertMeal(meal)
        }
    }
}