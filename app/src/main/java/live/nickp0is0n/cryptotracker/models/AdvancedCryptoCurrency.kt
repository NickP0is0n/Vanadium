package live.nickp0is0n.cryptotracker.models

data class AdvancedCryptoCurrency (
    val currency: CryptoCurrency,
    val priceHistory: List<Double>
)