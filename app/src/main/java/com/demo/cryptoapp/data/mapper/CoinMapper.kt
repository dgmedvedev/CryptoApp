package com.demo.cryptoapp.data.mapper

import com.demo.cryptoapp.data.database.CoinFavouriteInfoDbModel
import com.demo.cryptoapp.data.database.CoinInfoDbModel
import com.demo.cryptoapp.data.network.model.CoinInfoDto
import com.demo.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.demo.cryptoapp.data.network.model.CoinNamesListDto
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.demo.cryptoapp.domain.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()           // "BTC","ETH","XRP"...
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet() // "USD"
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(       // Object from JSON
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapDbModelToFavouriteDbModel(dbModel: CoinInfoDbModel) = CoinFavouriteInfoDbModel(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    fun mapFavouriteDbModelToFavouriteEntity(
        favouriteDbModel: CoinFavouriteInfoDbModel
    ) = CoinFavouriteInfo(
        fromSymbol = favouriteDbModel.fromSymbol,
        toSymbol = favouriteDbModel.toSymbol,
        price = favouriteDbModel.price,
        lastUpdate = convertTimestampToTime(favouriteDbModel.lastUpdate),
        highDay = favouriteDbModel.highDay,
        lowDay = favouriteDbModel.lowDay,
        lastMarket = favouriteDbModel.lastMarket,
        imageUrl = favouriteDbModel.imageUrl
    )

    fun mapListDbModelToListEntities(
        listDbModel: List<CoinInfoDbModel>
    ): List<CoinInfo> {
        return listDbModel.map {
            mapDbModelToEntity(it)
        }
    }

    fun mapFavouriteListDbModelToFavouriteListEntities(
        favouriteListDbModel: List<CoinFavouriteInfoDbModel>
    ): List<CoinFavouriteInfo> {
        return favouriteListDbModel.map {
            mapFavouriteDbModelToFavouriteEntity(it)
        }
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinNameDto?.name }?.joinToString(",") ?: ""
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}