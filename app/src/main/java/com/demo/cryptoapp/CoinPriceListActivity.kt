package com.demo.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.cryptoapp.adapters.CoinInfoAdapter
import com.demo.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.demo.cryptoapp.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                Log.d("ON_CLICK_TEST", coinPriceInfo.fromSymbol)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel.priceList.observe(this) {
            adapter.submitList(it)
        }
    }
}