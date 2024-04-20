package com.demo.cryptoapp.data.mapper

import com.demo.cryptoapp.data.database.models.CoinFavouriteInfoDbModel
import com.demo.cryptoapp.data.database.models.CoinInfoDbModel
import com.demo.cryptoapp.data.network.models.CoinInfoDto
import com.demo.cryptoapp.data.network.models.CoinInfoJsonContainerDto
import com.demo.cryptoapp.data.network.models.CoinNamesListDto
import com.demo.cryptoapp.domain.models.CoinFavouriteInfo
import com.demo.cryptoapp.domain.models.CoinInfo
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CoinMapper {

    private const val BASE_IMAGE_URL = "https://cryptocompare.com"
    private const val AS_MILLISECONDS = 1000
    private const val SEPARATOR = ","
    private const val TIME_FORMAT = "HH:mm:ss"
    private const val VALUE_IS_EMPTY = ""

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
        lastUpdate = convertTimestampToTime(dto.lastUpdate),
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
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
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
        return favouriteListDbModel.map { it.toDomain() }
    }

    fun mapCoinToCoinFavouriteDbModel(coin: CoinInfo) = CoinFavouriteInfoDbModel(
        fromSymbol = coin.fromSymbol,
        toSymbol = coin.toSymbol,
        price = coin.price,
        lastUpdate = coin.lastUpdate,
        highDay = coin.highDay,
        lowDay = coin.lowDay,
        lastMarket = coin.lastMarket,
        imageUrl = coin.imageUrl
    )

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinNameDto?.name }?.joinToString(SEPARATOR)
            ?: VALUE_IS_EMPTY
    }

    fun mapNamesListToString(namesList: List<String>): String {
        return namesList.joinToString(SEPARATOR)
    }

    private fun CoinFavouriteInfoDbModel.toDomain() = CoinFavouriteInfo(
        fromSymbol = fromSymbol,
        toSymbol = toSymbol,
        price = price,
        lastUpdate = lastUpdate,
        highDay = highDay,
        lowDay = lowDay,
        lastMarket = lastMarket,
        imageUrl = imageUrl
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        timestamp?.let {
            val date = Date(it * AS_MILLISECONDS)
            val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getDefault()
            return simpleDateFormat.format(date)
        }
        return VALUE_IS_EMPTY
    }
}