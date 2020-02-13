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
    }

    override fun onEnable() {
        saveDefaultConfig()
        verbose = config.getBoolean("verbose")

        Messages.initConfiguration("messages.yml");
        logger.log(Messages["enable"])

        Bukkit.getServer().pluginManager.registerEvents(DualistEventHandler(), this);
        getCommand("duel")!!.executor = DualistCommandHandler(logger)
        getCommand("duel")!!.tabCompleter = DualistTabCompleter()
    }

    override fun onDisable() {

    }
}