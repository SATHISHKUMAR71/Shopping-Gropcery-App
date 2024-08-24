package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinggroceryapp.model.entities.user.User
import com.example.shoppinggroceryapp.model.entities.user.Address

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(address: Address)

    @Query("SELECT * FROM USER")
    suspend fun getAllUsers():List<User>

    @Query("SELECT * FROM Address")
    suspend fun getAllAddress():List<Address>

    @Query("SELECT * FROM User JOIN Address ON User.userId = Address.userId")
    suspend fun getAddressDetailsForUser():Map<User,List<Address>>
}