package live.nickp0is0n.cryptotracker.network

import drewcarlson.coingecko.CoinGeckoService
import live.nickp0is0n.cryptotracker.adapter.CoinDataAdapter
import live.nickp0is0n.cryptotracker.models.AdvancedCryptoCurrency
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

class CryptoCurrencyDataFetcher {
    suspend fun fetchCurrency(id: String): CryptoCurrency? {
        val service = CoinGeckoService()
        val rawData = service.getCoinById(id, marketData = true)
        val adapter = CoinDataAdapter(rawData)
        return adapter.getCryptoCurrency()
    }

    suspend fun getCoinMarketChart(currency: CryptoCurrency, days: Int): AdvancedCryptoCurrency {
        val service = CoinGeckoService()
        val rawChart = service.getCoinMarketChartById(currency.id, "usd", days)

        val priceHistory = mutableListOf<Double>()
        rawChart.prices.forEach {
            priceHistory.add(it[1].toDouble())
        }
        return AdvancedCryptoCurrency(currency, priceHistory)
    }
}