package live.nickp0is0n.cryptotracker.adapter

import drewcarlson.coingecko.models.coins.CoinFullData
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import java.util.*
import kotlin.math.pow
import kotlin.math.round

class CoinDataAdapter (val coinData: CoinFullData) {
    fun getCryptoCurrency(): CryptoCurrency = CryptoCurrency(
            coinData.id,
            coinData.symbol.toUpperCase(Locale.ROOT),
            coinData.name,
            coinData.marketData?.currentPrice?.get("usd") ?: 0.00,
            roundNullable(coinData.marketData?.priceChangePercentage24h, 2),
            roundNullable(coinData.marketData?.priceChangePercentage7d, 2)
    )

    private fun roundNullable(number: Double?, digits : Int): Double {
        if (number != null) {
            if (digits == 0) return round(number)
            val power = (10.0).pow(digits)
            return round(number * power) /power
        }
        return 0.00
    }
}