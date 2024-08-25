package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.shoppinggroceryapp.R

class MainCategoryAdapter(var context: Context):RecyclerView.Adapter<MainCategoryAdapter.MainCategoryHolder>() {

    inner class MainCategoryHolder(mainCategoryView:View):RecyclerView.ViewHolder(mainCategoryView){
        val invisibleView = itemView.findViewById<RecyclerView>(R.id.subCategoryRecyclerView)
        val addSymbol = itemView.findViewById<ImageView>(R.id.addSymbol)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryHolder {
        return MainCategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_category_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MainCategoryHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if(holder.adapterPosition == position){
                val isVisible = holder.invisibleView.visibility == View.VISIBLE
                if(isVisible){
//                    TransitionManager.beginDelayedTransition(holder.invisibleView,AutoTransition())
                    holder.invisibleView.animate()
                        .alpha(0f)
                        .scaleY(0f)
                        .setDuration(100)
                        .withEndAction {
                            holder.invisibleView.visibility = View.GONE
                            holder.addSymbol.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.add_24px))
                        }
                }
                else{
                    holder.addSymbol.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.remove_24px))
                    holder.invisibleView.adapter = SubCategoryAdapter(context)
                    holder.invisibleView.layoutManager = LinearLayoutManager(context)
//                    TransitionManager.beginDelayedTransition(holder.invisibleView,AutoTransition())
                    holder.invisibleView.visibility = View.VISIBLE
                    holder.invisibleView.alpha = 0f
                    holder.invisibleView.scaleY = 0f
                    holder.invisibleView.animate()
                        .alpha(1f)
                        .scaleY(1f)
                        .setDuration(100)
                }
            }
        }
    }

}