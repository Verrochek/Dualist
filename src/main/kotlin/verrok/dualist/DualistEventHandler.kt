package verrok.dualist

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import verrok.dualist.Helpers.mcformat


class DualistEventHandler : Listener {


    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {

        e.player.sendTitle("&6Hello, ${e.player.displayName}".mcformat(), "&l&4Welcome", 20, 100, 20);

    }

}