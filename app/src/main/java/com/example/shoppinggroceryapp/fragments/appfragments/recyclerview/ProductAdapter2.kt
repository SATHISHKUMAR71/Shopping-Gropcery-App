package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.graphics.BitmapFactory
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.CartFragment
import com.example.shoppinggroceryapp.fragments.appfragments.diffutil.CartItemsDiffUtil
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductDetailFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.io.File

class ProductAdapter2(var fragment: Fragment,
                         private var file: File, private var searchBarTop:LinearLayout, private var bottomNav: BottomNavigationView,
                         private var tag:String):RecyclerView.Adapter<ProductAdapter2.ProductLargeImageHolder1>() {

    private var userDb:UserDao =
        AppDatabase.getAppDatabase(fragment.requireContext()).getUserDao()
    private var productList:MutableList<Product> = mutableListOf()
    var size = 0
    private var countList = mutableListOf<Int>()
    init {
        for(i in 0..<productList.size){
            countList.add(i,0)
        }
    }
    inner class ProductLargeImageHolder1(productLargeView:View):RecyclerView.ViewHolder(productLargeView){
        val productImage = productLargeView.findViewById<ImageView>(R.id.productImageLong)
        val brandName = productLargeView.findViewById<TextView>(R.id.brandName)
        val productName = productLargeView.findViewById<TextView>(R.id.productNameLong)
        val productQuantity = productLargeView.findViewById<TextView>(R.id.productQuantity)
        val productExpiryDate = productLargeView.findViewById<TextView>(R.id.productExpiryDate)
        val productPrice = productLargeView.findViewById<TextView>(R.id.productPriceLong)
        val offer = productLargeView.findViewById<TextView>(R.id.offerText)
        val productAddRemoveLayout:LinearLayout = productLargeView.findViewById(R.id.productAddRemoveLayout)
        val productAddOneTime:MaterialButton = productLargeView.findViewById(R.id.productAddLayoutOneTime)
        val totalItems:TextView = productLargeView.findViewById(R.id.totalItemsAdded)
        val addSymbolButton:ImageButton = productLargeView.findViewById(R.id.productAddSymbolButton)
        val removeSymbolButton:ImageButton = productLargeView.findViewById(R.id.productRemoveSymbolButton)
        val productMrpText:TextView = productLargeView.findViewById(R.id.productMrpText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLargeImageHolder1 {
        return ProductLargeImageHolder1(LayoutInflater.from(parent.context).inflate(R.layout.product_layout_long,parent,false))
    }

    override fun getItemCount(): Int {
        size = productList.size
        return if(size==0)0 else size
    }

    override fun onBindViewHolder(holder: ProductLargeImageHolder1, position: Int) {
        println("**** On PRODUCT LIST ADAPTER $tag $size")
        if(size==0){
            Toast.makeText(fragment.requireContext(),"No Items in this Category",Toast.LENGTH_SHORT).show()
        }
        else{
            Thread{
                println("**** In Thread$productList $position ${holder.absoluteAdapterPosition}")
                val cart:Cart? = userDb.getSpecificCart(MainActivity.cartId,productList[position].productId.toInt())
                println("AT Thread DATAS")
                if(cart!=null){
                    MainActivity.handler.post {
                        holder.productAddOneTime.visibility = View.GONE
                        holder.productAddRemoveLayout.visibility = View.VISIBLE
                        countList[position] = cart.totalItems
                        println("AT Thread DATAS IF $countList")
                        holder.totalItems.text = cart.totalItems.toString()
                    }
                }
                else{
                    MainActivity.handler.post {
                        println("AT Thread DATAS ELSE")
                        holder.productAddOneTime.visibility = View.VISIBLE
                        holder.productAddRemoveLayout.visibility = View.GONE
                        countList[position] = 0
                        holder.totalItems.text = "0"
                    }
                }
                println("IN THREAD DATAS ${countList.size} ${productList.size } $countList $productList ")
            }.start()
            println("On Bind View Holder ${productList[position].offer}")
            Thread{
                val brand = AppDatabase.getAppDatabase(fragment.requireContext()).getRetailerDao().getBrandName(productList[position].brandId)
                MainActivity.handler.post {
                    holder.brandName.text = brand
                }
            }.start()
            if(productList[position].offer!="-1"){
                val str = "MRP ₹"+productList[position].price
                holder.productMrpText.text = str
                holder.productMrpText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.productMrpText.visibility = View.VISIBLE
                holder.offer.visibility = View.VISIBLE
                holder.offer.text = productList[position].offer
            }
            else{
                val str = "MRP"
                holder.productMrpText.text = str
                holder.productMrpText.paintFlags = 0
                holder.offer.text = null
                holder.offer.visibility = View.GONE
            }
            holder.productName.text = productList[position].productName
            holder.productExpiryDate.text = productList[position].expiryDate
            holder.productQuantity.text = productList[position].productQuantity
            val price = "₹" + productList[position].price
            holder.productPrice.text = price
            val url = (productList[position].mainImage)
            if(url.isNotEmpty()){
                try{
                    val imagePath = File(file,url)
                    println(imagePath.absolutePath)
                    val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath)
                    holder.productImage.setImageBitmap(bitmap)
                }
                catch (e:Exception){
                    holder.productImage.setImageDrawable(ContextCompat.getDrawable(fragment.requireContext(),R.drawable.gram_pulses))
                    println("Error: $e")
                }
            }
            else{
                holder.productImage.setImageDrawable(ContextCompat.getDrawable(fragment.requireContext(),R.drawable.add_photo_alternate_24px))
            }
            setUpListeners(holder,position)
        }
    }

    private fun setUpListeners(holder: ProductLargeImageHolder1, position: Int) {
        holder.itemView.setOnClickListener {
            fragment.parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,ProductDetailFragment(searchBarTop,bottomNav))
                .addToBackStack("Product Detail Fragment")
                .commit()
        }


        holder.removeSymbolButton.setOnClickListener {
            if(holder.adapterPosition==position) {
                val count = --countList[position]
                if (tag == "P") {
                    ProductListFragment.totalCost.value =
                        ProductListFragment.totalCost.value!! - productList[position].price.toFloat()
                } else if (tag == "C") {
                    CartFragment.viewPriceDetailData.value =
                        CartFragment.viewPriceDetailData.value!! - productList[position].price.toFloat()
                }
                Thread {
                    userDb.addItemsToCart(
                        Cart(
                            MainActivity.cartId,
                            productList[position].productId.toInt(),
                            count,
                            productList[position].price.toFloat()
                        )
                    )
                }.start()
                println("COUNT $count $tag ${tag == "P"} ${tag == "C"}")
                if (count == 0) {
                    if (tag == "P" || tag == "O") {
                        println("##### ${holder.adapterPosition} $position")
                        holder.totalItems.text = "0"
                        Thread {
                            val cart = userDb.getSpecificCart(
                                MainActivity.cartId,
                                productList[position].productId.toInt()
                            )
                            userDb.removeProductInCart(cart)
                        }.start()
                        holder.productAddRemoveLayout.visibility = View.GONE
                        holder.productAddOneTime.visibility = View.VISIBLE
                    } else if (tag == "C") {
                        Thread {
                            val cart = userDb.getSpecificCart(
                                MainActivity.cartId,
                                productList[position].productId.toInt()
                            )
                            productList.removeAt(position)
                            countList.removeAt(position)
                            userDb.removeProductInCart(cart)
                            CartFragment.cartItemsSize -= 1
                            MainActivity.handler.post {
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position,productList.size)
                                println("DATAS ${countList.size} ${productList.size } $countList $productList ")
                            }
                        }.start()
                    }
                } else {
                    holder.totalItems.text = count.toString()
                }
            }
        }

        holder.addSymbolButton.setOnClickListener {
            if (holder.adapterPosition == position) {
                val count = ++countList[position]
                if (tag == "P") {
                    ProductListFragment.totalCost.value =
                        ProductListFragment.totalCost.value!! + productList[position].price.toFloat()
                } else if (tag == "C") {
                    CartFragment.viewPriceDetailData.value =
                        CartFragment.viewPriceDetailData.value!! + productList[position].price.toFloat()
                }
                Thread {
                    userDb.addItemsToCart(
                        Cart(
                            MainActivity.cartId,
                            productList[position].productId.toInt(),
                            count,
                            productList[position].price.toFloat()
                        )
                    )
                }.start()
                holder.totalItems.text = count.toString()
            }
        }
        holder.productAddOneTime.setOnClickListener {
            if (holder.adapterPosition == position) {
                val count = ++countList[position]
                holder.totalItems.text = count.toString()
                if (tag == "P") {
                    ProductListFragment.totalCost.value =
                        ProductListFragment.totalCost.value!! + productList[position].price.toFloat()
                } else if (tag == "C") {

                    CartFragment.viewPriceDetailData.value =
                        CartFragment.viewPriceDetailData.value!! + productList[position].price.toFloat()
                }
                Thread {
                    userDb.addItemsToCart(
                        Cart(
                            MainActivity.cartId,
                            productList[position].productId.toInt(),
                            count,
                            productList[position].price.toFloat()
                        )
                    )
                }.start()
                holder.productAddRemoveLayout.visibility = View.VISIBLE
                holder.productAddOneTime.visibility = View.GONE
            }
        }
    }

    fun setProducts(newList:List<Product>){
        println("Adapter Called: Set Products Called $productList $newList")
        val diffUtil = CartItemsDiffUtil(productList,newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        countList.clear()
        for(i in 0..newList.size){
            countList.add(i,0)
        }
        productList.clear()
        productList.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }
}