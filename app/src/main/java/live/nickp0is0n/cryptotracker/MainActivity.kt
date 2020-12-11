package live.nickp0is0n.cryptotracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import drewcarlson.coingecko.CoinGeckoService
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.database.DatabaseManager
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
            DatabaseManager.initializeDatabase(this@MainActivity)
            val currencyList = getCurrencyListFromDatabase()
            val intent = Intent(this@MainActivity, CryptoListActivity::class.java)
            intent.putExtra("currencyList", currencyList)
            startActivity(intent)
            finish()
        }
    }
}