package com.ramo.simplegithub.ui.common.viewholder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.ramo.sweetrecycler.BR
import com.ramo.sweetrecycler.DBSweetViewHolder

open class StandardViewHolder<T>(
    viewGroup: ViewGroup,
    @LayoutRes layoutId: Int
) : DBSweetViewHolder<T>(layoutId, viewGroup) {

    override fun bind(data: T) {
        binding.setVariable(BR.item, data)
        binding.executePendingBindings()
    }
}