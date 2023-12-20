package com.ingridentify.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ingridentify.data.local.dao.RecipeDao
import com.ingridentify.data.local.dao.RemoteKeysDao
import com.ingridentify.data.local.entity.RecipeEntity
import com.ingridentify.data.local.entity.RemoteKeys
import com.ingridentify.utils.Converter

@Database(
    entities = [
        RecipeEntity::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class IngridentifyDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var instance: IngridentifyDatabase? = null

        fun getInstance(context: Context): IngridentifyDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    IngridentifyDatabase::class.java,
                    "ingridentify.db"
                ).build().apply { instance = this }
            }
    }
}