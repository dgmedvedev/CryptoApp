package com.demo.cryptoapp.domain

import androidx.lifecycle.LiveData

class GetCoinFavouriteInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol: String): LiveData<CoinFavouriteInfo> =
        repository.getCoinFavouriteInfo(fromSymbol)
}