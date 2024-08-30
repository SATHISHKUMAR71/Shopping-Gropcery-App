package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class OrderListAdapter(var orderedItems:MutableList<OrderDetails>):RecyclerView.Adapter<OrderListAdapter.OrderLayoutViewHolder>() {

    companion object{
        var cartWithProductList = mutableListOf<MutableList<CartWithProductData>>()
    }

    inner class OrderLayoutViewHolder(orderView:View):RecyclerView.ViewHolder(orderView){
        val productNames = orderView.findViewById<TextView>(R.id.orderedProductsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderLayoutViewHolder {
        return OrderLayoutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return orderedItems.size
    }

    override fun onBindViewHolder(holder: OrderLayoutViewHolder, position: Int) {
        if(orderedItems[position].paymentStatus=="Pending"){
            val screen = "Expected Delivery Date: ${orderedItems[position].deliveryDate}"
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).text = screen
        }
        else{
            val screen = "Delivered On: ${orderedItems[position].deliveryDate}"
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).text = screen
        }
        var productName=""
        var i =0
        for(cartWithProductData in cartWithProductList[position]){
            if(i==0){
                productName += cartWithProductData.productName+" (${cartWithProductData.totalItems}) "
                i=1
            }
            else{
                productName += ", "+cartWithProductData.productName+" (${cartWithProductData.totalItems})"
            }
        }
        holder.productNames.text = productName
    }


}