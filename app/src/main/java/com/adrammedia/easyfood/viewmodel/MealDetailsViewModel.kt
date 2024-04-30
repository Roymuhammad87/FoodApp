package com.adrammedia.easyfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrammedia.easyfood.database.models.randommeal.Meal
import com.adrammedia.easyfood.database.models.randommeal.MealDetailsResponse
import com.adrammedia.easyfood.database.retrofit.RetrofitInstance
import com.adrammedia.easyfood.database.room.MealDb
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel(private val mealDb: MealDb) : ViewModel() {
    private val _mealDetailsLiveData = MutableLiveData<Meal>()
   val mealDetailsLiveData: LiveData<Meal> = _mealDetailsLiveData
    //Getting Meals by id from api
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

    //Saving a Meal in Room

    fun upsertMeal(meal: Meal){
        viewModelScope.launch {
            mealDb.getMealDao().upsertMeal(meal)
        }
    }
    //Deleting a Meal in Room
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDb.getMealDao().deleteMeal(meal)
        }
    }
    //Searching for a Meal in Room
    fun searchForAmeal(txt:String) {
        mealDb.getMealDao().searchForMeal(txt)
    }


}