package verrok.dualist

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
        val countdown: MutableMap<String, Int> = mutableMapOf()

        fun isInDuel(name: String) : Boolean {
            return duelList.contains(name) || duelList.containsValue(name)
        }

        fun isInDuelWith(name: String, name2: String) : Boolean {
            return duelList[name] == name2 || duelList[name2] == name
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