package com.ramo.simplegithub.ui.user_avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.simplegithub.core.BaseViewModel
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.usecase.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAvatarViewModel @Inject constructor(
    private val userDetailUseCase: UserDetailUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUserDetail(userName: String) = safeScope {
        _user.value = userDetailUseCase.invoke(userName)
    }


    fun changeFavoriteStatus(user: User) {
        // TODO:
    }
}