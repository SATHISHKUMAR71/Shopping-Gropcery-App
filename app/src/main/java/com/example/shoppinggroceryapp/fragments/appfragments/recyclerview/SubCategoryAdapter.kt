package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.model.entities.products.Category

class SubCategoryAdapter(var context: Context,var categoryList: List<Category>):RecyclerView.Adapter<SubCategoryAdapter.SubcategoryHolder>() {

    var size =0
    inner class SubcategoryHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val categoryName =itemView.findViewById<TextView>(R.id.subcategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryHolder {
        return SubcategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.sub_category_layout,parent,false))
    }

    override fun getItemCount(): Int {
        size = categoryList.size
        return if(size==0)1 else size
    }

    override fun onBindViewHolder(holder: SubcategoryHolder, position: Int) {
        if(size == 0){
            val text = "No Items in this Category"
            holder.categoryName.text = text
        }
        else{
            holder.categoryName.text = categoryList[position].categoryName
            holder.itemView.setOnClickListener {
                Toast.makeText(context,"Item Clicked",Toast.LENGTH_SHORT).show()
            }
        }

    }


}