/*
 * Created by WonJongSeong on 2019-04-12
 */

package com.example.taxishare.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView : ImageView, url: String) {
        Log.d("Test", url)
        Glide.with(imageView.context)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}