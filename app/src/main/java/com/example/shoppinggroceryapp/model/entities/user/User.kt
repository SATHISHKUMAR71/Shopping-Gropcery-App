package com.example.shoppinggroceryapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val userId:Int,
    val userImage:String,
    val userFirstName:String,
    val userLastName:String,
    val userEmail:String,
    val userPhone:String,
    val userPassword:String,
    val dateOfBirth:String,
    val isRetailer:Boolean
)