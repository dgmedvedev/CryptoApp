package com.demo.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.cryptoapp.databinding.ActivityCoinFavouriteBinding

class CoinFavouriteActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinFavouriteBinding.inflate(layoutInflater)
    }

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.coinFavouriteInfoList.observe(this) {
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoinFavouriteActivity::class.java)
        }
    }
}