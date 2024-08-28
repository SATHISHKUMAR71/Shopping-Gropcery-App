package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File


class OfferFragment(var searchbarTop:LinearLayout,var bottomNav:BottomNavigationView) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Offer Frag")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_offer, container, false)
        val offerList = view.findViewById<RecyclerView>(R.id.offerList)
        val fileDir = File(requireContext().filesDir,"AppImages")
        Thread{
            val offeredProductList = AppDatabase.getAppDatabase(requireContext()).getUserDao().getOfferedProducts()
            MainActivity.handler.post {
                offerList.adapter = ProductListAdapter(this,offeredProductList,null,fileDir,searchbarTop,bottomNav)
                offerList.layoutManager = LinearLayoutManager(context)
            }
        }.start()
        return view
    }

    override fun onResume() {
        super.onResume()
        searchbarTop.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        searchbarTop.visibility = View.VISIBLE
    }
}