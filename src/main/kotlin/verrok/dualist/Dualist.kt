package verrok.dualist

import com.sun.org.apache.xpath.internal.operations.Bool
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log
import java.util.*


class Dualist : JavaPlugin() {

    companion object {
        var verbose = true
        val duelInvitations: MutableMap<UUID, UUID> = mutableMapOf()
        val duelList: MutableMap<UUID, UUID> = mutableMapOf()
        val duelBets: MutableMap<UUID, Double> = mutableMapOf()
        val countdown: MutableMap<UUID, Int> = mutableMapOf()
        val deathScreen: MutableMap<UUID, Double> = mutableMapOf()


        fun isInDuel(name: UUID) : Boolean {
            return duelList.contains(name) || duelList.containsValue(name)
        }

        fun isInDuelWith(name: UUID, name2: UUID) : Boolean {
            return duelList[name] == name2 || duelList[name2] == name
        }

        fun isInitiator(name: UUID) : Boolean {
            return duelList.contains(name)
        }

        fun isParticipant(name: UUID) : Boolean {
            return duelList.containsValue(name)
        }

        fun isWaiting(uuid: UUID) : Boolean {
            return countdown.contains(uuid)
        }


        fun getBet(name: UUID) : Double {
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
            return 0.0
        }

        fun getAnotherPlayer(name: UUID) : UUID? {
            var rt: UUID? = null
            if (duelList.contains(name))
                return duelList[name]
            else {
                duelList.keys.forEach search@{
                    if (duelList[it] == name) {
                        rt = it
                        return@search
                    }
                }
                return rt
            }
        }

        var econ: Economy? = null
    }

    override fun onEnable() {
        saveDefaultConfig()
        val lang = config.getString("lang")
        verbose = config.getBoolean("verbose")

        saveResource("langs/$lang.yml", true)
        Messages.initConfiguration("langs/$lang.yml")

        if (!setupEconomy() ) {
            logger.log(Messages["noDependency"]);
            server.pluginManager.disablePlugin(this)
            return
        }
        logger.log(Messages["enable"])



        Bukkit.getServer().pluginManager.registerEvents(DualistEventHandler(this, logger, config), this)
        getCommand("duel")!!.executor = DualistCommandHandler(this, logger, config)
    }

    override fun onDisable() {
        countdown.keys.forEach {
            Bukkit.getScheduler().cancelTask(countdown[it]!!)
        }
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return false
        econ = rsp.provider
        return econ != null
    }




}