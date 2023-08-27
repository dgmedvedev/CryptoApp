package com.demo.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.demo.cryptoapp.data.database.AppDatabase
import com.demo.cryptoapp.data.mapper.CoinMapper
import com.demo.cryptoapp.data.workers.RefreshDataWorker
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.demo.cryptoapp.domain.CoinInfo
import com.demo.cryptoapp.domain.CoinRepository

class CoinRepositoryImpl(
    private val application: Application
) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()
    private val mapper = CoinMapper()

    private val workManager = WorkManager.getInstance(application)

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return MediatorLiveData<List<CoinInfo>>().apply {
            addSource(coinInfoDao.getPriceList()) {
//              it.map { mapper.mapDbModelToEntity(it) } - невозможно будет подписаться
                value = mapper.mapListDbModelToListEntities(it)
            }
        }
    }

    override fun getCoinFavouriteInfoList(): LiveData<List<CoinFavouriteInfo>> {
        return MediatorLiveData<List<CoinFavouriteInfo>>().apply {
            addSource(coinInfoDao.getPriceFavouriteList()) {
                value = mapper.mapFavouriteListDbModelToFavouriteListEntities(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
//              mapper.mapDbModelToEntity(it) - невозможно будет подписаться
                value = mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinFavouriteInfo(fromSymbol: String): LiveData<CoinFavouriteInfo> {
        return MediatorLiveData<CoinFavouriteInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoinFavourite(fromSymbol)) {
                value = mapper.mapFavouriteDbModelToFavouriteEntity(it)
            }
        }
    }

    override suspend fun insertCoinFavouriteInfo(coinInfo: CoinInfo) {
        val coinFovourite = mapper.mapCoinToCoinFavouriteDbModel(coinInfo)
        coinInfoDao.insertCoinFavourite(coinFovourite)
    }

    override suspend fun deleteCoinFavouriteInfo(coinFavouriteInfo: CoinFavouriteInfo) {
        coinInfoDao.deleteCoinFavourite(coinFavouriteInfo.fromSymbol)
    }

    override fun loadData() {
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

    override fun stopAllWorkers() {
        workManager.cancelAllWork()
    }
}