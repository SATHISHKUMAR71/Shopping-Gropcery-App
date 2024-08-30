package com.example.shoppinggroceryapp.fragments.appfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.example.shoppinggroceryapp.MainActivity.Companion.cartId
import com.example.shoppinggroceryapp.MainActivity.Companion.userEmail
import com.example.shoppinggroceryapp.MainActivity.Companion.userFirstName
import com.example.shoppinggroceryapp.MainActivity.Companion.userId
import com.example.shoppinggroceryapp.MainActivity.Companion.userLastName
import com.example.shoppinggroceryapp.MainActivity.Companion.userPhone
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.AccountFragment
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView


class InitialFragment : Fragment() {

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
        val view =  inflater.inflate(R.layout.fragment_initial, container, false)
        bottomNav = view.findViewById(R.id.bottomNav)
        searchBar = view.findViewById(R.id.searchBar)
        searchView = view.findViewById(R.id.searchView)
        searchView.setupWithSearchBar(searchBar)

        val pref = requireActivity().getSharedPreferences("freshCart", Context.MODE_PRIVATE)
        userFirstName = pref.getString("userFirstName","User").toString()
        userLastName = pref.getString("userLastName","User").toString()
        userId = pref.getString("userId","userId").toString()
        userEmail = pref.getString("userEmail","userEmail").toString()
        userPhone = pref.getString("userPhone","userPhone").toString()
        val searchBarTop = view.findViewById<LinearLayout>(R.id.searchBarTop)

        parentFragmentManager.registerFragmentLifecycleCallbacks(object :FragmentManager.FragmentLifecycleCallbacks(){
            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                when (f){
                    is AccountFragment -> bottomNav.menu.findItem(R.id.account).isChecked = true
                    is CategoryFragment -> bottomNav.menu.findItem(R.id.category).isChecked = true
                    is HomeFragment -> bottomNav.menu.findItem(R.id.homeMenu).isChecked = true
                    is CartFragment -> bottomNav.menu.findItem(R.id.cart).isChecked = true
                    is OfferFragment -> bottomNav.menu.findItem(R.id.offer).isChecked = true
                }
            }
        },true)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentMainLayout,HomeFragment())
            .commit()
        bottomNav.setOnItemReselectedListener {
        }
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.account -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout, AccountFragment(searchBarTop))
                        .addToBackStack("Account Fragment")
                        .commit()
                }
                R.id.cart -> {
                    parentFragmentManager.beginTransaction()
//                        .setCustomAnimations(
//                            R.anim.slide_in,
//                            R.anim.fade_out,
//                            R.anim.fade_in,
//                            R.anim.slide_out
//                        )
                        .replace(R.id.fragmentMainLayout,CartFragment(searchBarTop,bottomNav))
                        .addToBackStack("Cart Fragment")
                        .commit()
                }
                R.id.homeMenu -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,HomeFragment())
                        .addToBackStack("Initial Fragment")
                        .commit()
                }
                R.id.offer -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,OfferFragment(searchBarTop,bottomNav))
                        .addToBackStack("Offer Fragment")
                        .commit()
                }
                R.id.category -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainLayout,CategoryFragment(searchBarTop,bottomNav))
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