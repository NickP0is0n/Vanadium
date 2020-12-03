package live.nickp0is0n.cryptotracker.database

import drewcarlson.coingecko.CoinGeckoService
import drewcarlson.coingecko.models.coins.CoinFullData
import live.nickp0is0n.cryptotracker.models.CryptoCurrency
import java.util.*
import kotlin.math.pow
import kotlin.math.round

suspend fun getCurrencyListFromDatabase(): ArrayList<CryptoCurrency> {
    val service = CoinGeckoService()
    val dbList = AppDatabase.database!!.cryptocurrencydao().getAll() as ArrayList<CryptoCurrency>
    for (i in 0 until dbList.size) {
        with (dbList[i]) {
            dbList[i] = getCryptoCurrencyFromCoinData(service.getCoinById(id, marketData = true))
            AppDatabase.database!!.cryptocurrencydao().update(id, currentPrice, dayGrowth, weekGrowth)
        }
    }
    return dbList
}

fun getCryptoCurrencyFromCoinData(coinData: CoinFullData): CryptoCurrency = CryptoCurrency(
    coinData.id,
    coinData.symbol.toUpperCase(Locale.ROOT),
    coinData.name,
    coinData.marketData?.currentPrice?.get("usd") ?: 0.00,
    roundNullable(coinData.marketData?.priceChangePercentage24h, 2),
    roundNullable(coinData.marketData?.priceChangePercentage7d, 2)
)

fun roundNullable(number: Double?, digits : Int): Double {
    if (number != null) {
        if (digits == 0) return round(number)
        val power = (10.0).pow(digits)
        return round(number * power) /power
    }
    return 0.00
}