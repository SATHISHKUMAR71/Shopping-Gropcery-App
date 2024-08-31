package com.example.shoppinggroceryapp.model.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.products.ProductWithCategory
import com.example.shoppinggroceryapp.model.entities.user.User
import com.example.shoppinggroceryapp.model.entities.user.Address

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM Address WHERE (Address.userId=:userId)")
    fun getAddressListForUser(userId:Int):List<Address>

    @Query("SELECT * FROM USER")
    fun getAllUsers():List<User>

    @Query("SELECT * FROM Address")
    fun getAllAddress():List<Address>

    @Query("SELECT * FROM Product")
    fun getOnlyProducts():List<Product>

    @Query("SELECT * FROM PRODUCT")
    fun getOnlyProductsLiveData():LiveData<MutableList<Product>>

    @Query("SELECT * FROM user WHERE ((userEmail=:emailOrPhone OR userPhone=:emailOrPhone) AND (userPassword=:password))")
    fun getUser(emailOrPhone:String,password:String):User

    @Query("SELECT * FROM user WHERE ((userEmail=:emailOrPhone OR userPhone=:emailOrPhone))")
    fun getUserData(emailOrPhone:String):User

    @Query("SELECT * FROM Product WHERE Product.offer!='-1'")
    fun getOfferedProducts():List<Product>

    @Query("SELECT * FROM Product WHERE(Product.categoryName =:query)")
    fun getProductByCategory(query:String):List<Product>

    @Query("SELECT * FROM Product WHERE(Product.categoryName =:query)")
    fun getProductByCategoryLiveData(query:String):LiveData<MutableList<Product>>

    @Query("SELECT * FROM User JOIN Address ON User.userId = Address.userId WHERE User.userId=:id")
    fun getAddressDetailsForUser(id:Int):Map<User,List<Address>>


    @Query("SELECT * FROM Address WHERE Address.addressId=:addressId")
    fun getAddress(addressId:Int):Address

    @Query("SELECT Product.*,Category.parentCategoryName,Category.categoryDescription FROM Product JOIN Category ON Product.categoryName=Category.categoryName")
    fun getProducts():Map<Category,List<Product>>

    @Query("SELECT * FROM CartMapping WHERE CartMapping.userId=:userId and CartMapping.status='available'")
    fun getCartForUser(userId:Int):CartMapping

    @Insert
    fun addCartForUser(cartMapping:CartMapping)


    @Query("SELECT * From CartMapping Where CartMapping.userId=:userId and CartMapping.status!='available'")
    fun getCartId(userId:Int):List<CartMapping>

    @Query("SELECT * FROM Cart WHERE Cart.cartId=:cartId")
    fun getCartItems(cartId:Int):List<Cart>

    @Query("SELECT Product.* FROM Cart Join Product ON Product.productId = Cart.productId WHERE Cart.cartId=:cartId")
    fun getProductsByCartId(cartId:Int):List<Product>




    @Query("SELECT Product.* FROM Cart Join Product ON Product.productId = Cart.productId WHERE Cart.cartId=:cartId")
    fun getProductsByCartIdLiveData(cartId:Int):LiveData<MutableList<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemsToCart(cart:Cart)

    @Query("SELECT Product.* FROM Cart JOIN Product ON Cart.productId=Product.productId WHERE Cart.cartId-:cartId")
    fun getCartWithProducts(cartId:Int):List<Product>


    @Query("SELECT Product.mainImage AS mainImage,Product.productName AS productName,Product.productDescription as productDescription,Cart.totalItems as totalItems,Cart.unitPrice as unitPrice,Product.manufactureDate AS manufactureDate" +
            ",Product.expiryDate as expiryDate,Product.productQuantity as productQuantity FROM Cart Join Product ON Product.productId=Cart.productId WHERE Cart.cartId=:cartId")
    fun getProductsWithCartId(cartId:Int):List<CartWithProductData>


    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE CartMapping.userId=:userID")
    fun getOrdersForUser(userID:Int):List<OrderDetails>

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.cartId=:cartId")
    fun getOrder(cartId:Int):OrderDetails

    @Delete
    fun removeProductInCart(cart: Cart)

    @Update
    fun updateCartMapping(cartMapping: CartMapping)
    @Query("SELECT * FROM Cart WHERE Cart.cartId=:cartId and Cart.productId=:productId")
    fun getSpecificCart(cartId:Int,productId:Int):Cart
    @Update
    fun updateCartItems(cart: Cart)

}