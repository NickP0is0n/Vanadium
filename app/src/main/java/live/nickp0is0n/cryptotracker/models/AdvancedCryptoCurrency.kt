package live.nickp0is0n.cryptotracker.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvancedCryptoCurrency (
    val currency: CryptoCurrency,
    val priceHistory: List<Double>
) : Parcelable