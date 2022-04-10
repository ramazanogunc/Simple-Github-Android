package com.ramo.simplegithub.ui.user_search

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ramo.simplegithub.AppConstants
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.core.ext.gone
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.core.ext.textChangeDelayedListener
import com.ramo.simplegithub.core.ext.visible
import com.ramo.simplegithub.databinding.FragmentUserSearchBinding
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.ui.common.viewholder.UserViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment : BaseFragment<FragmentUserSearchBinding, UserSearchViewModel>(
    R.layout.fragment_user_search
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initObserver() {
        observe(viewModel.users) { users ->
            if (users.isEmpty() && viewModel.page == 1)
                binding.txtNotFound.visible()
            else
                binding.txtNotFound.gone()
            binding.recyclerView.addData(users)
            binding.recyclerView.isPaginationEnable = users.size >= AppConstants.PER_PAGE
        }
        observe(viewModel.listUpdated){
            binding.recyclerView.notifyDataSetChanged()
        }
    }

    private fun initViews() {
        withVB {
            recyclerView.onScrollEnd {
                viewModel.nextPage()
            }
            recyclerView.render { parent: ViewGroup, _: Int, _: User ->
                return@render UserViewHolder(viewGroup = parent, onClickFavorite = { user ->
                    viewModel.changeFavoriteStatus(user)
                })
            }
            recyclerView.setOnItemClickListener<User> { _, _, data ->
                viewModel.goUserDetail(data.userName)
            }
            editTextSearch.textChangeDelayedListener { query ->
                binding.recyclerView.clearData()
                viewModel.search(query)
            }
        }
    }
}