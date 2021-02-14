package com.example.data.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityImpl(context: Context) : Connectivity {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun hasNetworkAccess(): Boolean {
        return connectivityManager.allNetworks.isNotEmpty()
    }

    override fun getConnectivityManager(): ConnectivityManager = connectivityManager

}