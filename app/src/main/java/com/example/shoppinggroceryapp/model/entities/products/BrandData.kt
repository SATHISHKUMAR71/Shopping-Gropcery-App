package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Dao
import androidx.room.PrimaryKey

@Dao
data class BrandData(
    @PrimaryKey(autoGenerate = true)
    var brandId:Long,
    var brandName:String,
    var productId:Long
)