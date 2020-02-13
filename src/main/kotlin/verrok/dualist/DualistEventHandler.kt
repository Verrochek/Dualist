package verrok.dualist

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import verrok.dualist.Helpers.mcformat
import java.util.logging.Logger


class DualistEventHandler(val plugin: JavaPlugin, val logger: Logger, val config: FileConfiguration) : Listener {


    @EventHandler
    fun onDamagePlayer(e: EntityDamageByEntityEvent) {
        if (e.damager is Player && e.entity is Player) {
            val name = e.damager.name
            val targetName = e.entity.name
            if (Dualist.isInDuel(targetName)) {
                if (!Dualist.isInDuelWith(targetName, name)) {
                    e.isCancelled = true
                    return
                }
            }
        }
    }

}