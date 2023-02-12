package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CategoryModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCategoryRowBinding
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.CategoryClickListener

class CategoryAdapter(
    val mContext: Context,
    var list: List<CategoryModel>
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

        selected(category, view)
        holder.itemView.setOnClickListener {
            listener?.setOnCategorySelected(category)
            selected(category, view)
        }
    }

    fun selected(category: CategoryModel, view: RecyclerCategoryRowBinding){
        if (category.isChecked){
            view.constraintViewCategory.setBackgroundColor(mContext.getColor(R.color.brown))
            view.categoryNameText.setTextColor(mContext.getColor(R.color.white))
        }else{
            view.constraintViewCategory.setBackgroundColor(mContext.getColor(R.color.white))
            view.categoryNameText.setTextColor(mContext.getColor(R.color.dark_cool_green))
        }
    }

    fun onClick(listener: CategoryClickListener){
        this.listener = listener
    }

    override fun getItemCount(): Int = list.size

}