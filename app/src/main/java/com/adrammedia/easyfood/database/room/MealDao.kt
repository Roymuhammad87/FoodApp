package com.adrammedia.easyfood.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrammedia.easyfood.database.models.randommeal.Meal

@Dao
interface MealDao {
    @Insert
    suspend fun upsertMeal(meal: Meal)
    @Delete
    suspend fun deleteMeal(meal: Meal)
    @Query("select * from meal_table where  strMeal like'%' ||:txt || '%'")
    fun searchForMeal(txt: String):LiveData<List<Meal>>
    @Query("select * from meal_table")
    fun getAllMeals():LiveData<List<Meal>>
}