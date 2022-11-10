package com.stanislav.cryptochart.data.remote.api

import com.stanislav.cryptochart.data.remote.model.CoinMarketCapJson
import com.stanislav.cryptochart.data.remote.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinMarketCapApi {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun fetchCoins(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("limit") limit: String
    ): CoinMarketCapJson

    @GET("v2/cryptocurrency/info")
    suspend fun fetchDetail(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("symbol") symbol: String
    ): DetailResponse
}