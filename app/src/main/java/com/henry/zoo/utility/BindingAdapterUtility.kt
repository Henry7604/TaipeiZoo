package com.henry.zoo.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 *
 * @author Henry
 */
object BindingAdapterUtility {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, imageUrl: String) {
        GlideApp.with(imageView.context).load(imageUrl).into(imageView)
    }
}