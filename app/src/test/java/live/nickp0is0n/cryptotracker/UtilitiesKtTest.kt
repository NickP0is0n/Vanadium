package live.nickp0is0n.cryptotracker

import org.junit.Test

import org.junit.Assert.*

class UtilitiesKtTest {

    @Test
    fun roundNullableWithNotNullValueTest() {
        val roundedValue = roundNullable(3.1415, 2)
        assertEquals(3.14, roundedValue, 0.0)
    }

    @Test
    fun roundNullableWithNullValueTest() {
        val roundedValue = roundNullable(null, 2)
        assertEquals(0.0, roundedValue, 0.0)
    }

    @Test
    fun roundNullableWithMinusDigitsTest() {
        val roundedValue = roundNullable(3.1415, -1)
        assertEquals(3.0, roundedValue, 0.0)
    }
}