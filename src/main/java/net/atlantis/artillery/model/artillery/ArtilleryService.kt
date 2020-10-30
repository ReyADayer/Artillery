package net.atlantis.artillery.model.artillery

import getNbt
import net.atlantis.artillery.metadata.ArtilleryNbtKey
import net.atlantis.artillery.metadata.BasicNbtKey
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.util.EntityUtil
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

object ArtilleryService {
    fun create(name: String, location: Location, plugin: JavaPlugin) {
        getArtillery(name)?.create(location, plugin)
    }

    fun ride(player: Player, entity: Entity, plugin: JavaPlugin) {
        getArtillery(entity, plugin)?.ride(player, entity, plugin)
    }

    fun fire(player: Player, plugin: JavaPlugin) {
        val entity = EntityUtil.getEntityFromMeta(player, MetadataKey.ARTILLERY_ENTITY.key) ?: return
        getArtillery(entity, plugin)?.fire(player, entity, plugin)
    }

    fun removeAll(player: Player, plugin: JavaPlugin) {
        player.world.entities.filter {
            it.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsArtillery, 0) == 1.toByte() ||
                    it.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsPart, 0) == 1.toByte()
        }.forEach {
            it.remove()
        }
    }

    private fun getArtillery(entity: Entity, plugin: JavaPlugin): Artillery? {
        return getArtillery(entity.persistentDataContainer.getNbt(plugin, BasicNbtKey.Name))
    }

    private fun getArtillery(name: String?): Artillery? {
        return when (name) {
            ArtilleryEntity::class.simpleName -> {
                ArtilleryEntity()
            }
            EggArtilleryEntity::class.simpleName -> {
                EggArtilleryEntity()
            }
            else -> {
                null
            }
        }
    }
}