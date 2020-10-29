package net.atlantis.artillery.model

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

abstract class Artillery {
    abstract fun create(location: Location, plugin: JavaPlugin)
    abstract fun onClick(player: Player, entity: Entity, plugin: JavaPlugin)
}