package com.example.data.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityImpl(private val context: Context) : Connectivity {
    override fun hasNetworkAccess(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.allNetworks.isNotEmpty()
    }
}