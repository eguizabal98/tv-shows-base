package com.example.data.util

import android.net.ConnectivityManager

interface Connectivity {
    fun hasNetworkAccess(): Boolean
    fun getConnectivityManager(): ConnectivityManager
}
