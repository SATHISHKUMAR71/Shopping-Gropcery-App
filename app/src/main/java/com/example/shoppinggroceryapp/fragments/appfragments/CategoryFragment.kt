package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.MainCategoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class CategoryFragment() : Fragment() {

    private lateinit var mainCategoryRV:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Category Frag")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        val adapter = MainCategoryAdapter(requireContext())
        mainCategoryRV = view.findViewById(R.id.categoryRecyclerView)
        mainCategoryRV.adapter = adapter
        mainCategoryRV.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}