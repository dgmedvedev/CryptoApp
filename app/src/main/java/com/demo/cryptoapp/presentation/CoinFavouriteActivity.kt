package com.demo.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.cryptoapp.R
import com.demo.cryptoapp.databinding.ActivityCoinFavouriteBinding
import com.demo.cryptoapp.domain.CoinFavouriteInfo
import com.demo.cryptoapp.presentation.adapters.CoinFavouriteInfoAdapter

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
        val adapter = CoinFavouriteInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinFavouriteInfoAdapter.OnCoinClickListener {
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
        binding.rvCoinFavouriteList.adapter = adapter
        binding.rvCoinFavouriteList.itemAnimator = null

        viewModel.coinFavouriteInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinFavouriteActivity,
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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoinFavouriteActivity::class.java)
        }
    }
}