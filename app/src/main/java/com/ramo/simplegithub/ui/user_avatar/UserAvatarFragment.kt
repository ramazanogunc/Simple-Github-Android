package com.ramo.simplegithub.ui.user_avatar

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.databinding.FragmentUserAvatarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAvatarFragment : BaseFragment<FragmentUserAvatarBinding, UserAvatarViewModel>(
    R.layout.fragment_user_avatar
) {

    private val args: UserAvatarFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        if (args.userName.isNotEmpty() && args.profileImage.isNotEmpty()) {
            binding.imageUrl = args.profileImage
            binding.userName = args.userName
        }
        binding.materialToolbar.setNavigationOnClickListener {
            viewModel.goBack()
        }
    }
}