package verrok.dualist

import com.sun.org.apache.xpath.internal.operations.Bool
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log


class Dualist : JavaPlugin() {

    companion object {
        var verbose = true
        val duelInvitations: MutableMap<String, String> = mutableMapOf()
        val duelList: MutableMap<String, String> = mutableMapOf()
        val duelBets: MutableMap<String, Int> = mutableMapOf()
        val countdown: MutableMap<String, Int> = mutableMapOf()

        fun isInDuel(name: String) : Boolean {
            return duelList.contains(name) || duelList.containsValue(name)
        }

        fun isInDuelWith(name: String, name2: String) : Boolean {
            return duelList[name] == name2 || duelList[name2] == name
        }

        fun isInitiator(name: String) : Boolean {
            return duelList.contains(name)
        }

        fun isParticipant(name: String) : Boolean {
            return duelList.containsValue(name)
        }

        fun getBet(name: String) : Int {
            if (isInitiator(name)) {
                if (duelBets[name] != null)
                    return duelBets[name]!!
            }
            if (isParticipant(name)) {
                duelList.keys.forEach lit@{
                    if (duelList[it] == name) {
                        return duelBets[it]!!
                    }
                }
            }
            return 0;
        }

    }

    override fun onEnable() {
        saveDefaultConfig()
        verbose = config.getBoolean("verbose")

        Messages.initConfiguration("messages.yml");
        logger.log(Messages["enable"])

        Bukkit.getServer().pluginManager.registerEvents(DualistEventHandler(this, logger, config), this);
        getCommand("duel")!!.executor = DualistCommandHandler(this, logger, config)
        getCommand("duel")!!.tabCompleter = DualistTabCompleter()
    }

    override fun onDisable() {

    }
}