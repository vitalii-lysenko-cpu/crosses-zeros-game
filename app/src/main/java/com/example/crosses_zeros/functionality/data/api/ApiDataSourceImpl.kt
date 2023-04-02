package com.example.crosses_zeros.functionality.data.api


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiDataSourceImpl @Inject constructor(
    private val api: Api,
    @ApplicationContext
    private val context: Context,
) : ApiDataSource {

    override suspend fun getErrorResponse(): Int = api.getErrorResponse().code()

//    override suspend fun getLastUrl(): String = api.getErrorResponse().raw().request.url.toString()

    override fun checkConnection(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            manager.getNetworkCapabilities(manager.activeNetwork)?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        it.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        ) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            } ?: false
        else
            @Suppress("DEPRECATION")
            manager.activeNetworkInfo?.isConnected == true
    }
}