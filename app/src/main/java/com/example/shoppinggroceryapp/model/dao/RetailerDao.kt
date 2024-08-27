package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.model.entities.products.BrandData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product

@Dao
interface RetailerDao:UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParentCategory(parentCategory: ParentCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewBrand(brandData: BrandData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM ParentCategory WHERE ParentCategory.parentCategoryName=:parentCategoryName")
    fun getParentCategory(parentCategoryName:String):ParentCategory

    @Query("SELECT * FROM Category WHERE Category.categoryName=:subCategoryName")
    fun getSubCategory(subCategoryName:String):Category

}