package com.demo.cryptoapp.domain.usecases

import com.demo.cryptoapp.domain.repository.CoinRepository

class StopAllWorkersUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.stopAllWorkers()
}