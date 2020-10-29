package net.atlantis.artillery.model.skill

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

abstract class Skill(private val player: Player, private val plugin: JavaPlugin) {
    fun execute() {
        object : BukkitRunnable() {
            override fun run() {
                start()
            }
        }.runTaskLater(plugin, 1)
    }

    abstract fun start()
}