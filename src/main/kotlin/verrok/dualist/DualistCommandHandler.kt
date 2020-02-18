package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log
import verrok.dualist.Helpers.mcformat
import verrok.dualist.Helpers.sendMessage
import java.util.logging.Logger

class DualistCommandHandler(val plugin: JavaPlugin, val logger: Logger, val config: FileConfiguration) : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {

            val name = sender.uniqueId
            val maxDistance = config.getInt("maxDistance")
            if (args!!.isEmpty() && sender.hasPermission("dualist.help")) {
                sender.sendMessage(Messages["help"])
            } else {

                if (args[0] == "help" && sender.hasPermission("dualist.help")) {
                    sender.sendMessage(Messages["help"])
                    return true
                }

                if (!sender.hasPermission("dualist.use")) {
                    return true
                }

                val targetPlayer = Bukkit.getPlayer(args[0])

                when {
                    targetPlayer != null -> {

                        val targetName = targetPlayer.uniqueId

                        if (Dualist.isInDuel(targetName)) {
                            sender.sendMessage(Messages["haveDuel"], targetPlayer.name)
                            return true
                        }

                        if (args.size < 2) {
                            sender.sendMessage(Messages["noBet"])
                            return true
                        }

                        val bet = args[1].toIntOrNull()

                        if (bet == null) {
                            sender.sendMessage(Messages["betType"])
                            return true
                        }

                        if (sender.location.distance(targetPlayer.location).toInt() > maxDistance) {
                            sender.sendMessage(Messages["maxCallDistance"])
                            return true
                        }

                        if (!Dualist.econ!!.has(sender, bet.toDouble())) {
                            sender.sendMessage(Messages["noMoney"])
                            return true
                        }

                        if (!Dualist.econ!!.has(targetPlayer, bet.toDouble())) {
                            sender.sendMessage(Messages["noEnemyMoney"].mcformat(targetPlayer.name))
                            return true
                        }

                        sender.sendMessage(Messages["waitForAnswer"], targetPlayer.name)
                        targetPlayer.sendMessage(Messages["newDuel"], sender.name, bet.toString())
                        Dualist.duelBets[targetName] = bet.toDouble()
                        Dualist.duelInvitations[targetName] = name
                    }
                    args[0] == "accept" -> {
                        if (Dualist.duelInvitations.contains(name)) {
                            val target = Bukkit.getPlayer(Dualist.duelInvitations[name])
                            target.sendMessage(Messages["acceptedDuel"], sender.name, config.getInt("startDelay"))
                            sender.sendMessage(Messages["youAcceptedDuel"], config.getInt("startDelay"))

                            Dualist.duelList[name] = target.uniqueId
                            Dualist.duelInvitations.remove(name)
                            var count = config.getInt("startDelay")

                            Dualist.countdown[name] = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                                if (count > 0) {
                                    if (count == config.getInt("startDelay")) {
                                        target.sendTitle(Messages["willStart"], "", 5, 20, 5)
                                        sender.sendTitle(Messages["willStart"], "", 5, 20, 5)
                                    } else {
                                        target.sendTitle("&a$count".mcformat(), "", 5, 20, 5)
                                        sender.sendTitle("&a$count".mcformat(), "", 5, 20, 5)
                                    }
                                } else {
                                    target.sendTitle(Messages["started"], "", 5, 20, 5)
                                    sender.sendTitle(Messages["started"], "", 5, 20, 5)
                                    Bukkit.getScheduler().cancelTask(Dualist.countdown[target.uniqueId]!!)
                                    Bukkit.getScheduler().cancelTask(Dualist.countdown[name]!!)
                                    Dualist.countdown.remove(target.uniqueId)
                                    Dualist.countdown.remove(name)
                                    logger.log("&eLeft tasks")
                                }
                                count--
                            }, 0, 30)
                            Dualist.countdown[target.uniqueId] = Dualist.countdown[name]!!
                        } else {
                            sender.sendMessage(Messages["noDuels"])
                        }
                    }
                    args[0] == "decline" -> {
                        if (Dualist.duelInvitations.contains(name)) {
                            val target = Bukkit.getPlayer(Dualist.duelInvitations[name])
                            target.sendMessage(Messages["declinedDuel"], sender.name)
                            sender.sendMessage(Messages["youDeclinedDuel"])

                            Dualist.duelBets.remove(name)
                            Dualist.duelInvitations.remove(name)
                        } else {
                            sender.sendMessage(Messages["noDuels"])
                        }
                    }
                    else -> {
                        sender.sendMessage(Messages["help"])
                    }

                }
            }
        }
        return true
    }

}