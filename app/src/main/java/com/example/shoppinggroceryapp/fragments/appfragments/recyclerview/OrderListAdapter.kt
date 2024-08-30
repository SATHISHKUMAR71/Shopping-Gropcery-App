package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails

class OrderListAdapter(var orderedItems:MutableList<OrderDetails>):RecyclerView.Adapter<OrderListAdapter.OrderLayoutViewHolder>() {


    inner class OrderLayoutViewHolder(orderView:View):RecyclerView.ViewHolder(orderView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderLayoutViewHolder {
        return OrderLayoutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return orderedItems.size
    }

    override fun onBindViewHolder(holder: OrderLayoutViewHolder, position: Int) {

    }


}