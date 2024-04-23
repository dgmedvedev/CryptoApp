package com.demo.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.demo.cryptoapp.domain.models.CoinFavouriteInfo
import com.demo.cryptoapp.domain.repository.CoinRepository

class GetCoinFavouriteInfoListUseCase(private val repository: CoinRepository) {
    operator fun invoke(): LiveData<List<CoinFavouriteInfo>> =
        repository.getCoinFavouriteInfoList()
}