package live.nickp0is0n.cryptotracker.network

import drewcarlson.coingecko.CoinGeckoService
import live.nickp0is0n.cryptotracker.adapter.CoinDataAdapter
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

class CryptoCurrencyDataFetcher {
    suspend fun fetchCurrency(id: String): CryptoCurrency? {
        val service = CoinGeckoService()
        val rawData = service.getCoinById(id, marketData = true)
        val adapter = CoinDataAdapter(rawData)
        return adapter.getCryptoCurrency()
    }

}