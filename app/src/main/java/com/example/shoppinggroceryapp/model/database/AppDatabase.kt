package com.example.shoppinggroceryapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.deals.Deals
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest
import com.example.shoppinggroceryapp.model.entities.help.FAQ
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.example.shoppinggroceryapp.model.entities.user.User

@Database(entities = [User::class,Address::class, Product::class,Images::class,ParentCategory::class,Category::class,Deals::class,FAQ::class,CustomerRequest::class], version = 1)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getUserDao():UserDao
    abstract fun getRetailerDao():RetailerDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null
        fun getAppDatabase(context: Context):AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fresh_cart_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}