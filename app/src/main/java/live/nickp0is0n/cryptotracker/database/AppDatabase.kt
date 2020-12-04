package live.nickp0is0n.cryptotracker.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

object AppDatabase {
    var database: SQLiteDatabase? = null

    fun initializeDatabase(context: Context) {
        if (database != null) Log.w("Database", "App database is already initialized.")
        else {
            database = Room.databaseBuilder(
                    context,
                    SQLiteDatabase::class.java, "currency-list"
                    ).allowMainThreadQueries() //WORKAROUND: Database doesn't work without this, even if in coroutine
                    .build()
        }
    }
}

@Database(entities = arrayOf(CryptoCurrency::class), version = 1)
abstract class SQLiteDatabase(): RoomDatabase() {
    abstract fun cryptocurrencydao(): CryptoCurrencyDao
}
