package com.example.shoppinggroceryapp.model.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
//    foreignKeys = [
//        ForeignKey(entity = User::class,
//            parentColumns = ["userId"],
//            childColumns = ["userId"])
//    ]
//)
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