package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinggroceryapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private lateinit var bottomNav:BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        bottomNav = view.findViewById(R.id.bottomNav)
        bottomNav.setOnItemReselectedListener {
            when(it.itemId){
                R.id.account -> println("Account Clicked Reselected")
                R.id.cart -> println("Cart Clicked Reselected")
                R.id.home -> println("Home Clicked Reselected")
                R.id.offer -> println("Offer CLicked Reselected")
                R.id.category -> println("Category Clicked Reselected")
            }
        }
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.account -> println("Account Clicked")
                R.id.cart -> println("Cart Clicked")
                R.id.home -> println("Home Clicked")
                R.id.offer -> println("Offer CLicked")
                R.id.category -> println("Category Clicked")
            }
            true
        }
        return view
    }

}