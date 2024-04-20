package com.demo.cryptoapp.domain.usecases

import com.demo.cryptoapp.domain.repository.CoinRepository

class LoadDataUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.loadData()
}