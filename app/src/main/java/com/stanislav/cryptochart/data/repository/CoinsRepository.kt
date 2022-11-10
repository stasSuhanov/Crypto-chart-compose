package com.stanislav.cryptochart.data.repository

import com.google.gson.Gson
import com.stanislav.cryptochart.data.remote.api.CoinMarketCapApi
import com.stanislav.cryptochart.data.remote.model.CoinDetail
import com.stanislav.cryptochart.data.remote.model.toDomainModel
import com.stanislav.cryptochart.domain.entity.CoinEntity
import com.stanislav.cryptochart.domain.entity.DetailsEntity
import com.stanislav.cryptochart.utils.Constants.API_KEY
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class CoinsRepository @Inject constructor(private val coinMarketCapApi: CoinMarketCapApi) {

    suspend fun fetchCoins(): List<CoinEntity> {
        val coinMarketCapJson = coinMarketCapApi.fetchCoins(API_KEY, 100.toString())
        var coins = listOf<CoinEntity>()
        if (coinMarketCapJson.coins != null) {
            coins = coinMarketCapJson.coins.map { it.toDomainModel() }
        }
        return coins
    }

    suspend fun fetchCoin(symbol: String): DetailsEntity {
        val detailsResponse = coinMarketCapApi.fetchDetail(API_KEY, symbol)
        val gson = Gson()
        val json = gson.toJson(detailsResponse.data)
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject[symbol] as JSONArray
        val coin = gson.fromJson(jsonArray.getJSONObject(0).toString(), CoinDetail::class.java)
        return coin.toDomainModel()
    }
}