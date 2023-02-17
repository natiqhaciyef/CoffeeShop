package com.natiqhaciyef.coffeshop.ui.adapter

import android.app.Notification
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natiqhaciyef.coffeshop.data.model.NotificationModel
import com.natiqhaciyef.coffeshop.databinding.RecyclerNotificationRowBinding

class NotificationAdapter(val list: List<NotificationModel>, val mContext: Context):
    RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    inner class NotificationHolder(val binding: RecyclerNotificationRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val binding = RecyclerNotificationRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return NotificationHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val view = holder.binding
        val notification = list[position]

        view.notificationTitleText.text = notification.title
        view.notificationDescriptionText.text = notification.description
        Glide.with(mContext).load(notification.image).into(view.notificationCoffeeImageView)
    }

    override fun getItemCount(): Int = list.size
}