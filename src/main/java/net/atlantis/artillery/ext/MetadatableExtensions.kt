package net.atlantis.artillery.ext

import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.plugin.java.JavaPlugin

fun Metadatable.getBooleanMetadata(key: String): Boolean {
    return try {
        getMetadata(key)[0].value() as Boolean
    } catch (e: IndexOutOfBoundsException) {
        false
    }
}

fun Metadatable.setBooleanMetadata(plugin: JavaPlugin, key: String, value: Boolean) {
    setMetadata(key, FixedMetadataValue(plugin, value))
}

fun Metadatable.getIntMetadata(key: String): Int? {
    return try {
        getMetadata(key)[0].value() as Int
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}

fun Metadatable.setIntMetadata(plugin: JavaPlugin, key: String, value: Int) {
    setMetadata(key, FixedMetadataValue(plugin, value))
}

fun Metadatable.getStringMetadata(key: String): String? {
    return try {
        getMetadata(key)[0].value() as String
    } catch (e: IndexOutOfBoundsException) {
        null
    }
}

fun Metadatable.setStringMetadata(plugin: JavaPlugin, key: String, value: String) {
    setMetadata(key, FixedMetadataValue(plugin, value))
}