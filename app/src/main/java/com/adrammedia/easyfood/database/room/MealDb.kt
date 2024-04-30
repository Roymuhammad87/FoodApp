package com.adrammedia.easyfood.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrammedia.easyfood.database.models.randommeal.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDb:RoomDatabase() {
    abstract fun getMealDao():MealDao

    companion object {
        @Volatile
        private var INSTANCE:MealDb?= null
        @Synchronized
        fun getInstance(context: Context):MealDb {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDb::class.java,
                    "meals.db"
                   ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDb
        }
    }
}