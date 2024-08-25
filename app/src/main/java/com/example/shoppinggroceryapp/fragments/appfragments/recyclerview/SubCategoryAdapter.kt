package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R

class SubCategoryAdapter(var context: Context):RecyclerView.Adapter<SubCategoryAdapter.SubcategoryHolder>() {

    inner class SubcategoryHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryHolder {
        return SubcategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.sub_category_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: SubcategoryHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Item Clicked",Toast.LENGTH_SHORT).show()
        }
    }


}