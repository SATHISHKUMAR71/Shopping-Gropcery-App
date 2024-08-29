package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.NumberPicker.OnScrollListener
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment.Companion.totalCost
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductAdapter2
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.io.File

class CartFragment(var searchBarTop:LinearLayout,var bottomNav:BottomNavigationView) : Fragment() {


    companion object{
        var viewPriceDetailData = MutableLiveData(0f)
        var cartItemsSize = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Cart Frag")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartList)
        var fileDir = File(requireContext().filesDir,"AppImages")
        val db = AppDatabase.getAppDatabase(requireContext()).getUserDao()
        val price = view.findViewById<MaterialButton>(R.id.viewPriceDetailsButton)
        val priceDetails = view.findViewById<LinearLayout>(R.id.linearLayout12)
        val noOfItems = view.findViewById<TextView>(R.id.priceDetailsMrpTotalItems)
        val totalAmount =view.findViewById<TextView>(R.id.priceDetailsMrpPrice)
        val discountedAmount = view.findViewById<TextView>(R.id.priceDetailsDiscountedAmount)
        val grandTotalAmount = view.findViewById<TextView>(R.id.priceDetailsTotalAmount)
        viewPriceDetailData.observe(viewLifecycleOwner){
            val str = "₹$it\nView Price Details"
            noOfItems.text = cartItemsSize.toString()
            price.text = str
        }
        viewPriceDetailData.value = 0f
        Thread{
            val list = db.getCartItems(MainActivity.cartId)
            for(cart in list){
                val totalItems = cart.totalItems
                val price = cart.unitPrice
                MainActivity.handler.post {
                    viewPriceDetailData.value = viewPriceDetailData.value!!+(totalItems * price)
                }
            }
        }.start()

//        Thread{
//            val cartList = db.getProductsByCartId(MainActivity.cartId)
//            println("CART SIZE: ${cartList.size}")
//            MainActivity.handler.post {
//                cartItemsSize= cartList.size
//                recyclerView.adapter = ProductListAdapter(this,cartList.toMutableList(),fileDir,searchBarTop,bottomNav,"C")
//                recyclerView.layoutManager = LinearLayoutManager(requireContext())
//            }
//        }.start()
        MainActivity.handler.post{
            val adapter = ProductListAdapter(this,fileDir,searchBarTop,bottomNav,"C")
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            db.getProductsByCartIdLiveData(MainActivity.cartId).observe(viewLifecycleOwner){
                println("Adapter Called: $it")
                adapter.setProducts(it)
            }
        }

        return view
    }
}