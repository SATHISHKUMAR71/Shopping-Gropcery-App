package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParentCategory(
    @PrimaryKey
    val parentCategoryId:Int,
    val parentCategoryName:String,
    val parentCategoryImage:String,
    val parentCategoryDescription:String,
    val isEssential:Boolean
)