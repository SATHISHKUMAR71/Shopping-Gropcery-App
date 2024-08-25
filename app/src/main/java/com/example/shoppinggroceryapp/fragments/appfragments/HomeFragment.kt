package com.example.shoppinggroceryapp.fragments.appfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView


class HomeFragment : Fragment() {

    private lateinit var bottomNav:BottomNavigationView
    private lateinit var searchView:SearchView
    private lateinit var searchBar:SearchBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Home Frag")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        bottomNav = view.findViewById(R.id.bottomNav)
        searchBar = view.findViewById(R.id.searchBar)
        searchView = view.findViewById(R.id.searchView)
        searchView.setupWithSearchBar(searchBar)

        parentFragmentManager.registerFragmentLifecycleCallbacks(object :FragmentManager.FragmentLifecycleCallbacks(){
            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                when (f){
                    is AccountFragment -> bottomNav.menu.findItem(R.id.account).isChecked = true
                    is CategoryFragment -> bottomNav.menu.findItem(R.id.category).isChecked = true
                    is InitialFragment -> bottomNav.menu.findItem(R.id.homeMenu).isChecked = true
                    is CartFragment -> bottomNav.menu.findItem(R.id.cart).isChecked = true
                    is OfferFragment -> bottomNav.menu.findItem(R.id.offer).isChecked = true
                }
            }
        },true)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentMainLayout,InitialFragment())
            .commit()
        bottomNav.setOnItemReselectedListener {
        }
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.account -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,AccountFragment())
                        .addToBackStack("Account Fragment")
                        .commit()
                }
                R.id.cart -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,CartFragment())
                        .addToBackStack("Cart Fragment")
                        .commit()
                }
                R.id.homeMenu -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,InitialFragment())
                        .addToBackStack("Initial Fragment")
                        .commit()
                }
                R.id.offer -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,OfferFragment())
                        .addToBackStack("Offer Fragment")
                        .commit()
                }
                R.id.category -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,CategoryFragment())
                        .addToBackStack("Category Fragment")
                        .commit()
                }
            }
            true
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        println("Home On Resume")
    }

    override fun onPause() {
        super.onPause()
        println("Home On Pause")
    }
}