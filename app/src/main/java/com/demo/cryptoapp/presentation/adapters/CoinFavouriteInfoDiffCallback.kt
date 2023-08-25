package com.demo.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.demo.cryptoapp.domain.CoinFavouriteInfo

class CoinFavouriteInfoDiffCallback : DiffUtil.ItemCallback<CoinFavouriteInfo>() {
    override fun areItemsTheSame(
        oldItem: CoinFavouriteInfo,
        newItem: CoinFavouriteInfo
    ): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(
        oldItem: CoinFavouriteInfo,
        newItem: CoinFavouriteInfo
    ): Boolean {
        return oldItem == newItem
    }
}