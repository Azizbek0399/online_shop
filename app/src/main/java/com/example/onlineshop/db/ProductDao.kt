package com.example.onlineshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.ProductModel

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items:List<ProductModel>)

    @Query("DELETE from products")
    fun deleteAll()


    @Query("select * from products")
    fun getAllProducts():List<ProductModel>
}