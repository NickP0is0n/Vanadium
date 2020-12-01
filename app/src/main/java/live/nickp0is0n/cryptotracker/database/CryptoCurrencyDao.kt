package live.nickp0is0n.cryptotracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import live.nickp0is0n.cryptotracker.models.CryptoCurrency

@Dao
interface CryptoCurrencyDao {
    @Query("SELECT * FROM cryptocurrency")
    fun getAll(): List<CryptoCurrency>

    @Query("SELECT * FROM cryptocurrency WHERE full_name LIKE :name LIMIT 1")
    fun findByName(name: String): CryptoCurrency

    @Query("SELECT * FROM cryptocurrency WHERE short_name LIKE :name LIMIT 1")
    fun findByShortName(name: String): CryptoCurrency

    @Insert
    fun insertAll(vararg currencies: CryptoCurrency)

    @Delete
    fun delete(currency: CryptoCurrency)
}