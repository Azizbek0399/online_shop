package com.example.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.CategoryModel

@Dao

interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items:List<CategoryModel>)

    @Query("DELETE from categories")
    fun deleteAll()


    @Query("select * from categories")
    fun getAllCategories(): List<CategoryModel>
}