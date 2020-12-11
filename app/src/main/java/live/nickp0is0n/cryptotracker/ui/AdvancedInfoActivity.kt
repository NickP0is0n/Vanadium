package live.nickp0is0n.cryptotracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_advanced_info.*
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.database.CryptoCurrencyReceiver
import live.nickp0is0n.cryptotracker.database.DatabaseManager
import live.nickp0is0n.cryptotracker.models.AdvancedCryptoCurrency
import java.util.*


class AdvancedInfoActivity : AppCompatActivity() {
    private lateinit var cryptoCurrencyInfo: AdvancedCryptoCurrency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_info)
        cryptoCurrencyInfo = intent.extras?.get("info") as AdvancedCryptoCurrency
        bindDataToUI()
    }

    fun onRemoveFromListButtonListener(view: View) {
        lifecycleScope.launch {
            val currencyReceiver = CryptoCurrencyReceiver(DatabaseManager.database!!)
            DatabaseManager.database!!.cryptocurrencydao().delete(cryptoCurrencyInfo.currency)
            val intent = Intent(this@AdvancedInfoActivity, CryptoListActivity::class.java)
            val currencyList = currencyReceiver.getCurrencyList()
            intent.putExtra("currencyList", currencyList)
            startActivity(intent)
            finish()
        }
    }

    private fun bindDataToUI() {
        val dataPointList = mutableListOf<DataPoint>()
        cryptoCurrencyInfo.priceHistory.forEachIndexed { index, price ->
            run {
                dataPointList.add(DataPoint(index.toDouble(), price))
            }
        }
        val series = LineGraphSeries(dataPointList.toTypedArray())
        coinGraph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    ""
                } else {
                    // show currency for y values
                    super.formatLabel(value, isValueX) + "$"
                }
            }
        }
        coinGraph.addSeries(series)

        advancedInfoTitle.text = cryptoCurrencyInfo.currency.fullName
        cryptoLogoImageView.setImageResource(resources.getIdentifier(
            cryptoCurrencyInfo.currency.fullName.toLowerCase(Locale.ROOT).replace(' ', '_'),
            "mipmap",
            packageName
        ))

        currentPriceView.text = "${cryptoCurrencyInfo.currency.currentPrice}$"

        if (cryptoCurrencyInfo.currency.dayGrowth < 0) dailyGrowthView.setTextColor(resources.getColor(R.color.red))
        else if (cryptoCurrencyInfo.currency.dayGrowth > 0) dailyGrowthView.setTextColor(resources.getColor(R.color.green))
        dailyGrowthView.text = "${cryptoCurrencyInfo.currency.dayGrowth}%"

        if (cryptoCurrencyInfo.currency.weekGrowth < 0) weeklyGrowthView.setTextColor(resources.getColor(R.color.red))
        else if (cryptoCurrencyInfo.currency.weekGrowth > 0) weeklyGrowthView.setTextColor(resources.getColor(R.color.green))
        weeklyGrowthView.text = "${cryptoCurrencyInfo.currency.weekGrowth}%"
    }
}