package live.nickp0is0n.cryptotracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import drewcarlson.coingecko.CoinGeckoService
import kotlinx.android.synthetic.main.activity_crypto_list.*
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

class CryptoListActivity : AppCompatActivity() {
    private val adapter = CryptoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_list)
        initRecyclerView()
        loadCurrencyList()
    }

    fun onAddCryptocurrencyButtonClick(view: View) {
        clProgressBar.isVisible = true
        lifecycleScope.launch {
            val coinList = getCoinList()
            val namesList = arrayListOf<String>()
            val idList = arrayListOf<String>()
            coinList.forEach {
                namesList.add(it.second)
                idList.add(it.first)
            }
            val intent = Intent(this@CryptoListActivity, AddCurrencyActivity::class.java)
            intent.putExtra("coinList", namesList)
            intent.putExtra("coinIdList", idList)
            startActivity(intent)
            clProgressBar.isVisible = false
        }
    }

    private suspend fun getCoinList(): ArrayList<Pair<String, String>> {
        val service = CoinGeckoService()
        val coinList = arrayListOf<Pair<String, String>>()
        service.getCoinList().forEach {
            coinList.add(Pair(it.id, it.name))
        }
        return coinList
    }

    private fun initRecyclerView() {
        cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        cryptoRecyclerView.adapter = adapter
    }

    private fun loadCurrencyList() {
        val list = getCurrencies()
        adapter.setItems(getCurrencies())
    }

    private fun getCurrencies(): List<CryptoCurrency> = intent.extras!!["currencyList"] as List<CryptoCurrency>

}