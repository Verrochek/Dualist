package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log
import verrok.dualist.Helpers.mcformat
import java.util.logging.Logger

class DualistCommandHandler(val logger: Logger) : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {
            if (args!!.isEmpty()) {
                sender.sendMessage(Messages["help"])
            } else {

                if (args[0] == "help") {
                    sender.sendMessage(Messages["help"])
                    return true;
                }

                val targetPlayer = Bukkit.getPlayer(args[0])

                if (targetPlayer != null) {
                    logger.log("Found player {}", sender.displayName)
                } else if (args[0] == "accept") {
                    logger.log("Player {} accepted duel", sender.displayName)
                } else if (args[0] == "decline") {
                    logger.log("Player {} declined duel", sender.displayName)

                }
            }
        }
        return true
    }

}