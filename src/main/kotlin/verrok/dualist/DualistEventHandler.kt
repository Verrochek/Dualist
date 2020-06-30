package verrok.dualist

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.ThrownPotion
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.event.server.ServerCommandEvent
import org.bukkit.plugin.java.JavaPlugin
import org.spigotmc.event.player.PlayerSpawnLocationEvent
import verrok.dualist.Helpers.Messages
import verrok.dualist.Helpers.log
import verrok.dualist.Helpers.mcformat
import java.util.logging.Logger


class DualistEventHandler(val plugin: JavaPlugin, val logger: Logger, val config: FileConfiguration) : Listener {

    private val restrictedCauses = mutableListOf<PlayerTeleportEvent.TeleportCause>()

    init {
        restrictedCauses.add(PlayerTeleportEvent.TeleportCause.COMMAND)
        restrictedCauses.add(PlayerTeleportEvent.TeleportCause.PLUGIN)
        restrictedCauses.add(PlayerTeleportEvent.TeleportCause.SPECTATE)
        restrictedCauses.add(PlayerTeleportEvent.TeleportCause.UNKNOWN)
    }

    fun cancellEvent(sender: Player, receiver: Player, e: Cancellable) {
        val name = sender.uniqueId
        val targetName = receiver.uniqueId
        if (Dualist.isInDuel(targetName)) {
            if (!Dualist.isInDuelWith(targetName, name) || (Dualist.isWaiting(targetName) && Dualist.isWaiting(name))) {
                e.isCancelled = true
                return
            }

        }

        if (Dualist.isInDuel(name)) {
            if (!Dualist.isInDuelWith(targetName, name)) {
                e.isCancelled = true
                return
            }
        }
    }


    @EventHandler
    fun onDamagePlayer(e: EntityDamageByEntityEvent) {

        if (!config.getBoolean("damageFromOtherPlayers")) {
            val sender = e.damager
            val receiver = e.entity

            if (receiver is Player) {
                if (sender is Player) {
                    cancellEvent(sender, receiver, e)
                }

                if (sender is Arrow) {
                    val shooter = sender.shooter
                    if (shooter is Player) {
                        cancellEvent(shooter, receiver, e)
                    }
                }

                if (sender is ThrownPotion) {
                    val shooter = sender.shooter
                    if (shooter is Player) {
                        cancellEvent(shooter, receiver, e)
                    }
                }
            }
        }

    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val entity = e.entity
        if (entity is Player && entity.killer is Player) {
            val name = entity.uniqueId
            if (Dualist.isInDuel(entity.uniqueId)) {

                if (Dualist.isWaiting(name)) {
                    Bukkit.getScheduler().cancelTask(Dualist.countdown[name]!!)
                    Bukkit.getScheduler().cancelTask(Dualist.countdown[Dualist.getAnotherPlayer(name)!!]!!)
                }

                val bet = Dualist.getBet(name)
                Dualist.deathScreen[name] = bet

                if (Dualist.isInitiator(name)) {
                    val player = entity.killer
                    player.sendTitle(Messages["playerDeath"].mcformat(entity.name),Messages["playerSub"].mcformat(bet), 5, 30, 5)
                    Dualist.econ!!.withdrawPlayer(entity, bet)
                    Dualist.econ!!.depositPlayer(player, bet)
                    Dualist.duelList.remove(name)

                } else if (Dualist.isParticipant(name)) {
                    Dualist.duelList.keys.forEach lit@{
                        if (Dualist.duelList[it] == name) {
                            val player = Bukkit.getPlayer(it)
                            player.sendTitle(Messages["playerDeath"].mcformat(entity.name),Messages["playerSub"].mcformat(bet), 5, 30, 5)
                            Dualist.econ!!.withdrawPlayer(entity, bet)
                            Dualist.econ!!.depositPlayer(player, bet)
                            Dualist.duelList.remove(it, name)
                            return@lit
                        }
                    }
                }
                e.keepInventory = config.getBoolean("deathKeepInventory")
                e.keepLevel = config.getBoolean("deathKeepLevel")
            }
        }
    }

