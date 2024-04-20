package com.demo.cryptoapp.data.network.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonContainerDto(
    @SerializedName("RAW")
    val json: JsonObject? = null
)