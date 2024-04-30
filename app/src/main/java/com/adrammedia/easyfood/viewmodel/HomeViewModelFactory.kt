package com.adrammedia.easyfood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrammedia.easyfood.database.room.MealDb

class HomeViewModelFactory(private val mealDb: MealDb):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDb) as T
    }
}