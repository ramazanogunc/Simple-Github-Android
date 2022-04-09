package com.ramo.simplegithub.ui.user_search

import android.os.Bundle
import android.view.View
import com.ramo.simplegithub.R
import com.ramo.simplegithub.core.BaseFragment
import com.ramo.simplegithub.databinding.FragmentUserSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchFragment : BaseFragment<FragmentUserSearchBinding, UserSearchViewModel>(
    R.layout.fragment_user_search
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}