package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCoffeeRowBinding

class FavouriteAdapter(val list: List<CoffeeModel>, val mContext: Context):
    RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder>() {

    inner class FavouriteHolder(val binding: RecyclerCoffeeRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val binding = RecyclerCoffeeRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return FavouriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        val view = holder.binding
        val coffee = list[position]

        Glide.with(mContext).load(coffee.image).into(view.coffeeImage)
        view.coffeeNameText.text = coffee.name
        view.coffeePriceText.text = "%.2f".format(coffee.price)
    }

    override fun getItemCount(): Int = list.size
}