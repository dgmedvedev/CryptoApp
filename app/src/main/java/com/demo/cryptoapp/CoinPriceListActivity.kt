package com.demo.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class CoinPriceListActivity : AppCompatActivity() {

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel.priceList.observe(this) {
            Log.d("TEST_OF_LOADING_DATA", "Success in activity: $it")
        }
        viewModel.getDetailInfo("XRP").observe(this) {
            Log.d("TEST_OF_LOADING_DATA", "Success in activity: $it")
        }
    }
}