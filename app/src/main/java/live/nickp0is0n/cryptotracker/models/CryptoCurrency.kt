package live.nickp0is0n.cryptotracker.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoCurrency(
    val id: String,
    val shortName: String,
    val fullName: String,
    val currentPrice: Double,
    val dayGrowth: Double,
    val weekGrowth: Double
) : Parcelable