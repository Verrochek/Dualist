package verrok.dualist

import kotlin.math.round
import kotlin.math.roundToInt

fun ticksFromSeconds(a: Float): Int {
    return (a * 20).roundToInt()
}

fun ticksFromSeconds(a: Int): Int {
    return a * 20
}