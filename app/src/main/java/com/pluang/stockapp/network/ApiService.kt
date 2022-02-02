package com.pluang.stockapp.network

import com.pluang.stockapp.data.model.DataResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {


    @Headers("Accept: application/json")
    @GET("stocks/quotes?sids=RELI%2CTCS%2CITC%2CHDBK%2CINFY")
    fun getStocksData(): Call<DataResponse?>?

    companion object Factory {

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.tickertape.in/")
                .build()

            return retrofit.create(ApiService::class.java);

        }

    }
}