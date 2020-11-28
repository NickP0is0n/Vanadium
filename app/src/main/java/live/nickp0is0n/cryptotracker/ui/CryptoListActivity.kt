package live.nickp0is0n.cryptotracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_crypto_list.*
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.adapter.CryptoAdapter
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

class CryptoListActivity : AppCompatActivity() {
    private val adapter = CryptoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_list)
        initRecyclerView()
        loadCurrencyList()
    }

    private fun initRecyclerView() {
        cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        cryptoRecyclerView.adapter = adapter
    }

    private fun loadCurrencyList() {
        val list = getCurrencies()
        adapter.setItems(getCurrencies())
    }

    private fun getCurrencies(): List<CryptoCurrency> {
        TODO()
    }
}