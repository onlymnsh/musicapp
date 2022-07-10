package com.example.practicaltestapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicaltestapplication.R

fun loadPreviewImage(view: ImageView, url: String) =
    Glide.with(view.context).load(url)
        .transform(RoundedCorners(12))
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.ic_image).error(R.drawable.ic_broken_image).into(view)
