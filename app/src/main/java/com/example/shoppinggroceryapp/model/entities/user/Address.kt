package com.example.shoppinggroceryapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address (
    @PrimaryKey
    val addressId:Int,
    val userId:Int,
    val buildingName:String,
    val streetName:String,
    val city:String,
    val state:String,
    val country:String,
    val postalCode:String
)