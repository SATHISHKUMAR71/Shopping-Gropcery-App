package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
//    foreignKeys = [ForeignKey(entity = Category::class,
//        parentColumns = ["categoryId"],
//        childColumns = ["categoryId"])]
//)
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