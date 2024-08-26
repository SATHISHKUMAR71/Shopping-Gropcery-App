package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory

class MainCategoryAdapter(var context: Context,var mainCategoryList: List<ParentCategory>):RecyclerView.Adapter<MainCategoryAdapter.MainCategoryHolder>() {

    private var expandedViews = mutableSetOf<Int>()
    inner class MainCategoryHolder(mainCategoryView:View):RecyclerView.ViewHolder(mainCategoryView){
        val invisibleView = itemView.findViewById<RecyclerView>(R.id.subCategoryRecyclerView)
        val addSymbol = itemView.findViewById<ImageView>(R.id.addSymbol)
        val parentCategoryName = itemView.findViewById<TextView>(R.id.parentCategoryName)
        val parentCategoryDescription = itemView.findViewById<TextView>(R.id.parentCategoryDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryHolder {
        return MainCategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_category_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return mainCategoryList.size
    }

    override fun onBindViewHolder(holder: MainCategoryHolder, position: Int) {

        val isExpanded = expandedViews.contains(holder.adapterPosition)
        println("IS EXPANDED: $isExpanded ${holder.adapterPosition}")
        holder.parentCategoryName.text = mainCategoryList[position].parentCategoryName
        holder.parentCategoryDescription.text = mainCategoryList[position].parentCategoryDescription
        holder.itemView.setOnClickListener {

                if(isExpanded){
                    expandedViews.remove(holder.adapterPosition)
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
                    val handler = Handler(Looper.getMainLooper())
                    expandedViews.add(holder.adapterPosition)
                    Thread{
                        val categoryList = AppDatabase.getAppDatabase(context).getProductDao().getChildCategoryList(mainCategoryList[position].parentCategoryName)
                        handler.post {
                            holder.addSymbol.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.remove_24px))
                            holder.invisibleView.adapter = SubCategoryAdapter(context,categoryList)
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
                    }.start()
                }
            }

    }

}