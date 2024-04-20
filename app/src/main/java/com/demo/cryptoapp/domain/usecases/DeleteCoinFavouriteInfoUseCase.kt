package com.demo.cryptoapp.domain.usecases

import com.demo.cryptoapp.domain.models.CoinFavouriteInfo
import com.demo.cryptoapp.domain.repository.CoinRepository

class DeleteCoinFavouriteInfoUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coinFavouriteInfo: CoinFavouriteInfo) =
        repository.deleteCoinFavouriteInfo(coinFavouriteInfo)
}