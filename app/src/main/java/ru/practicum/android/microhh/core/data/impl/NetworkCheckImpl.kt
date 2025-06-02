package ru.practicum.android.microhh.core.data.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.practicum.android.microhh.core.data.network.NetworkCheck

class NetworkCheckImpl(
    private val context: Context
) : NetworkCheck {

    override fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
