package com.demo.cryptoapp.presentation.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.demo.cryptoapp.R
import com.demo.cryptoapp.databinding.ItemCoinInfoBinding
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.squareup.picasso.Picasso

class CoinFavouriteInfoAdapter(private val context: Context) :
    ListAdapter<CoinFavouriteInfo, CoinInfoViewHolder>(CoinFavouriteInfoDiffCallback()) {

    var onCoinClickListener: OnCoinClickListener? = null
    lateinit var options: ActivityOptions

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
        with(holder.binding) {
            val symbolsTemplate = context.getString(R.string.symbol_template)
            val lastUpdateTemplate = context.getString(R.string.last_update_template)
            tvSymbols.text = String.format(
                symbolsTemplate,
                coin.fromSymbol,
                coin.toSymbol
            )
            tvPrice.text = coin.price.toString()
            tvLastUpdate.text =
                String.format(lastUpdateTemplate, coin.lastUpdate)
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
            root.setOnClickListener {
                options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    ivLogoCoin,
                    "logo"
                )
                onCoinClickListener?.onCoinClick(coin)
            }
            root.setOnLongClickListener {
                onCoinClickListener?.onCoinLongClick(coin)
                true
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinFavouriteInfo: CoinFavouriteInfo)
        fun onCoinLongClick(coinFavouriteInfo: CoinFavouriteInfo)
    }
}