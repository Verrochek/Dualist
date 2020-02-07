package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class Dualist : JavaPlugin() {

    override fun onEnable() {
        logger.info("enabled")
        Bukkit.getServer().pluginManager.registerEvents(DualistEventHandler(), this)

        getCommand("duel")!!.executor = DualistCommandHandler()
        getCommand("duel")!!.tabCompleter = DualistTabCompleter()
    }

    override fun onDisable() {

    }
}