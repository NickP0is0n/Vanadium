package live.nickp0is0n.cryptotracker.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CryptoCurrency(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "short_name") val shortName: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "current_price") val currentPrice: Double,
    @ColumnInfo(name = "day_growth") val dayGrowth: Double,
    @ColumnInfo(name = "week_growth") val weekGrowth: Double
) : Parcelable