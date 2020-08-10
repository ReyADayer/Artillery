package net.atlantis.artillery.metadata

import net.atlantis.artillery.ext.getBooleanMetadata
import net.atlantis.artillery.ext.setBooleanMetadata
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class PlayerFlagMetadata(val plugin: JavaPlugin) {
    private val key = "PlayerInteractEvent"

    fun avoidTwice(player: Player) {
        setFlag(player, true)
        object : BukkitRunnable() {
            override fun run() {
                setFlag(player, false)
            }
        }.runTaskLater(plugin, 10)
    }

    fun setFlag(player: Player, flag: Boolean) {
        player.setBooleanMetadata(plugin, key, flag)
    }

    fun getFlag(player: Player): Boolean {
        return player.getBooleanMetadata(key)
    }
}