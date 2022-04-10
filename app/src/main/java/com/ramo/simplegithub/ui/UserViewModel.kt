package com.ramo.simplegithub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramo.simplegithub.AppConstants
import com.ramo.simplegithub.core.BaseViewModel
import com.ramo.simplegithub.core.SingleLiveEvent
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.usecase.SearchUserListUseCase
import com.ramo.simplegithub.domain.usecase.UserChangeFavoriteStatusUseCase
import com.ramo.simplegithub.domain.usecase.UserDetailUseCase
import com.ramo.simplegithub.domain.usecase.UserListUseCase
import com.ramo.simplegithub.ui.user_detail.UserDetailFragmentDirections
import com.ramo.simplegithub.ui.user_list.UserListFragmentDirections
import com.ramo.simplegithub.ui.user_search.UserSearchFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val searchUserListUseCase: SearchUserListUseCase,
    private val userDetailUseCase: UserDetailUseCase,
    private val userChangeFavoriteStatusUseCase: UserChangeFavoriteStatusUseCase
) : BaseViewModel() {

    // LIST OPERATIONS

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    val listUpdated = SingleLiveEvent<Boolean>()

    var listPage = 1
        private set

    fun getUsers() = safeScope {
        _users.value = userListUseCase.invoke(listPage, AppConstants.PER_PAGE)
    }

    fun getUserNextPage() {
        listPage++
        getUsers()
    }

    fun refreshUsers() {
        listPage = 1
        getUsers()
    }

    //SEARCH OPERATIONS

    private val _searchUsers = MutableLiveData<List<User>>()
    val searchUsers: LiveData<List<User>> = _searchUsers

    var searchPage = 1
        private set
    private var query = ""


    fun search(query: String) = safeScope {
        this.query = query
        searchPage = 1
        if (query.length <= 2) return@safeScope
        _searchUsers.value = searchUserListUseCase.invoke(query, searchPage, AppConstants.PER_PAGE)
    }

    fun nextSearchPage() = safeScope {
        searchPage++
        _searchUsers.value = searchUserListUseCase.invoke(query, searchPage, AppConstants.PER_PAGE)
    }

    // USER DETAIL OPERATIONS

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

    // COMMON OPERATIONS

    fun goUserDetailFromSearch(userName: String) {
        navigate(
            UserSearchFragmentDirections.actionFragmentUserSearchToUserDetailFragment(
                userName = userName
            )
        )
    }

    fun goUserDetailFromList(userName: String) {
        navigate(
            UserListFragmentDirections.actionFragmentUserListToUserDetailFragment(
                userName = userName
            )
        )
    }

    fun changeFavoriteStatus(user: User) = safeScope {
        userChangeFavoriteStatusUseCase.invoke(user, user.isFavorite?.not() ?: true)
        users.value?.firstOrNull { it.id == user.id }?.isFavorite = user.isFavorite
        searchUsers.value?.firstOrNull { it.id == user.id }?.isFavorite = user.isFavorite
        listUpdated.value = true
        _user.value = user
    }
}
