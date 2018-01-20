package com.seacoin.seacoin.util

import android.content.Context
import android.net.ConnectivityManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by eka on 2018. 1. 21..
 */
class NetworkHelper(private val context: Context) {
    companion object {
        private val url = "http://soylatte.kr"
        private val port = 8080

        var retrofit: Retrofit? = null

        val networkInstance: NetworkAPI
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                            .baseUrl(url + ":" + port)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return retrofit!!.create<NetworkAPI>(NetworkAPI::class.java)
            }

        fun returnNetworkState(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
        }
    }

}