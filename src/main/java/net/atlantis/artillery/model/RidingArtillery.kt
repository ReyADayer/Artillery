package net.atlantis.artillery.model

import io.reactivex.Observable
import net.atlantis.artillery.ext.setBooleanMetadata
import net.atlantis.artillery.ext.setEntityMetadata
import net.atlantis.artillery.metadata.MetadataKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit

class RidingArtillery(val artillery: Entity, val second: Double, val plugin: JavaPlugin) {

    private fun effect(player: Player, currentSecond: Int) {
        object :BukkitRunnable(){
            override fun run() {
                ArtilleryEntity.setLocation(artillery, player)
            }
        }.runTaskLater(plugin, 1)
    }

    private fun start(player: Player) {
        object :BukkitRunnable(){
            override fun run() {
                player.setEntityMetadata(plugin, MetadataKey.ARTILLERY_ENTITY.key, artillery)
                player.setBooleanMetadata(plugin, MetadataKey.IS_RIDING.key, true)
                artillery.addPassenger(player)
                ArtilleryEntity.setLocation(artillery, player)
            }
        }.runTaskLater(plugin, 1)
    }

    private fun end(player: Player) {
        object :BukkitRunnable(){
            override fun run() {
                player.removeMetadata(MetadataKey.ARTILLERY_ENTITY.key, plugin)
                artillery.removePassenger(player)
            }
        }.runTaskLater(plugin, 1)
    }

    fun set(player: Player) {
        val effectTime = (second * 10.0).toInt().toLong()
        start(player)
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(effectTime)
                .doOnNext {
                    if (!player.hasMetadata(MetadataKey.IS_RIDING.key)) {
                        throw RideResetException()
                    }
                    if (player.isDead) {
                        throw RideResetException()
                    }
                    effect(player, (it / 10L).toInt())
                }
                .doOnComplete {
                    end(player)
                }
                .doOnError {
                    end(player)
                }
                .subscribe()
    }

    class RideResetException : Exception()
}