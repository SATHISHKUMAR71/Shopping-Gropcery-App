package com.example.shoppinggroceryapp.database

data class User(var firstName:String,
    var lastName:String,
    var emailAddress:String,
    var password:String,
    var phoneNumber:String,
    var profileImage:String,
    var isAdmin:Boolean)