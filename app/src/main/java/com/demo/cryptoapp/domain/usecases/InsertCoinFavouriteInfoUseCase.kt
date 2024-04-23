package com.demo.cryptoapp.domain.usecases

import com.demo.cryptoapp.domain.models.CoinInfo
import com.demo.cryptoapp.domain.repository.CoinRepository

class InsertCoinFavouriteInfoUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coinInfo: CoinInfo) =
        repository.insertCoinFavouriteInfo(coinInfo)
}