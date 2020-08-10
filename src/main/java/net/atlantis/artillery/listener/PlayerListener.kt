package net.atlantis.artillery.listener

import net.atlantis.artillery.ext.getBooleanMetadata
import net.atlantis.artillery.ext.getEntityMetadata
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.metadata.PlayerFlagMetadata
import net.atlantis.artillery.model.ArtilleryEntity
import net.atlantis.artillery.model.Bombardment
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
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
        if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && player.getBooleanMetadata(MetadataKey.IS_RIDING.key)) {
            playerFlagMetadata.avoidTwice(player)
            Bombardment(player, plugin).execute()
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
            getArtilleryEntity(entity)?.let {
                playerFlagMetadata.avoidTwice(player)
                ArtilleryEntity().onClick(player, entity, plugin)
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

    private fun getArtilleryEntity(entity: Entity): Entity? {
        return when {
            entity.getBooleanMetadata(MetadataKey.IS_ARTILLERY.key) -> {
                entity
            }
            entity.getBooleanMetadata(MetadataKey.IS_PART.key) -> {
                entity.getEntityMetadata(MetadataKey.ARTILLERY_ENTITY.key)
            }
            else -> {
                null
            }
        }
    }
}