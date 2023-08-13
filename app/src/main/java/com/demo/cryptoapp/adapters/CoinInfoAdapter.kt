package com.demo.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.cryptoapp.databinding.ItemCoinInfoBinding
import com.demo.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter :
    ListAdapter<CoinPriceInfo, CoinInfoAdapter.CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder) {
            tvSymbols.text = coin.fromSymbol + " / " + coin.toSymbol
            tvPrice.text = coin.price.toString()
            tvLastUpdate.text = coin.getFormattedTime()
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
        }
    }

    inner class CoinInfoViewHolder(binding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivLogoCoin = binding.ivLogoCoin
        val tvSymbols = binding.tvSymbols
        val tvPrice = binding.tvPrice
        val tvLastUpdate = binding.tvLastUpdate
    }
}