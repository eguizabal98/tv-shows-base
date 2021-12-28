package com.example.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityImpl @Inject constructor(@ApplicationContext context: Context) : Connectivity {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @SuppressLint("MissingPermission")
    override fun hasNetworkAccess(): Boolean {
        return connectivityManager.allNetworks.isNotEmpty()
    }

    override fun getConnectivityManager(): ConnectivityManager = connectivityManager
}
