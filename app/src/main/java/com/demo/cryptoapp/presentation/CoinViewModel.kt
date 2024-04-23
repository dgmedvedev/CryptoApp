package com.demo.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.demo.cryptoapp.data.repository.CoinRepositoryImpl
import com.demo.cryptoapp.domain.models.CoinFavouriteInfo
import com.demo.cryptoapp.domain.models.CoinInfo
import com.demo.cryptoapp.domain.usecases.DeleteCoinFavouriteInfoUseCase
import com.demo.cryptoapp.domain.usecases.GetCoinFavouriteInfoListUseCase
import com.demo.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.demo.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.demo.cryptoapp.domain.usecases.InsertCoinFavouriteInfoUseCase
import com.demo.cryptoapp.domain.usecases.LoadDataUseCase
import com.demo.cryptoapp.domain.usecases.StopAllWorkersUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinFavouriteInfoListUseCase = GetCoinFavouriteInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    private val stopAllWorkersUseCase = StopAllWorkersUseCase(repository)

    private val insertCoinFavouriteInfoUseCase = InsertCoinFavouriteInfoUseCase(repository)
    private val deleteCoinFavouriteInfoUseCase = DeleteCoinFavouriteInfoUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()
    val coinFavouriteInfoList = getCoinFavouriteInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    fun stopAllWorkers() {
        stopAllWorkersUseCase()
    }

    init {
        loadDataUseCase()
    }

    suspend fun insertCoinFavouriteInfo(coinInfo: CoinInfo) =
        insertCoinFavouriteInfoUseCase(coinInfo)

    suspend fun deleteCoinFavouriteInfo(coinFavouriteInfo: CoinFavouriteInfo) =
        deleteCoinFavouriteInfoUseCase(coinFavouriteInfo)
}