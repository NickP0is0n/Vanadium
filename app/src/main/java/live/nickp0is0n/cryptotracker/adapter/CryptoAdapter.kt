package live.nickp0is0n.cryptotracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import java.util.*


class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val shortName: TextView = itemView.findViewById(R.id.shortName)
    val fullName: TextView = itemView.findViewById(R.id.fullName)
    private val price: TextView = itemView.findViewById(R.id.price)
    val twentyFourHoursPercent: TextView = itemView.findViewById(R.id.twentyFourHoursPercent)
    val sevenDaysPercent: TextView = itemView.findViewById(R.id.sevenDaysPercent)
    val cryptoLogo: ImageView = itemView.findViewById(R.id.cryptoLogo)

    fun bind(currency: CryptoCurrency) {
        shortName.text = currency.shortName
        fullName.text = currency.fullName
        price.text = "${currency.currentPrice}$"

        if (currency.dayGrowth < 0) twentyFourHoursPercent.setTextColor(itemView.resources.getColor(R.color.red))
        else if (currency.dayGrowth > 0 ) twentyFourHoursPercent.setTextColor(itemView.resources.getColor(R.color.green))
        twentyFourHoursPercent.text = "${currency.dayGrowth}%"

        if (currency.weekGrowth < 0) sevenDaysPercent.setTextColor(itemView.resources.getColor(R.color.red))
        else if (currency.weekGrowth > 0 ) sevenDaysPercent.setTextColor(itemView.resources.getColor(R.color.green))
        sevenDaysPercent.text = "${currency.weekGrowth}%"

        cryptoLogo.setImageResource(itemView.context.resources.getIdentifier(
            (fullName.text as String).toLowerCase(Locale.ROOT).replace(' ', '_'),
            "mipmap",
            itemView.context.packageName
        ))
    }
}

class CryptoAdapter: RecyclerView.Adapter<CryptoViewHolder>() {
    private val cryptocurrencyList = mutableListOf<CryptoCurrency>()

    fun setItems(currencies: Collection<CryptoCurrency>) {
        cryptocurrencyList.addAll(currencies)
        notifyDataSetChanged()
    }

    fun clearItems() {
        cryptocurrencyList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crypto_row_layout, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptocurrencyList[position])
    }

    override fun getItemCount(): Int = cryptocurrencyList.size
}