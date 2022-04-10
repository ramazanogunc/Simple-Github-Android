package com.ramo.simplegithub.ui.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.simplegithub.core.BaseViewModel
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.usecase.UserChangeFavoriteStatusUseCase
import com.ramo.simplegithub.domain.usecase.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userDetailUseCase: UserDetailUseCase,
    private val userChangeFavoriteStatusUseCase: UserChangeFavoriteStatusUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUserDetail(userName: String) = safeScope {
        _user.value = userDetailUseCase.invoke(userName)
    }

    fun goAvatar(userName: String, profileImageUrl: String) {
        navigate(
            UserDetailFragmentDirections.actionUserDetailFragmentToUserAvatarFragment(
                userName = userName,
                profileImage = profileImageUrl
            )
        )
    }


    fun changeFavoriteStatus(user: User) = safeScope {
        userChangeFavoriteStatusUseCase.invoke(user, user.isFavorite?.not() ?: true)
        _user.value = user
    }
}