package com.adrammedia.easyfood.database.retrofit

import com.adrammedia.easyfood.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api:MealsApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsApi::class.java)
    }
    //We can write the above code with this syntax
    /**
    lateinit var api: MealsApi
    init {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealsApi::class.java)
    }
    */
}