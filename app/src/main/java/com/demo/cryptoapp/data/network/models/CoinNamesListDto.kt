package com.demo.cryptoapp.data.network.models

import com.google.gson.annotations.SerializedName

data class CoinNamesListDto(
    @SerializedName("Data")
    val names: List<CoinNameContainerDto>? = null
)