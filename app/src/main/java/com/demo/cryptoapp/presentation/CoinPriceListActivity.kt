package com.demo.cryptoapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.demo.cryptoapp.R
import com.demo.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.demo.cryptoapp.domain.CoinInfo
import com.demo.cryptoapp.presentation.adapters.CoinFavouriteInfoAdapter
import com.demo.cryptoapp.presentation.adapters.CoinInfoAdapter
import kotlinx.coroutines.launch

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinInfoAdapter: CoinInfoAdapter
    private lateinit var coinFavouriteInfoAdapter: CoinFavouriteInfoAdapter

    private val binding: ActivityCoinPriceListBinding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        coinInfoAdapter = CoinInfoAdapter(this)
        coinFavouriteInfoAdapter = CoinFavouriteInfoAdapter(this)

        observeViewModel()
        setAdaptersClickListeners(coinInfoAdapter, coinFavouriteInfoAdapter)
        setViewsListeners()

        with(binding) {
            rvCoinPriceList.adapter = coinInfoAdapter
            tvTotal.setTextColor(getColor(R.color.teal_200))
        }
    }

    private fun observeViewModel() {
        viewModel.coinInfoList.observe(this) {
            coinInfoAdapter.submitList(it)
        }

        viewModel.coinFavouriteInfoList.observe(this) {
            coinFavouriteInfoAdapter.submitList(it)
        }
    }

    private fun setAdaptersClickListeners(
        adapter: CoinInfoAdapter,
        favouriteAdapter: CoinFavouriteInfoAdapter
    ) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                if (binding.fragmentContainer == null) {
                    launchDetailActivity(coinInfo.fromSymbol, adapter.options.toBundle())
                } else {
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }

            override fun onCoinLongClick(coinInfo: CoinInfo) {
                lifecycleScope.launch {
                    viewModel.insertCoinFavouriteInfo(coinInfo)
                }
            }
        }
        favouriteAdapter.onCoinClickListener =
            object : CoinFavouriteInfoAdapter.OnCoinClickListener {
                override fun onCoinClick(coinFavouriteInfo: CoinFavouriteInfo) {
                    if (binding.fragmentContainer == null) {
                        launchDetailActivity(
                            coinFavouriteInfo.fromSymbol,
                            favouriteAdapter.options.toBundle()
                        )
                    } else {
                        launchDetailFragment(coinFavouriteInfo.fromSymbol)
                    }
                }

                override fun onCoinLongClick(coinFavouriteInfo: CoinFavouriteInfo) {
                    lifecycleScope.launch {
                        viewModel.deleteCoinFavouriteInfo(coinFavouriteInfo)
                    }
                }
            }
    }

    private fun setViewsListeners() {
        with(binding) {
            tvTotal.setOnClickListener {
                viewModel.stopAllWorkers()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.background_update_disabled),
                    Toast.LENGTH_SHORT
                ).show()
                tvFavourite.setTextColor(getColor(R.color.black))
                switchList.isChecked = false
                tvTotal.setTextColor(getColor(R.color.teal_200))
                changeAdapter(switchList.isChecked)
            }

            tvFavourite.setOnClickListener {
                tvTotal.setTextColor(getColor(R.color.black))
                switchList.isChecked = true
                tvFavourite.setTextColor(getColor(R.color.teal_200))
                changeAdapter(switchList.isChecked)
            }

            switchList.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    tvFavourite.setTextColor(getColor(R.color.teal_200))
                    tvTotal.setTextColor(getColor(R.color.black))
                } else {
                    tvTotal.setTextColor(getColor(R.color.teal_200))
                    tvFavourite.setTextColor(getColor(R.color.black))
                }
                changeAdapter(isChecked)
            }
        }
    }

    private fun changeAdapter(isChecked: Boolean) {
        if (isChecked) {
            binding.rvCoinPriceList.adapter = coinFavouriteInfoAdapter
        } else {
            binding.rvCoinPriceList.adapter = coinInfoAdapter
        }
    }

    private fun launchDetailActivity(fromSymbol: String, options: Bundle?) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent, options)
        //overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
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