package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.ext.SdkExtensions
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.ProductListFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import java.io.File

class ProductListAdapter(var fragment: Fragment,var productList:List<Product>,var imageLauncher: ActivityResultLauncher<Intent>,
    val intent: Intent):RecyclerView.Adapter<ProductListAdapter.ProductLargeImageHolder>() {



    inner class ProductLargeImageHolder(productLargeView:View):RecyclerView.ViewHolder(productLargeView){
        val productImage = productLargeView.findViewById<ImageView>(R.id.productImageLong)
        val brandName = productLargeView.findViewById<TextView>(R.id.brandName)
        val productName = productLargeView.findViewById<TextView>(R.id.productNameLong)
        val productQuantity = productLargeView.findViewById<TextView>(R.id.productQuantity)
        val productExpiryDate = productLargeView.findViewById<TextView>(R.id.productExpiryDate)
        val productPrice = productLargeView.findViewById<TextView>(R.id.productPriceLong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLargeImageHolder {
        return ProductLargeImageHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout_long,parent,false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductLargeImageHolder, position: Int) {
        println("On Bind View Holder")
        holder.productName.text = productList[position].productName
        holder.productExpiryDate.text = productList[position].expiryDate
        holder.productQuantity.text = productList[position].productQuantity
        holder.productPrice.text = productList[position].price
        val url = (productList[position].mainImage)
        if(url.isNotEmpty()){
            println("Is URL NOT EMPTY")
            try{
                val fileDir = File(fragment.requireContext().filesDir,"AppImages")
                val bitmapFile = File(fileDir,"New Image")
                println(bitmapFile.absolutePath)
                val imagePath = File(url)
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
            holder.productImage.setImageDrawable(ContextCompat.getDrawable(fragment.requireContext(),R.drawable.gram_pulses))
        }

        holder.productImage.setOnClickListener {
            ProductListFragment.position = position
            ProductListFragment.clickObserver.value = productList[position]
            imageLauncher.launch(intent)
        }
    }
}