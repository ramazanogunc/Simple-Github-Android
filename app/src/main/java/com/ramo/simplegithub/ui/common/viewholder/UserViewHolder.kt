package com.ramo.simplegithub.ui.common.viewholder

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.ramo.simplegithub.R
import com.ramo.simplegithub.domain.model.User

class UserViewHolder(
    private val onClickFavorite: (User) -> Unit,
    viewGroup: ViewGroup
) : StandardViewHolder<User>(
    viewGroup = viewGroup,
    layoutId = R.layout.item_user
) {
    override fun bind(data: User) {
        super.bind(data)
        itemView.findViewById<AppCompatImageView>(R.id.imgFavorite).setOnClickListener {
            onClickFavorite.invoke(data)
        }
    }
}