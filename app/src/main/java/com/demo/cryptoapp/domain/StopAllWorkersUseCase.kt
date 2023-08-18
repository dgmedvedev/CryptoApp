package com.demo.cryptoapp.domain

class StopAllWorkersUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.stopAllWorkers()
}