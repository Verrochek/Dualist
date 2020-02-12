package verrok.dualist

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import verrok.dualist.Helpers.Messages

class DualistCommandHandler : CommandExecutor {

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        if (sender is Player) {
            if (args!!.isEmpty()) {
                sender.sendMessage(Messages["noArgs"])
            }
        }
        return true
    }

}