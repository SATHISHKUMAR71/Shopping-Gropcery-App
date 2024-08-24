package com.example.shoppinggroceryapp.model.entities.products

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
//    foreignKeys = [ForeignKey(
//        entity = ParentCategory::class,
//        parentColumns = ["parentCategoryId"],
//        childColumns = ["parentCategoryId"]
//    )]
//)
data class Category(
    @PrimaryKey
    val categoryId:Int,
    val parentCategoryId:Int,
    val categoryName:String,
    val categoryDescription:String
)