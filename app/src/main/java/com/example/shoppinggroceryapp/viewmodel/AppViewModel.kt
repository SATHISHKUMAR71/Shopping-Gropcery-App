package com.example.shoppinggroceryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.dataclass.User

class AppViewModel:ViewModel() {

    companion object{
        var usersList:MutableList<User> = getList()

        private fun getList():MutableList<User>{
            val list:MutableList<User> = mutableListOf()
            list.add(User(firstName = "Sathish Kumar",
                lastName = "B",
                emailAddress = "satishmsd@gmail.com",
                password = "123",
                phoneNumber = "1234567890",
                profileImage = "",
                isAdmin = false))
            list.add(User(firstName = "admin",
                lastName = "",
                emailAddress = "admin@shop.com",
                password = "admin",
                phoneNumber = "1234567890",
                profileImage = "",
                isAdmin = true))
            return list
        }
    }
}