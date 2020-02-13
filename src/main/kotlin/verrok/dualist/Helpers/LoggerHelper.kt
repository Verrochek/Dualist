package verrok.dualist.Helpers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import verrok.dualist.Dualist
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

fun String.mcformat(vararg args: Any) : String {
    var newmsg: String = this
    args.forEach {
        newmsg = newmsg.replaceFirst("{}", it.toString());
    }
    return newmsg
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
    if (Dualist.verbose) {
        Bukkit.getConsoleSender().sendMessage(msg.mcformat(true))
    }
}

fun Logger.log(msg: String, vararg args: Any) {
    var newmsg = msg;

    if (Dualist.verbose) {
        args.forEach {
            newmsg = newmsg.replaceFirst("{}", it.toString());
        }

        Bukkit.getConsoleSender().sendMessage(newmsg.mcformat(true))
    }
}

fun Player.sendMessage(msg: String, vararg args: Any) {
    var newmsg = msg;
    args.forEach {
        newmsg = newmsg.replaceFirst("{}", it.toString());
    }
    this.sendMessage(newmsg.mcformat(false))

}