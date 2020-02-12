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
            msgCfg = YamlConfiguration.loadConfiguration(InputStreamReader(object {}.javaClass.classLoader.getResourceAsStream(path)!!, Charsets.UTF_8));
        }

        operator fun get(key: String) : String {
            return msgCfg!!.getString(key).mcformat()
        }
    }
}


fun String.mcformat() : String {
    return mcformat(false)
}

fun String.mcformat(prefix: Boolean) : String {
    val message: String = if (prefix) {
        "${Messages["prefix"]} $this"
    } else {
        this
    }
    return message.replace("&", "§")

}

fun Logger.log(msg: String) {
    Bukkit.getConsoleSender().sendMessage(msg.mcformat(true))
}

fun Logger.log(msg: String, vararg args: String) {

    args.forEach {
        msg.replaceFirst("{}", it);
    }

    Bukkit.getConsoleSender().sendMessage(msg.mcformat(true))
}