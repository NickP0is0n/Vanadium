package live.nickp0is0n.cryptotracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import live.nickp0is0n.cryptotracker.R

class AdvancedInfoActivity : AppCompatActivity() {
    private lateinit var cryptoCurrencyInfo: AdvancedInfoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_info)
        cryptoCurrencyInfo = intent.extras?.get("info") as AdvancedInfoActivity
    }
}