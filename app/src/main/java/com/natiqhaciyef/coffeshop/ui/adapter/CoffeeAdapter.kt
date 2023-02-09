package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCoffeeRowBinding

class CoffeeAdapter(val list: List<CoffeeModel>, val  mContext: Context):
    RecyclerView.Adapter<CoffeeAdapter.CoffeeHolder>() {

    inner class CoffeeHolder(val binding: RecyclerCoffeeRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeHolder {
        val binding = RecyclerCoffeeRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CoffeeHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeHolder, position: Int) {
        val view = holder.binding
        val coffee = list[position]

        Glide.with(mContext).load(coffee.image).into(view.coffeeImage)
        view.coffeeNameText.text = coffee.name
        view.coffeePriceText.text = "%.2f".format(coffee.price)
    }

    override fun getItemCount(): Int = list.size
}