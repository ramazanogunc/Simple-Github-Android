package com.ramo.simplegithub.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.simplegithub.AppConstants
import com.ramo.simplegithub.core.BaseViewModel
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private var page = 1

    init {
        getUsers()
    }

    private fun getUsers() = safeScope {
        _users.value = userListUseCase.invoke(page, AppConstants.PER_PAGE)
    }

    fun getUserNextPage() {
        page++
        getUsers()
    }

    fun refreshUsers() {
        page = 1
        getUsers()
    }

    fun changeFavoriteStatus(user: User) {
        // TODO:
    }
}