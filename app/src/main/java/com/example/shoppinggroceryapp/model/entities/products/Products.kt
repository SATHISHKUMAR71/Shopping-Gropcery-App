package com.example.shoppinggroceryapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Products(
    @PrimaryKey
    val productId:Int,
    val categoryId:Int,
    val productName:String,
    val productDescription:String,
    val price:String,
    val offer:String,
    val productQuantity:String,
    val mainImage:String,
    val isVeg:Boolean,
    val manufactureDate:String,
    val expiryDate:String,
    val availableItems:Int
)