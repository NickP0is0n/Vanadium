package live.nickp0is0n.cryptotracker.database

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseManager {
    var database: CryptoCurrencyDatabase? = null

    fun initializeDatabase(context: Context) {
        if (database != null) Log.w("Database", "App database is already initialized.")
        else {
            database = Room.databaseBuilder(
                    context,
                    CryptoCurrencyDatabase::class.java, "currency-list"
                    ).allowMainThreadQueries() //WORKAROUND: Database doesn't work without this, even if in coroutine
                    .build()
        }
    }
}

