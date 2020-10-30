package net.atlantis.artillery.util

import net.atlantis.artillery.ext.getStringMetadata
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

object EntityUtil {
    fun getEntityFromNbt(entity: Entity, tag: String, plugin: JavaPlugin): Entity? {
        val string = entity.persistentDataContainer.get(NamespacedKey(plugin, tag), PersistentDataType.STRING)
        val uuid = UUID.fromString(string)
        return Bukkit.getServer().getEntity(uuid)
    }

    fun getEntityFromMeta(entity: Entity, tag: String): Entity? {
        val string = entity.getStringMetadata(tag)
        val uuid = UUID.fromString(string)
        return Bukkit.getServer().getEntity(uuid)
    }
}