package live.nickp0is0n.cryptotracker.database

import android.content.Context
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import java.util.ArrayList

object CryptoCurrencyManager {
    private var database: CryptoCurrencyDatabase? = null

    fun initialize(context: Context) {
        DatabaseManager.initializeDatabase(context)
        database = DatabaseManager.database
    }

    fun getCurrencyList() = database!!.cryptocurrencydao().getAll()

    fun add(currency: CryptoCurrency) {
        database!!.cryptocurrencydao().insertAll(currency)
    }

    fun addAll(currencyList: List<CryptoCurrency>) {
        database!!.cryptocurrencydao().insertAll(*currencyList.toTypedArray())
    }

    suspend fun getActualCurrencyData(): ArrayList<CryptoCurrency> {
        val receiver = CryptoCurrencyReceiver(database!!)
        return receiver.getCurrencyList()
    }

    fun search(query: String) : CryptoCurrency? {
        var result = database!!.cryptocurrencydao().findByName(query)
        if (result == null) result = database!!.cryptocurrencydao().findByShortName(query)
        return result
    }

    fun remove(currency: CryptoCurrency) {
        database!!.cryptocurrencydao().delete(currency)
    }
}