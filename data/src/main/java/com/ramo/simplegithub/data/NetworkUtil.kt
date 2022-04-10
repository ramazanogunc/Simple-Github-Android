package com.ramo.simplegithub.data

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class NetworkUtil @Inject constructor(
    private val context: Context
) {

    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm ?: return false
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected ?: false
    }
}