package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.coffeshop.databinding.RecyclerSizeRowBinding

class DetailsSizeAdapter(val mContext: Context, val list: List<String>) :
    RecyclerView.Adapter<DetailsSizeAdapter.SizeHolder>() {

    inner class SizeHolder(val binding: RecyclerSizeRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeHolder {
        val binding = RecyclerSizeRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return SizeHolder(binding)
    }

    override fun onBindViewHolder(holder: SizeHolder, position: Int) {
        holder.binding.sizeText.text = list[position]
    }

    override fun getItemCount(): Int = list.size
}