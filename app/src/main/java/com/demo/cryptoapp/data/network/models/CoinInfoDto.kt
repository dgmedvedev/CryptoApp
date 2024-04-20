package com.demo.cryptoapp.data.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_price_list")
data class CoinInfoDto(
    @SerializedName("TYPE")
    val type: String?,
    @SerializedName("MARKET")
    val market: String?,
    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    val toSymbol: String?,
    @SerializedName("FLAGS")
    val flags: String?,
    @SerializedName("PRICE")
    val price: Double?,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Long?,
    @SerializedName("MEDIAN")
    val median: Double?,
    @SerializedName("LASTVOLUME")
    val lastVolume: Double?,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeTo: Double?,
    @SerializedName("LASTTRADEID")
    val lastTradeid: String?,
    @SerializedName("VOLUMEDAY")
    val volumeDay: Double?,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: Double?,
    @SerializedName("VOLUME24HOUR")
    val volume24Hour: Double?,
    @SerializedName("VOLUME24HOURTO")
    val volume24HourTo: Double?,
    @SerializedName("OPENDAY")
    val openDay: Double?,
    @SerializedName("HIGHDAY")
    val highDay: Double?,
    @SerializedName("LOWDAY")
    val lowDay: Double?,
    @SerializedName("OPEN24HOUR")
    val open24Hour: Double?,
    @SerializedName("HIGH24HOUR")
    val high24Hour: Double?,
    @SerializedName("LOW24HOUR")
    val low24Hour: Double?,
    @SerializedName("LASTMARKET")
    val lastMarket: String?,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: Double?,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: Double?,
    @SerializedName("OPENHOUR")
    val openHour: Double?,
    @SerializedName("HIGHHOUR")
    val highHour: Double?,
    @SerializedName("LOWHOUR")
    val lowHour: Double?,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val topTierVolume24Hour: Double?,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24HourTo: Double?,
    @SerializedName("CHANGE24HOUR")
    val change24Hour: Double?,
    @SerializedName("CHANGEPCT24HOUR")
    val changePct24Hour: Double?,
    @SerializedName("CHANGEDAY")
    val changeDay: Double?,
    @SerializedName("CHANGEPCTDAY")
    val changePctDay: Double?,
    @SerializedName("CHANGEHOUR")
    val changeHour: Double?,
    @SerializedName("CHANGEPCTHOUR")
    val changePctHour: Double?,
    @SerializedName("CONVERSIONTYPE")
    val conversionType: String?,
    @SerializedName("CONVERSIONSYMBOL")
    val conversionSymbol: String?,
    @SerializedName("CONVERSIONLASTUPDATE")
    val conversionLastUpdate: Int?,
    @SerializedName("SUPPLY")
    val supply: Int?,
    @SerializedName("MKTCAP")
    val mktCap: Double?,
    @SerializedName("MKTCAPPENALTY")
    val mktCapPenalty: Int?,
    @SerializedName("CIRCULATINGSUPPLY")
    val circulatingSupply: Int?,
    @SerializedName("CIRCULATINGSUPPLYMKTCAP")
    val circulatingSupplyMktCap: Double?,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24Hour: Double?,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24HourTo: Double?,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24Hour: Double?,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24HourTo: Double?,
    @SerializedName("IMAGEURL")
    val imageUrl: String?
)