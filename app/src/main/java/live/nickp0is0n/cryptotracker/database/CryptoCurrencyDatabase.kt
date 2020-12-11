package live.nickp0is0n.cryptotracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

@Database(entities = [CryptoCurrency::class], version = 1)
abstract class CryptoCurrencyDatabase(): RoomDatabase() {
    abstract fun cryptocurrencydao(): CryptoCurrencyDao
}