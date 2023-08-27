package com.demo.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinInfoDao {
    @Query("SELECT*FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinInfoDbModel>>

    @Query("SELECT*FROM full_price_favourite_list ORDER BY lastUpdate DESC")
    fun getPriceFavouriteList(): LiveData<List<CoinFavouriteInfoDbModel>>

    @Query("SELECT fromSymbol FROM full_price_favourite_list ORDER BY lastUpdate DESC")
    suspend fun getCoinsFavouriteNamesList(): List<String>

    @Query("SELECT*FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinInfoDbModel>

    @Query("SELECT*FROM full_price_favourite_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoinFavourite(fSym: String): LiveData<CoinFavouriteInfoDbModel>

    @Query("DELETE FROM full_price_favourite_list WHERE fromSymbol == :coinFavouriteSymbol")
    suspend fun deleteCoinFavourite(coinFavouriteSymbol: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceList(priceList: List<CoinInfoDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceFavouriteList(priceFavouriteList: List<CoinFavouriteInfoDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinFavourite(coinFavouriteInfoDbModel: CoinFavouriteInfoDbModel)
}