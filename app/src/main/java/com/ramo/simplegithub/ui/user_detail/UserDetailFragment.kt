package com.ramo.simplegithub.ui.user_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.databinding.FragmentUserDetailBinding
import com.ramo.simplegithub.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserViewModel>(
    R.layout.fragment_user_detail
) {
    override fun isSharedViewModel() = true
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        if (args.userName.isNotEmpty())
            viewModel.getUserDetail(args.userName)
    }

    override fun initObserver() {
        observe(viewModel.user) { user ->
            binding.item = user
        }
    }

    private fun initViews() {
        binding.vm = viewModel
        binding.materialToolbar.setNavigationOnClickListener {
            viewModel.goBack()
        }
        binding.btnOpenBrowser.setOnClickListener {
            viewModel.user.value?.let { user ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(user.profileUrl))
                startActivity(browserIntent)
            }
        }
    }
}