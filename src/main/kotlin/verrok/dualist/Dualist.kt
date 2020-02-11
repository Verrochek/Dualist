package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log


class Dualist : JavaPlugin() {

    override fun onEnable() {

        Messages.initConfiguration("messages.yml");

        logger.log(Messages["enable"])
        Bukkit.getServer().pluginManager.registerEvents(DualistEventHandler(), this);
        getCommand("duel")!!.executor = DualistCommandHandler()
        getCommand("duel")!!.tabCompleter = DualistTabCompleter()
    }

    override fun onDisable() {

    }
}