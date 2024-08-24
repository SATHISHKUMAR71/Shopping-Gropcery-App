package com.example.shoppinggroceryapp.model.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = false)
    val id:CartId,
    val productId:Int,
    val totalItems:Int,
    val unitPrice:Float
)