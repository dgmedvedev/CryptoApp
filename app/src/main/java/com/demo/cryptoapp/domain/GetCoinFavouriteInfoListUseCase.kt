package com.demo.cryptoapp.domain

import androidx.lifecycle.LiveData

class GetCoinFavouriteInfoListUseCase(private val repository: CoinRepository) {
    operator fun invoke(): LiveData<List<CoinFavouriteInfo>> =
        repository.getCoinFavouriteInfoList()
}