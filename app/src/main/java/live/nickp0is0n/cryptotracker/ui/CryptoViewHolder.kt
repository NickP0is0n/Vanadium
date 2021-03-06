package live.nickp0is0n.cryptotracker.ui

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import drewcarlson.coingecko.CoinGeckoService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.models.AdvancedCryptoCurrency
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import live.nickp0is0n.cryptotracker.network.CryptoCurrencyDataFetcher
import java.util.*

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var currency: CryptoCurrency

    val viewBorders: View = itemView.findViewById(R.id.view2)
    val shortName: TextView = itemView.findViewById(R.id.shortName)
    val fullName: TextView = itemView.findViewById(R.id.fullName)
    private val price: TextView = itemView.findViewById(R.id.price)
    val twentyFourHoursPercent: TextView = itemView.findViewById(R.id.twentyFourHoursPercent)
    val sevenDaysPercent: TextView = itemView.findViewById(R.id.sevenDaysPercent)
    val cryptoLogo: ImageView = itemView.findViewById(R.id.cryptoLogo)

    fun bind(currency: CryptoCurrency) {
        this.currency = currency

        initializeText()
        initializePercentage()
        initializeLogo()

        setOnClickListener()
    }

    private fun initializeText() {
        shortName.text = currency.shortName
        fullName.text = currency.fullName
        price.text = "${currency.currentPrice}$"
    }

    private fun initializePercentage() {
        if (currency.dayGrowth < 0) {
            twentyFourHoursPercent.setTextColor(itemView.resources.getColor(R.color.red))
        }
        else if (currency.dayGrowth > 0 ) {
            twentyFourHoursPercent.setTextColor(itemView.resources.getColor(R.color.green))
        }
        twentyFourHoursPercent.text = "${currency.dayGrowth}%"

        if (currency.weekGrowth < 0) {
            sevenDaysPercent.setTextColor(itemView.resources.getColor(R.color.red))
        }
        else if (currency.weekGrowth > 0 ) {
            sevenDaysPercent.setTextColor(itemView.resources.getColor(R.color.green))
        }
        sevenDaysPercent.text = "${currency.weekGrowth}%"
    }

    private fun initializeLogo() {
        cryptoLogo.setImageResource(itemView.context.resources.getIdentifier(
                (fullName.text as String).toLowerCase(Locale.ROOT).replace(' ', '_'),
                "mipmap",
                itemView.context.packageName
        ))
    }

    private fun setOnClickListener() {
        viewBorders.setOnClickListener {
            GlobalScope.launch {
                val dataFetcher = CryptoCurrencyDataFetcher()
                val data = dataFetcher.getCoinMarketChart(currency, days = 3)
                val context = it.context
                val intent = Intent(context, AdvancedInfoActivity::class.java)
                intent.putExtra("info", data)
                context.startActivity(intent)
            }
        }
    }
}