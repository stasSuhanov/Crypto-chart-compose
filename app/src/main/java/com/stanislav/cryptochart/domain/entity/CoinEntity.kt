package com.stanislav.cryptochart.domain.entity

data class CoinEntity(
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val cmcRank: String,
    val price: Double,
    val marketCap: String,
    val percentChange24h: Double,
    val volume24: String,
)