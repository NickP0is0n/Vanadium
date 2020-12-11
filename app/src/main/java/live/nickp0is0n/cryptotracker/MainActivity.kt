package live.nickp0is0n.cryptotracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.database.CryptoCurrencyManager
import live.nickp0is0n.cryptotracker.database.CryptoCurrencyReceiver
import live.nickp0is0n.cryptotracker.database.DatabaseManager
import live.nickp0is0n.cryptotracker.ui.CryptoListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            CryptoCurrencyManager.initialize(this@MainActivity)
            val currencyList = CryptoCurrencyManager.getActualCurrencyData()
            val intent = Intent(this@MainActivity, CryptoListActivity::class.java)
            intent.putExtra("currencyList", currencyList)
            startActivity(intent)
            finish()
        }
    }
}