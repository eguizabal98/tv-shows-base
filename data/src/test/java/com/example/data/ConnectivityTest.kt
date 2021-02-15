package com.example.data

import android.net.ConnectivityManager
import com.example.data.util.Connectivity

class ConnectivityTest : Connectivity {
    override fun hasNetworkAccess(): Boolean {
        return true
    }

    override fun getConnectivityManager(): ConnectivityManager {
        TODO("Not yet implemented")
    }
}