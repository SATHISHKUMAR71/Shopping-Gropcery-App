package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductDetailFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class ProductListAdapter(var fragment: Fragment, private var productList:List<Product>,
                         private var file: File, private var searchBarTOp:LinearLayout, private var bottomNav: BottomNavigationView):RecyclerView.Adapter<ProductListAdapter.ProductLargeImageHolder>() {

    var size = 0
    inner class ProductLargeImageHolder(productLargeView:View):RecyclerView.ViewHolder(productLargeView){
        val productImage = productLargeView.findViewById<ImageView>(R.id.productImageLong)
        val brandName = productLargeView.findViewById<TextView>(R.id.brandName)
        val productName = productLargeView.findViewById<TextView>(R.id.productNameLong)
        val productQuantity = productLargeView.findViewById<TextView>(R.id.productQuantity)
        val productExpiryDate = productLargeView.findViewById<TextView>(R.id.productExpiryDate)
        val productPrice = productLargeView.findViewById<TextView>(R.id.productPriceLong)
        val offer = productLargeView.findViewById<TextView>(R.id.offerText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLargeImageHolder {
        return ProductLargeImageHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout_long,parent,false))
    }

    override fun getItemCount(): Int {
        size = productList.size
        return if(size==0)1 else size
    }

    override fun onBindViewHolder(holder: ProductLargeImageHolder, position: Int) {
        if(size==0){
            Toast.makeText(fragment.requireContext(),"No Items in this Category",Toast.LENGTH_SHORT).show()
        }
        else{
            println("On Bind View Holder ${productList[position].offer}")
            Thread{
                val brand = AppDatabase.getAppDatabase(fragment.requireContext()).getRetailerDao().getBrandName(productList[position].brandId)
                MainActivity.handler.post {
                    holder.brandName.text = brand
                }
            }.start()
            if(productList[position].offer!="-1"){
                holder.offer.visibility = View.VISIBLE
                holder.offer.text = productList[position].offer
            }
            else{
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
                println("Is URL NOT EMPTY")
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

            holder.itemView.setOnClickListener {
                fragment.parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentMainLayout,ProductDetailFragment(searchBarTOp,bottomNav))
                    .addToBackStack("Product Detail Fragment")
                    .commit()
            }
        }
    }
}