    @EventHandler
    fun onPlayerSpawn(e: PlayerRespawnEvent) {
        val player = e.player
        if (Dualist.deathScreen.contains(player.uniqueId)) {
            player.sendTitle(Messages["lose"], Messages["loseSub"].mcformat(Dualist.deathScreen[player.uniqueId]!!), 5, 30, 5)
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        if (Dualist.deathScreen.contains(player.uniqueId)) {
            player.sendTitle(Messages["lose"], Messages["loseSub"].mcformat(Dualist.deathScreen[player.uniqueId]!!), 5, 30, 5)
        }
    }


    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val name = e.player.uniqueId
        if (Dualist.isInDuel(name)) {

            if (Dualist.isWaiting(name)) {
                Bukkit.getScheduler().cancelTask(Dualist.countdown[name]!!)
                Bukkit.getScheduler().cancelTask(Dualist.countdown[Dualist.getAnotherPlayer(name)!!]!!)
            }

            val bet = Dualist.getBet(name)
            Dualist.deathScreen[name] = bet
            if (Dualist.isInitiator(name)) {
                val player = Bukkit.getPlayer(Dualist.duelList[name]!!)
                player.sendTitle(Messages["playerQuit"],Messages["playerSub"].mcformat(bet), 5, 30, 5)
                logger.log(Dualist.getBet(name).toString())
                Dualist.econ!!.withdrawPlayer(e.player, bet)
                Dualist.econ!!.depositPlayer(player, bet )

                Dualist.duelList.remove(name)

            } else if (Dualist.isParticipant(name)) {
                Dualist.duelList.keys.forEach lit@{
                    if (Dualist.duelList[it] == name) {
                        val player = Bukkit.getPlayer(it)
                        player.sendTitle(Messages["playerQuit"],Messages["playerSub"].mcformat(bet), 5, 30, 5)

                        Dualist.econ!!.withdrawPlayer(e.player, bet)
                        Dualist.econ!!.depositPlayer(player, bet)

                        Dualist.duelList.remove(it, name)
                        return@lit
                    }
                }
            }
        }
    }

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        val name = e.player.uniqueId
        if (Dualist.isInDuel(name)) {
            val another = Dualist.getAnotherPlayer(name)!!
            val player = Bukkit.getPlayer(name)
            val anotherPlayer = Bukkit.getPlayer(another)

            if (player.isDead || anotherPlayer.isDead)
                return

            val maxDistance = config.getInt("maxDistance")
            if ((player.world.uid != anotherPlayer.world.uid) || (maxDistance > 0 && e.player.location.distance(anotherPlayer.location).toInt() > maxDistance)) {
                if (Dualist.isWaiting(name)) {
                    Bukkit.getScheduler().cancelTask(Dualist.countdown[name]!!)
                    Bukkit.getScheduler().cancelTask(Dualist.countdown[another]!!)

                }

                if (Dualist.isInitiator(name)) {
                    Dualist.duelList.remove(name)
                } else if (Dualist.isParticipant(name)) {
                    Dualist.duelList.remove(another)
                }

                e.player.sendTitle(Messages["draw"], Messages["drawSub"], 5, 30, 5)
                anotherPlayer.sendTitle(Messages["draw"], Messages["drawSub"], 5, 30, 5)
            }
        }
    }

    @EventHandler
    fun onTeleport(e: PlayerTeleportEvent) {
        if (config.getBoolean("preventTeleportFromPlugins")) {
            if (Dualist.isInDuel(e.player.uniqueId) && restrictedCauses.contains(e.cause)) {
                e.player.sendMessage(Messages["preventTeleport"])
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        if (config.getBoolean("preventUsingCommands")) {
            if (Dualist.isInDuel(e.player.uniqueId)) {
                e.player.sendMessage(Messages["preventCommands"])
                e.isCancelled = true;
            }
        }
    }

}