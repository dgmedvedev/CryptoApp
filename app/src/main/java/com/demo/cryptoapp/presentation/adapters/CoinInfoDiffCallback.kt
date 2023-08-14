package com.demo.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.demo.cryptoapp.data.model.CoinPriceInfo

class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem == newItem
    }
}