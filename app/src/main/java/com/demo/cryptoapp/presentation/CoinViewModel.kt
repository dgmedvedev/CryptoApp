package com.demo.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.demo.cryptoapp.data.repository.CoinRepositoryImpl
import com.demo.cryptoapp.domain.*

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinFavouriteInfoListUseCase = GetCoinFavouriteInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinFavouriteInfoUseCase = GetCoinFavouriteInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val stopAllWorkersUseCase = StopAllWorkersUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()
    val coinFavouriteInfoList = getCoinFavouriteInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)
    fun getDetailFavouriteInfo(fSym: String) = getCoinFavouriteInfoUseCase(fSym)

    fun stopAllWorkers() {
        stopAllWorkersUseCase()
    }

    init {
        loadDataUseCase()
    }
}