package com.ramo.simplegithub.ui.user_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.simplegithub.AppConstants
import com.ramo.simplegithub.core.BaseViewModel
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.usecase.SearchUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val searchUserListUseCase: SearchUserListUseCase
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    var page = 1
        private set
    private var query = ""


    fun search(query: String) = safeScope {
        this.query = query
        page = 1
        if (query.length <= 2) return@safeScope
        _users.value = searchUserListUseCase.invoke(query, page, AppConstants.PER_PAGE)
    }

    fun nextPage() = safeScope {
        page++
        _users.value = searchUserListUseCase.invoke(query, page, AppConstants.PER_PAGE)
    }
}