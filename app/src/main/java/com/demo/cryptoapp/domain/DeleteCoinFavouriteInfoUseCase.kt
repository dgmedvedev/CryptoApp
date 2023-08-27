package com.demo.cryptoapp.domain

class DeleteCoinFavouriteInfoUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coinFavouriteInfo: CoinFavouriteInfo) =
        repository.deleteCoinFavouriteInfo(coinFavouriteInfo)
}