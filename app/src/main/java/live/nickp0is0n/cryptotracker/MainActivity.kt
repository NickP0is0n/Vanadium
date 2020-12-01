package live.nickp0is0n.cryptotracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import drewcarlson.coingecko.CoinGeckoService
import drewcarlson.coingecko.models.coins.CoinFullData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.database.AppDatabase
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import live.nickp0is0n.cryptotracker.ui.CryptoListActivity
import java.util.*
import kotlin.math.pow
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            AppDatabase.initializeDatabase(this@MainActivity)
            if (AppDatabase.database!!.cryptocurrencydao().getAll().isEmpty()) {
                AppDatabase.database!!.cryptocurrencydao().insertAll(*getDemoCurrencyList().toTypedArray())
            } //ДЕМО для тестирования во время разработки
            val currencyList = getCurrencyListFromDatabase()
            val intent = Intent(this@MainActivity, CryptoListActivity::class.java)
            intent.putExtra("currencyList", currencyList)
            startActivity(intent)
            finish()
        }
    }

    suspend fun getCurrencyListFromDatabase(): ArrayList<CryptoCurrency> {
        val service = CoinGeckoService()
        val dbList = AppDatabase.database!!.cryptocurrencydao().getAll() as ArrayList<CryptoCurrency>
        for (i in 0 until dbList.size) {
            dbList[i] = getCryptoCurrencyFromCoinData(service.getCoinById(dbList[i].id, marketData = true))
        }
        return dbList
    }

    suspend fun getDemoCurrencyList(): ArrayList<CryptoCurrency> {
        val service = CoinGeckoService()
        val coinData = service.getCoinById(id = "bitcoin", marketData = true)

        return arrayListOf(
            getCryptoCurrencyFromCoinData(service.getCoinById("bitcoin", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("ethereum", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("tether", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("dogecoin", marketData = true)),
            getCryptoCurrencyFromCoinData(service.getCoinById("potcoin", marketData = true))
        )
    }

    fun getCryptoCurrencyFromCoinData(coinData: CoinFullData): CryptoCurrency = CryptoCurrency(
        coinData.id,
        coinData.symbol.toUpperCase(Locale.ROOT),
        coinData.name,
        coinData.marketData?.currentPrice?.get("usd") ?: 0.00,
        roundNullable(coinData.marketData?.priceChangePercentage24h, 2),
        roundNullable(coinData.marketData?.priceChangePercentage7d, 2)
    )

    fun roundNullable(number: Double?, digits : Int): Double {
        if (number != null) {
            if (digits == 0) return round(number)
            val power = (10.0).pow(digits)
            return round(number * power)/power
        }
        return 0.00
    }
}