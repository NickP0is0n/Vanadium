package live.nickp0is0n.cryptotracker.models

data class CryptoCurrency(
    val shortName: String,
    val fullName: String,
    val currentPrice: Double,
    val dayGrowth: Double,
    val weekGrowth: Double
)