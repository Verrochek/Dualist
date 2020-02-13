package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log
import verrok.dualist.Helpers.sendMessage
import java.util.logging.Logger

class DualistCommandHandler(val logger: Logger, val config: FileConfiguration) : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {

            val name = sender.displayName;

            if (args!!.isEmpty()) {
                sender.sendMessage(Messages["help"])
            } else {

                if (args[0] == "help") {
                    sender.sendMessage(Messages["help"])
                    return true;
                }

                var targetPlayer = Bukkit.getPlayer(args[0])

                when {
                    targetPlayer != null -> {

                        val targetName = targetPlayer.name;

                        if (args.size < 2) {
                            sender.sendMessage(Messages["noBet"])
                            return true;
                        }

                        val bet = args[1].toIntOrNull()

                        if (bet == null) {
                            sender.sendMessage(Messages["betType"])
                            return true
                        }

                        if (Dualist.duelList.contains(targetName) || Dualist.duelList.containsValue(targetName)) {
                            sender.sendMessage(Messages["alreadyInDuel"])
                            return true
                        }

                        sender.sendMessage(Messages["waitForAnswer"], targetName)
                        targetPlayer.sendMessage(Messages["newDuel"], name, bet.toString())
                        Dualist.duelInvitations[targetName] = name;

                    }
                    args[0] == "accept" -> {
                        if (Dualist.duelInvitations.contains(name)) {
                            targetPlayer = Bukkit.getPlayer(Dualist.duelInvitations[name])
                            targetPlayer.sendMessage(Messages["acceptedDuel"], name, config.getInt("startDelay"))
                            sender.sendMessage(Messages["youAcceptedDuel"], config.getInt("startDelay"))
                            Dualist.duelInvitations.remove(name)


                        } else {
                            sender.sendMessage(Messages["noDuels"])
                        }
                    }
                    args[0] == "decline" -> {
                        logger.log("Player &b{} &fdeclined duel", name)
                        if (Dualist.duelInvitations.contains(name)) {
                            targetPlayer = Bukkit.getPlayer(Dualist.duelInvitations[name])
                            targetPlayer.sendMessage(Messages["declinedDuel"], name)
                            sender.sendMessage(Messages["youDeclinedDuel"])

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