package live.nickp0is0n.cryptotracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import drewcarlson.coingecko.CoinGeckoService
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.database.AppDatabase
import live.nickp0is0n.cryptotracker.database.getCryptoCurrencyFromCoinData
import live.nickp0is0n.cryptotracker.database.getCurrencyListFromDatabase
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import live.nickp0is0n.cryptotracker.ui.CryptoListActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            AppDatabase.initializeDatabase(this@MainActivity)
            if (AppDatabase.database!!.cryptocurrencydao().getAll().isEmpty()) {
                AppDatabase.database!!.cryptocurrencydao().insertAll(*getDemoCurrencyList().toTypedArray())
            } //DEMO data for development purposes
            val currencyList = getCurrencyListFromDatabase()
            val intent = Intent(this@MainActivity, CryptoListActivity::class.java)
            intent.putExtra("currencyList", currencyList)
            startActivity(intent)
            finish()
        }
    }

    suspend fun getDemoCurrencyList(): ArrayList<CryptoCurrency> {
        val service = CoinGeckoService()
        val coinData = service.getCoinById(id = "bitcoin", marketData = true)
        val data = service.getCoinMarketChartById("bitcoin", "usd", 7)

        return arrayListOf(
            getCryptoCurrencyFromCoinData(service.getCoinById("bitcoin", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("ethereum", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("tether", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("dogecoin", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("potcoin", marketData = true))
        )
    }
}