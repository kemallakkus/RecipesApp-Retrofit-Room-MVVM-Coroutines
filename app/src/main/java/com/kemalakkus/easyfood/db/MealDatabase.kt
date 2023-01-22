package com.kemalakkus.easyfood.db

import android.content.Context
import androidx.room.*
import com.kemalakkus.easyfood.models.Meal
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase: RoomDatabase() {

    abstract fun mealDao() : MealDao

    companion object{

        @Volatile
        private var instance : MealDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(lock){

            instance ?: makeDatabase(context).also {
                instance = it
            }

        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,MealDatabase::class.java,"meal.db"
        ).fallbackToDestructiveMigration().build()

    }

}