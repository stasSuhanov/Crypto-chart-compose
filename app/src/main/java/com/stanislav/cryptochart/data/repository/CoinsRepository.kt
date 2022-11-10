package com.stanislav.cryptochart.data.repository

import com.stanislav.cryptochart.data.remote.api.CoinMarketCapApi
import com.stanislav.cryptochart.data.remote.model.toDomainModel
import com.stanislav.cryptochart.domain.entity.CoinEntity
import com.stanislav.cryptochart.utils.Constants.API_KEY
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
}