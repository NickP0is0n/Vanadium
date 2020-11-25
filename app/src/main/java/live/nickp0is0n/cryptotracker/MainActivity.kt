package live.nickp0is0n.cryptotracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.ui.CryptoListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            delay(2000)
            val intent = Intent(this@MainActivity, CryptoListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}