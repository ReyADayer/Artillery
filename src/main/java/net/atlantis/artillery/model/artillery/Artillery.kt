package net.atlantis.artillery.model.artillery

import net.atlantis.artillery.ext.setEntityMetadata
import net.atlantis.artillery.ext.spawn
import net.atlantis.artillery.metadata.ArtilleryNbtKey
import net.atlantis.artillery.metadata.BasicNbtKey
import net.atlantis.artillery.model.RidingArtillery
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import setNbt

abstract class Artillery {
    abstract val name: String

    fun create(location: Location, plugin: JavaPlugin) {
        val armorStand = location.spawn<ArmorStand> {
            it.customName = name
            it.isCustomNameVisible = true
            it.setAI(false)
            it.isVisible = false
            it.isSmall = true
            it.persistentDataContainer.setNbt(plugin, BasicNbtKey.Name, this::class.simpleName)
            it.persistentDataContainer.setNbt(plugin, ArtilleryNbtKey.IsArtillery, 1)
        } as ArmorStand
        onCreate(armorStand, plugin)
    }

    fun ride(player: Player, entity: Entity, plugin: JavaPlugin) {
        if (entity.passengers.isNotEmpty()) {
            return
        }
        RidingArtillery(this, entity, 300.0, plugin).set(player)
        onRide(player, entity, plugin)
    }

    fun fire(player: Player, entity: Entity, plugin: JavaPlugin) {
        onFire(player, entity, plugin)
    }

    abstract fun onCreate(armorStand: ArmorStand, plugin: JavaPlugin)
    abstract fun onRide(player: Player, entity: Entity, plugin: JavaPlugin)
    abstract fun onFire(player: Player, entity: Entity, plugin: JavaPlugin)

    abstract fun setLocation(entity: Entity, passenger: LivingEntity)

    protected fun createCannonPartArmorStand(
            location: Location,
            entity: Entity,
            tag: String,
            plugin: JavaPlugin,
            onCreate: (armorStand: ArmorStand) -> Unit
    ) {
        location.spawn<ArmorStand> {
            onCreate.invoke(it)
            it.setGravity(false)
            it.isVisible = false
            entity.setEntityMetadata(plugin, tag, it)
            it.persistentDataContainer.setNbt(plugin, BasicNbtKey.Name, "CannonPart")
            it.persistentDataContainer.setNbt(plugin, ArtilleryNbtKey.IsPart, 1)
        }
    }
}