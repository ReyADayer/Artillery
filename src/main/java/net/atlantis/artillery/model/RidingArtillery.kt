package net.atlantis.artillery.model

import net.atlantis.artillery.ext.runTaskTimerAsynchronously
import net.atlantis.artillery.ext.setBooleanMetadata
import net.atlantis.artillery.ext.setStringMetadata
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.model.artillery.Artillery
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class RidingArtillery(val artillery: Artillery, val entity: Entity, val second: Double, val plugin: JavaPlugin) {

    private fun effect(player: Player, currentSecond: Int) {
        object : BukkitRunnable() {
            override fun run() {
                artillery.setLocation(entity, player, plugin)
            }
        }.runTaskLater(plugin, 1)
    }

    private fun end(player: Player) {
        object : BukkitRunnable() {
            override fun run() {
                player.removeMetadata(MetadataKey.ARTILLERY_ENTITY.key, plugin)
                entity.removePassenger(player)
            }
        }.runTaskLater(plugin, 1)
    }

    fun set(player: Player) {
        player.setStringMetadata(plugin, MetadataKey.ARTILLERY_ENTITY.key, entity.uniqueId.toString())
        player.setBooleanMetadata(plugin, MetadataKey.IS_RIDING.key, true)
        entity.addPassenger(player)
        artillery.setLocation(entity, player, plugin)
        plugin.runTaskTimerAsynchronously(0.1, {
            !player.hasMetadata(MetadataKey.IS_RIDING.key) || player.isDead
        }, {
            effect(player, (it / 10L).toInt())
        }, {
            end(player)
        })
    }
}