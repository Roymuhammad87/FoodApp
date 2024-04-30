package com.adrammedia.easyfood.database.retrofit

import com.adrammedia.easyfood.database.models.mealscategories.CategoriesResponse
import com.adrammedia.easyfood.database.models.popularmeals.PopularMeal
import com.adrammedia.easyfood.database.models.popularmeals.PopularMealsResponse
import com.adrammedia.easyfood.database.models.randommeal.MealDetailsResponse
import com.adrammedia.easyfood.database.models.randommeal.RandomMealResponse
import com.adrammedia.easyfood.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    //GET RANDOM MEAL
    @GET(Constants.RANDOM_MEAL_END_POINT)
    fun getRandomMeal():Call<RandomMealResponse>

    //GET MEAL DETAILS BY iD
    @GET(Constants.GET_MEAL_BY_ID)
    fun getMealDetails(@Query("i") mealId: String):Call<MealDetailsResponse>
    //GET MEAL DETAILS BY name
    @GET(Constants.GET_MEAL_BY_NAME)
    fun getMealByName(@Query("s") mealName: String):Call<MealDetailsResponse>

    //GET POPULAR MEALS
    @GET(Constants.GET_POPULAR_MEALS)
    fun getPopularMeals(@Query("c") categoryId: String):Call<PopularMealsResponse>

    //GET MEALS CATEGORIES
    @GET(Constants.GET_MEALS_CATEGORIES)
    fun getCategoriesList():Call<CategoriesResponse>
}