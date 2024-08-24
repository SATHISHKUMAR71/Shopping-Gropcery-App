package com.example.shoppinggroceryapp.model.entities.help

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FAQ (
    @PrimaryKey
    val faqId:Int,
    val question:String,
    val answer:String
)