package com.demo.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.demo.cryptoapp.domain.models.CoinFavouriteInfo
import com.demo.cryptoapp.domain.models.CoinInfo

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinFavouriteInfoList(): LiveData<List<CoinFavouriteInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    suspend fun insertCoinFavouriteInfo(coinInfo: CoinInfo)

    suspend fun deleteCoinFavouriteInfo(coinFavouriteInfo: CoinFavouriteInfo)

    fun loadData()

    fun stopAllWorkers()
}