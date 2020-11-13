package net.atlantis.artillery.metadata

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

sealed class NbtKey<T>(val persistentDataType: PersistentDataType<T, T>) {
    fun namespacedKey(plugin: JavaPlugin): NamespacedKey = NamespacedKey(plugin, this::class.simpleName!!)
}

sealed class BasicNbtKey<T>(persistentDataType: PersistentDataType<T, T>) : NbtKey<T>(persistentDataType) {
    object Name : BasicNbtKey<String>(PersistentDataType.STRING)
}

sealed class ArtilleryNbtKey<T>(persistentDataType: PersistentDataType<T, T>) : NbtKey<T>(persistentDataType) {
    object IsArtillery : ArtilleryNbtKey<Byte>(PersistentDataType.BYTE)
    object IsPart : ArtilleryNbtKey<Byte>(PersistentDataType.BYTE)
    object ArtilleryUniqueId : ArtilleryNbtKey<String>(PersistentDataType.STRING)
}