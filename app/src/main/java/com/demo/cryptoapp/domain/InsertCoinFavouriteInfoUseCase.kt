package com.demo.cryptoapp.domain

class InsertCoinFavouriteInfoUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coinInfo: CoinInfo) =
        repository.insertCoinFavouriteInfo(coinInfo)
}