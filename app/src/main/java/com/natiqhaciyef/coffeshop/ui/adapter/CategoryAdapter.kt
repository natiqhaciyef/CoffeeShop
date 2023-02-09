package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CategoryModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCategoryRowBinding
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.CategoryClickListener

class CategoryAdapter(
    val mContext: Context,
    val list: List<CategoryModel>
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    var listener: CategoryClickListener? = null

    inner class CategoryHolder(val binding: RecyclerCategoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            RecyclerCategoryRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val view = holder.binding
        val category = list[position]
        view.categoryNameText.text = category.name

        if (category.isChecked){
            view.constraintViewCategory.setBackgroundColor(mContext.getColor(R.color.brown))
            view.categoryNameText.setTextColor(mContext.getColor(R.color.white))
        }

        holder.itemView.setOnClickListener {
            for (element in list){
                if (element.name != category.name)
                    element.isChecked = false
            }
            listener?.setOnCategorySelected(category)
        }
    }

    fun onClick(listener: CategoryClickListener){
        this.listener = listener
    }

    override fun getItemCount(): Int = list.size
}