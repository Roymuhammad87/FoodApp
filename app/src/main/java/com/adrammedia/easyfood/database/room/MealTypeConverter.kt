package com.adrammedia.easyfood.database.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToStr(any: Any?):String{
        if (any == null){
            return ""
        }
        return any as String
    }
    @TypeConverter
    fun fromStrToAny(string: String?):String{
        if (string== null){
            return ""
        }
        return string
    }
}