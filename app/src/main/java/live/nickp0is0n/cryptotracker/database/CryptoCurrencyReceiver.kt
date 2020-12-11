package live.nickp0is0n.cryptotracker.database

import drewcarlson.coingecko.CoinGeckoService
import drewcarlson.coingecko.models.coins.CoinFullData
import live.nickp0is0n.cryptotracker.adapter.CoinDataAdapter
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import java.util.*

class CryptoCurrencyReceiver(val database: CryptoCurrencyDatabase) {
    suspend fun getCurrencyList(): ArrayList<CryptoCurrency> {
        val service = CoinGeckoService()
        val dbList = database.cryptocurrencydao().getAll() as ArrayList<CryptoCurrency>
        for (i in 0 until dbList.size) {
            with (dbList[i]) {
                val adapter = CoinDataAdapter(service.getCoinById(id, marketData = true))
                dbList[i] = adapter.getCryptoCurrency()
                DatabaseManager.database!!.cryptocurrencydao().update(id, currentPrice, dayGrowth, weekGrowth)
            }
        }
        return dbList
    }
}