package com.ramo.simplegithub.customview.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.lang.String


@BindingAdapter(
    "image_url",
    "image_placeholder",
    "image_error",
    "image_corner_radius",
    requireAll = false
)
fun loadImage(
    imageView: ImageView,
    url: String?,
    placeholder: Drawable?,
    error: Drawable?,
    cornerRadius: Int?
) {
    val options = RequestOptions().placeholder(placeholder).error(error)
    cornerRadius?.let {
        options.transform(CenterCrop(), RoundedCorners(it))
    }
    Glide.with(imageView.context)
        .load(url)
        .apply(
            options
        )
        .into(imageView)
}
