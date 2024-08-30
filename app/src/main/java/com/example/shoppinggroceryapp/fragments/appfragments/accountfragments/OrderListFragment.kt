package com.example.shoppinggroceryapp.fragments.appfragments.accountfragments

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
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.OrderListAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class OrderListFragment(var searchBarTop:LinearLayout,var bottomnav:BottomNavigationView) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_order_list, container, false)
        Thread{
            val orderedItems = AppDatabase.getAppDatabase(requireContext()).getRetailerDao().getOrdersForUser(MainActivity.userId.toInt())
            MainActivity.handler.post {
                val orderList = view.findViewById<RecyclerView>(R.id.orderList)
                orderList.adapter = OrderListAdapter(orderedItems.toMutableList())
                orderList.layoutManager = LinearLayoutManager(context)
            }
        }.start()

        view.findViewById<MaterialToolbar>(R.id.materialToolbarOrderList).setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        searchBarTop.visibility = View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        searchBarTop.visibility = View.GONE
    }
}