package com.natiqhaciyef.coffeshop.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.natiqhaciyef.coffeshop.R
import android.graphics.drawable.Drawable as Drawable1


fun ImageView.downloadFromUrl(url: String?, circularProgressDrawable: CircularProgressDrawable){
    val option = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}

fun ImageView.downloadAndRotateFromUrl(url: String?, circularProgressDrawable: CircularProgressDrawable){
    val option = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .transform(Rotate(90))
        .into(this)
}

fun placeHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 40f
        start()
    }
}