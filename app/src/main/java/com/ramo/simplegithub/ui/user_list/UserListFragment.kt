package com.ramo.simplegithub.ui.user_list

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ramo.simplegithub.AppConstants.PER_PAGE
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.databinding.FragmentUserListBinding
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.ui.UserViewModel
import com.ramo.simplegithub.ui.common.viewholder.UserViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding, UserViewModel>(
    R.layout.fragment_user_list
) {

    override fun isSharedViewModel() = true
    var firstOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.swipeToRefresh.setOnRefreshListener {
            binding.recyclerView.clearData()
            viewModel.refreshUsers()
        }
        if (firstOpen){
            viewModel.getUsers()
            firstOpen = false
        }
    }

    override fun showLoading() {
        binding.swipeToRefresh.isRefreshing = true
    }

    override fun dismissLoading() {
        binding.swipeToRefresh.isRefreshing = false
    }

    override fun initObserver() {
        observe(viewModel.users) { users ->
            binding.recyclerView.addData(users)
            binding.recyclerView.isPaginationEnable = users.size >= PER_PAGE
        }
        observe(viewModel.listUpdated){
            binding.recyclerView.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.render { parent: ViewGroup, _: Int, _: User ->
            return@render UserViewHolder(viewGroup = parent, onClickFavorite = { user ->
                viewModel.changeFavoriteStatus(user)
            })
        }
        binding.recyclerView.setOnItemClickListener<User> { _, _, data ->
            viewModel.goUserDetailFromList(data.userName)
        }
        binding.recyclerView.onScrollEnd {
            viewModel.getUserNextPage()
        }
    }

}