package com.pluang.stockapp.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkState {
    fun isNetworkAvailable(mContext: Context): Boolean {
        var status = false
        try {
            val cm =
                (mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                // connected to the internet
                when (activeNetwork.type) {
                    ConnectivityManager.TYPE_WIFI ->                         // connected to wifi
                        status = true
                    ConnectivityManager.TYPE_MOBILE ->                         // connected to mobile data
                        status = true
                    else -> {}
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return status
    }
}