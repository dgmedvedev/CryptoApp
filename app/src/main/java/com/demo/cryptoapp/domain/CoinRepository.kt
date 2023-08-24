package com.demo.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinFavouriteInfoList(): LiveData<List<CoinFavouriteInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun getCoinFavouriteInfo(fromSymbol: String): LiveData<CoinFavouriteInfo>

    fun loadData()

    fun stopAllWorkers()
}