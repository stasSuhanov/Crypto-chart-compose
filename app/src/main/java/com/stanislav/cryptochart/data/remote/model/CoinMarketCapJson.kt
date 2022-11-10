package com.stanislav.cryptochart.data.remote.model

import com.google.gson.annotations.SerializedName
import com.stanislav.cryptochart.domain.entity.CoinEntity
import com.stanislav.cryptochart.domain.entity.DetailsEntity

data class CoinMarketCapJson(
    @SerializedName("data") val coins: List<Coin>? = null,
)

data class Coin(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("cmc_rank") val cmcRank: String? = null,
    @SerializedName("quote") val quote: Quote? = null,
)

data class Quote(
    @SerializedName("USD") val Usd: Usd? = null,
)

data class Usd(
    @SerializedName("price") val price: Double? = null,
    @SerializedName("volume_24h") val volume24: String? = null,
    @SerializedName("market_cap") val marketCap: String? = null,
    @SerializedName("percent_change_24h") val percentChange24h: Double? = null,
)

data class DetailResponse(
    @SerializedName("data") val data: Any? = null,
)

data class CoinDetail(
    @SerializedName("description") val description: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("logo") val logo: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
)

fun Coin.toDomainModel(): CoinEntity {
    return CoinEntity(
        id = id ?: "0",
        name = name ?: "Custom coin",
        symbol = symbol ?: "CC",
        slug = slug ?: "custom coin",
        cmcRank = cmcRank ?: "1",
        price = quote?.Usd?.price ?: 0.0,
        marketCap = quote?.Usd?.marketCap ?: "1.1",
        percentChange24h = quote?.Usd?.percentChange24h ?: 0.0,
        volume24 = quote?.Usd?.volume24 ?: "1.1"
    )
}

fun CoinDetail.toDomainModel(): DetailsEntity {
    return DetailsEntity(
        id = id ?: "0",
        name = name ?: "Custom coin",
        symbol = symbol ?: "CC",
        logo = logo ?: "0",
        description = description ?: "No description",
    )
}