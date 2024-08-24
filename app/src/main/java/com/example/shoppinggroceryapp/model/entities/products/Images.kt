package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Images (
    @PrimaryKey
    val imageId:Int,
    val productId:Int,
    val images:String
)