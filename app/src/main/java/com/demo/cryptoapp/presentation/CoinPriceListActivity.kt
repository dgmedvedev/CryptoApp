package com.demo.cryptoapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.cryptoapp.R
import com.demo.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.demo.cryptoapp.domain.CoinInfo
import com.demo.cryptoapp.presentation.adapters.CoinFavouriteInfoAdapter
import com.demo.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinPriceListBinding
    private lateinit var coinInfoAdapter: CoinInfoAdapter
    private lateinit var coinFavouriteInfoAdapter: CoinFavouriteInfoAdapter

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coinInfoAdapter = CoinInfoAdapter(this)
        coinFavouriteInfoAdapter = CoinFavouriteInfoAdapter(this)

        setClickListeners(coinInfoAdapter, coinFavouriteInfoAdapter)

        binding.rvCoinPriceList.adapter = coinInfoAdapter
        binding.rvCoinPriceList.itemAnimator = null

        viewModel.coinInfoList.observe(this) {
            coinInfoAdapter.submitList(it)
        }

        viewModel.coinFavouriteInfoList.observe(this) {
            coinFavouriteInfoAdapter.submitList(it)
        }

        binding.tvFavourite.setOnClickListener {

        }

        binding.switchList.isChecked = false
        binding.switchList.setOnCheckedChangeListener { compoundButton, isChecked ->
            changeAdapter(isChecked)
        }
    }

    private fun changeAdapter(isChecked: Boolean) {
        if (isChecked) {
            binding.rvCoinPriceList.adapter = coinFavouriteInfoAdapter
        } else {
            binding.rvCoinPriceList.adapter = coinInfoAdapter
        }
    }

    private fun setClickListeners(
        adapter: CoinInfoAdapter,
        favouriteAdapter: CoinFavouriteInfoAdapter
    ) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                if (binding.fragmentContainer == null) {
                    launchDetailActivity(coinInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }

            override fun onCoinLongClick(coinInfo: CoinInfo) {
                viewModel.stopAllWorkers()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.background_update_disabled),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        favouriteAdapter.onCoinClickListener =
            object : CoinFavouriteInfoAdapter.OnCoinClickListener {
                override fun onCoinClick(coinFavouriteInfo: CoinFavouriteInfo) {
                    if (binding.fragmentContainer == null) {
                        launchDetailActivity(coinFavouriteInfo.fromSymbol)
                    } else {
                        launchDetailFragment(coinFavouriteInfo.fromSymbol)
                    }
                }

                override fun onCoinLongClick(coinFavouriteInfo: CoinFavouriteInfo) {
                    viewModel.stopAllWorkers()
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.background_update_disabled),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}