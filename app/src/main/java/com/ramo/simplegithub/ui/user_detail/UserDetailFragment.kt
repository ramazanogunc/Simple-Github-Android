package com.ramo.simplegithub.ui.user_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.core.ext.observe
import com.ramo.simplegithub.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserDetailViewModel>(
    R.layout.fragment_user_detail
) {

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
        binding.materialToolbar.setNavigationOnClickListener {
            Toast.makeText(context, "girdi", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}