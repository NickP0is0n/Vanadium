package live.nickp0is0n.cryptotracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_add_currency.*
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.database.CryptoCurrencyManager
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import live.nickp0is0n.cryptotracker.network.CryptoCurrencyDataFetcher
import java.util.*

class AddCurrencyActivity : AppCompatActivity() {
    private lateinit var coinList: List<String>
    private lateinit var coinIdList: List<String>
    private var activeCurrency: CryptoCurrency? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_currency)
        coinList = intent.extras!!["coinList"] as List<String>
        coinIdList = intent.extras!!["coinIdList"] as List<String>
    }

    fun onSearchButtonClick(view: View) {
        val request = acSearchCurrencyField.text.toString()
        lifecycleScope.launch {
            val reply = coinList.firstOrNull {
                it.equals(request, ignoreCase = true)
            }
            if (reply == null) {
                Toast.makeText(this@AddCurrencyActivity, "Currency not found.", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                val dataFetcher = CryptoCurrencyDataFetcher()
                activeCurrency = dataFetcher.fetchCurrency(coinIdList[coinList.indexOf(reply)])
                runOnUiThread {
                    bindActiveCurrencyToUI()
                    showCurrencyInfoViews()
                }
            }
        }
    }

    fun onAddToListButtonClick(view: View) {
        val currency = activeCurrency
        if (currency != null) {
            lifecycleScope.launch {
                CryptoCurrencyManager.add(currency)
                val intent = Intent(this@AddCurrencyActivity, CryptoListActivity::class.java)
                val currencyList = CryptoCurrencyManager.getActualCurrencyData()
                intent.putExtra("currencyList", currencyList)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun bindActiveCurrencyToUI() {
        val currency = activeCurrency!!
        acLogoView.setImageResource(resources.getIdentifier(
            currency.fullName.toLowerCase(Locale.ROOT).replace(' ', '_'),
            "mipmap",
            packageName
        ))
        acShortCoinNameView.text = currency.shortName
        acFullCoinNameView.text = currency.fullName
        acCurrentPriceView.text = "${currency.currentPrice}$"
        acDailyGrowthView.text = "${currency.dayGrowth}%"
        acWeeklyGrowthView.text = "${currency.weekGrowth}%"
    }

    private fun showCurrencyInfoViews() {
        acLogoView.isVisible = true

        acCurrentPriceTitleView.isVisible = true
        acDailyGrowthTitleView.isVisible = true
        acWeeklyGrowthTitleView.isVisible = true

        acShortCoinNameView.isVisible = true
        acFullCoinNameView.isVisible = true
        acCurrentPriceView.isVisible = true
        acDailyGrowthView.isVisible = true
        acWeeklyGrowthView.isVisible = true

        acAddToListButton.isVisible = true
        acAddToListText.isVisible = true
    }
}