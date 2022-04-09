package com.ramo.simplegithub.customview.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.ramo.simplegithub.core.ext.gone
import com.ramo.simplegithub.core.ext.visible

@BindingAdapter(
    "visible"
)
fun visible(
    view: View,
    visible: Boolean
) {
    if (visible) view.visible()
    else view.gone()
}