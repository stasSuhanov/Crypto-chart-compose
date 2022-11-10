package com.stanislav.cryptochart.ui.screens.details.view

data class CoinDetailsModel(
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val cmcRank: String,
    val price: Double,
    val marketCap: String,
    val percentChange24h: Double,
    val description: String,
    val logo: String,
    val volume24: String,
)