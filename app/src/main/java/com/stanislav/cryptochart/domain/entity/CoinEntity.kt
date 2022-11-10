package com.stanislav.cryptochart.domain.entity

data class CoinEntity(
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val cmcRank: String,
    val price: Double,
    val marketCap: Double,
    val percentChange24h: Double,
)