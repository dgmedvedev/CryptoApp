package com.demo.cryptoapp.data.network.models

import com.google.gson.annotations.SerializedName

data class CoinNameDto(
    @SerializedName("Name")
    val name: String? = null
)