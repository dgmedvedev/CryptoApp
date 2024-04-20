package com.demo.cryptoapp.domain.usecases

import androidx.lifecycle.LiveData
import com.demo.cryptoapp.domain.models.CoinInfo
import com.demo.cryptoapp.domain.repository.CoinRepository

class GetCoinInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> = repository.getCoinInfo(fromSymbol)
}