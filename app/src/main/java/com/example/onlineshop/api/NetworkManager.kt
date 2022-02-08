package com.example.onlineshop.api

import com.example.onlineshop.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit? = null

    private var api: Api? = null

    fun getApiService(): Api{
        if (api == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL).build()

            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }

}