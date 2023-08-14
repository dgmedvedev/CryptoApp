package com.demo.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.demo.cryptoapp.data.network.model.CoinInfoDto

class CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem == newItem
    }
}