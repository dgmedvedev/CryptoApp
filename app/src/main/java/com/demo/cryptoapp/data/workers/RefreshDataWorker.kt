package com.demo.cryptoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.demo.cryptoapp.data.database.AppDatabase
import com.demo.cryptoapp.data.mapper.CoinMapper
import com.demo.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val coinInfoDao = AppDatabase.getInstance(context).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val favouriteCoinsNamesList = coinInfoDao.getCoinsFavouriteNamesList()
                val fSyms = mapper.mapNamesListToString(topCoins)
                val fSymsFavourite = mapper.mapNamesListToString(favouriteCoinsNamesList)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val jsonContainerFavourite = apiService.getFullPriceList(fSyms = fSymsFavourite)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val coinInfoDtoFavouriteList =
                    mapper.mapJsonContainerToListCoinInfo(jsonContainerFavourite)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                val favouriteDbModelList = coinInfoDtoFavouriteList
                    .map { mapper.mapDtoToDbModel(it) }
                    .map { mapper.mapDbModelToFavouriteDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
                coinInfoDao.insertPriceFavouriteList(favouriteDbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}