package com.ramo.simplegithub.data.repository

import com.ramo.simplegithub.data.NetworkUtil
import com.ramo.simplegithub.domain.exception.RefreshException


abstract class BaseRepository(
    protected val networkUtil: NetworkUtil
) {

    protected var isOnlyCache: Boolean = networkUtil.isNetworkConnected().not()

    fun checkStatus(cacheList: List<*>) {
        if (isOnlyCache && cacheList.isEmpty()) throw RefreshException()
    }
}