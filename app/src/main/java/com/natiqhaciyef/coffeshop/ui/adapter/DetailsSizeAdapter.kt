package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.coffeshop.R
import com.natiqhaciyef.coffeshop.data.model.CategoryModel
import com.natiqhaciyef.coffeshop.data.model.SizeModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCategoryRowBinding
import com.natiqhaciyef.coffeshop.databinding.RecyclerSizeRowBinding
import com.natiqhaciyef.coffeshop.ui.adapter.behavior.SizeClickListener

class DetailsSizeAdapter(val mContext: Context, val list: List<SizeModel>) :
    RecyclerView.Adapter<DetailsSizeAdapter.SizeHolder>() {

    private var listener: SizeClickListener? = null

    inner class SizeHolder(val binding: RecyclerSizeRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeHolder {
        val binding = RecyclerSizeRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return SizeHolder(binding)
    }

    override fun onBindViewHolder(holder: SizeHolder, position: Int) {
        holder.binding.sizeText.text = list[position].name

        selected(list[position], holder.binding)
        holder.itemView.setOnClickListener {
            listener?.setOnSizeClickListener(list[position])
            selected(list[position], holder.binding)
        }
    }

    private fun selected(size: SizeModel, view: RecyclerSizeRowBinding){
        if (size.isChecked){
            view.sizeConstraint.setBackgroundColor(mContext.getColor(R.color.light_brown))
            view.sizeText.setTextColor(mContext.getColor(R.color.dark_cool_green))
        }else{
            view.sizeConstraint.setBackgroundColor(mContext.getColor(R.color.white))
            view.sizeText.setTextColor(mContext.getColor(R.color.light_black))
        }
    }

    override fun getItemCount(): Int = list.size

    fun onClick(listener: SizeClickListener){
        this.listener = listener
    }
}