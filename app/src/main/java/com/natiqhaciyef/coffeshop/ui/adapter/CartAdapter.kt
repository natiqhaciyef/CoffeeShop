package com.natiqhaciyef.coffeshop.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerCartRowBinding
import com.natiqhaciyef.coffeshop.util.calendarFormatter
import java.util.*

class CartAdapter(var list: List<CartCoffeeModel>, val mContext: Context):
    RecyclerView.Adapter<CartAdapter.CartHolder>() {

    inner class CartHolder(val binding: RecyclerCartRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = RecyclerCartRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartHolder(binding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val view = holder.binding
        val cartCoffee = list[position]

        view.cartCoffeeNameText.text = cartCoffee.name
        view.cartCountText.text = "Order count: ${cartCoffee.count}"

        val today = calendarFormatter(Calendar.getInstance())
        if (today[1] == cartCoffee.date[1] && today[0] == cartCoffee.date[0] )
            view.cartDateText.text = "${cartCoffee.date[11]}${cartCoffee.date[12]}:${cartCoffee.date[14]}${cartCoffee.date[15]}"
        else
            view.cartCountText.text = "${cartCoffee.date}"

        val price = "%.2f".format(cartCoffee.totalPrice)
        view.cartTotalPriceText.text = "Price: $price"
        Glide.with(mContext).load(cartCoffee.image).into(view.cartCoffeeImageView)
    }

    override fun getItemCount(): Int = list.size
}