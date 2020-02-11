package verrok.dualist.Helpers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.logging.Logger
import kotlin.math.roundToInt

fun ticksFromSeconds(a: Float): Int {
    return (a * 20).roundToInt()
}

fun ticksFromSeconds(a: Int): Int {
    return a * 20
}


