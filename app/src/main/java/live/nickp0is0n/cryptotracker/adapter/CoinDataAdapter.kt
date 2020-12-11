package live.nickp0is0n.cryptotracker.adapter

import drewcarlson.coingecko.models.coins.CoinFullData
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import live.nickp0is0n.cryptotracker.roundNullable
import java.util.*

class CoinDataAdapter (val coinData: CoinFullData) {
    fun getCryptoCurrency(): CryptoCurrency = CryptoCurrency(
            coinData.id,
            coinData.symbol.toUpperCase(Locale.ROOT),
            coinData.name,
            coinData.marketData?.currentPrice?.get("usd") ?: 0.00,
            roundNullable(coinData.marketData?.priceChangePercentage24h, 2),
            roundNullable(coinData.marketData?.priceChangePercentage7d, 2)
    )
}