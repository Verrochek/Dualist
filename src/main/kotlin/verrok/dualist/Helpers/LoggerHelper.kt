package verrok.dualist.Helpers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.InputStreamReader
import java.util.logging.Logger



class Messages {
    companion object {

        var msgCfg: FileConfiguration? = null

        fun initConfiguration(path: String) {
            msgCfg = YamlConfiguration.loadConfiguration(InputStreamReader(object {}.javaClass.classLoader.getResourceAsStream(path)!!));
        }

        operator fun get(key: String) : String {
            return msgCfg!!.getString(key)
        }
    }
}


fun String.mcformat() : String {
    return mcformat(true)
}

fun String.mcformat(prefix: Boolean) : String {
    var message: String = ""
    if (prefix) {
        message = "${Messages["prefix"]} $this"
    } else {
        message = this
    }
    return ChatColor.translateAlternateColorCodes('&', message)

}

fun Logger.log(msg: String) {
    Bukkit.getConsoleSender().sendMessage(msg.mcformat())
}

fun Logger.log(msg: String, vararg args: String) {

    args.forEach {
        msg.replaceFirst("{}", it);
    }

    Bukkit.getConsoleSender().sendMessage(msg.mcformat())
}