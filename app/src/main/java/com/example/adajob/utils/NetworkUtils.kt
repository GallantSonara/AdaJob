@file:Suppress("DEPRECATION")

package com.example.adajob.utils
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtils {
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}