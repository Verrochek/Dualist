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
                sender.sendMessage(Messages["noArgs"])
            } else {
                val targetPlayer = Bukkit.getPlayer(args[0])

                if (targetPlayer != null) {

                } else if (args[0] == "accept") {

                } else if (args[0] == "decline") {

                }
            }
        }
        return true
    }

}