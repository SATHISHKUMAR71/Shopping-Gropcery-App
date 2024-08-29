package com.example.shoppinggroceryapp.fragments.appfragments.orderfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.shoppinggroceryapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class OrderSummaryFragment(var searchBarTop:LinearLayout,var bottomNav:BottomNavigationView) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_order_summary, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        searchBarTop.visibility =View.GONE
        bottomNav.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        searchBarTop.visibility = View.VISIBLE
        bottomNav.visibility = View.VISIBLE
    }
}