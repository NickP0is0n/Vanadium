package live.nickp0is0n.cryptotracker

import kotlin.math.pow
import kotlin.math.round

fun roundNullable(number: Double?, digits : Int): Double {
    if (number != null) {
        if (digits <= 0) return round(number)
        val power = (10.0).pow(digits)
        return round(number * power) /power
    }
    return 0.00
}