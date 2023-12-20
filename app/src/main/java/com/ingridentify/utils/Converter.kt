package com.ingridentify.utils

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }
}