package com.natiqhaciyef.coffeshop.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


val scope = CoroutineScope(Dispatchers.IO)
fun imageSizeCheckerAndDownloader(imageView: ImageView, url: String, context: Context) {
    scope.launch {
        val image = Picasso.get().load(url).get()
        withContext(Dispatchers.Main) {
            if (image.width < image.height)
                imageView.downloadAndRotateFromUrl(url, placeHolder(context))
            else
                imageView.downloadFromUrl(url, placeHolder(context))
        }
    }
}

fun imageDownloader(imageView: ImageView, url: String, context: Context) {
    scope.launch {
        imageView.downloadFromUrl(url, placeHolder(context))
    }
}