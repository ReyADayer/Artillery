package net.atlantis.artillery.listener

import getNbt
import net.atlantis.artillery.ext.getBooleanMetadata
import net.atlantis.artillery.metadata.ArtilleryNbtKey
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.metadata.PlayerFlagMetadata
import net.atlantis.artillery.model.artillery.ArtilleryService
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.spigotmc.event.entity.EntityDismountEvent

class PlayerListener(private val plugin: JavaPlugin) : Listener {

    private val playerFlagMetadata = PlayerFlagMetadata(plugin)

    @EventHandler
    fun onShoot(event: PlayerInteractEvent) {
        val player = event.player
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        if (event.action == Action.LEFT_CLICK_AIR && player.getBooleanMetadata(MetadataKey.IS_RIDING.key)) {
            playerFlagMetadata.avoidTwice(player)
            ArtilleryService.fire(player, plugin)
            event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onClickArtillery(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        if (playerFlagMetadata.getFlag(player)) {
            return
        }
        val entity = event.rightClicked
        if (entity is ArmorStand) {
            if (entity.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsArtillery, 0) == 1.toByte()) {
                playerFlagMetadata.avoidTwice(player)
                ArtilleryService.ride(player, entity, plugin)
                event.isCancelled = true
            } else if (entity.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsPart, 0) == 1.toByte()) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onRideOf(event: EntityDismountEvent) {
        if (event.entity is Player) {
            val player = event.entity
            val dismounted = event.dismounted
            if (dismounted is ArmorStand) {
                if (player.hasMetadata(MetadataKey.IS_RIDING.key)) {
                    player.removeMetadata(MetadataKey.IS_RIDING.key, plugin)
                }
            }
        }
    }
}