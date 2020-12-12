package live.nickp0is0n.cryptotracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.nickp0is0n.cryptotracker.R
import live.nickp0is0n.cryptotracker.models.CryptoCurrency


class CryptoAdapter: RecyclerView.Adapter<CryptoViewHolder>() {
    private val cryptocurrencyList = mutableListOf<CryptoCurrency>()

    fun setItems(currencies: Collection<CryptoCurrency>) {
        cryptocurrencyList.clear()
        cryptocurrencyList.addAll(currencies)
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