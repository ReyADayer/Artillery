package net.atlantis.artillery.model.item

import getNbt
import net.atlantis.artillery.metadata.ArtilleryNbtKey
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Remover : Item() {
    override val name = "砲台除去"
    override val description = "右クリックで砲台を除去"
    override val itemStack = ItemStack(Material.IRON_SHOVEL)

    override fun onClicked(player: Player, itemStack: ItemStack, clickedEntity: Entity, plugin: JavaPlugin) {
        if (clickedEntity is ArmorStand) {
            if (clickedEntity.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsArtillery, 0) == 1.toByte()) {
                removeArtillery(clickedEntity, plugin)
            } else if (clickedEntity.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.IsPart, 0) == 1.toByte()) {
                val uniqueId = UUID.fromString(clickedEntity.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.ArtilleryUniqueId))
                val artilleryEntity = Bukkit.getServer().getEntity(uniqueId)
                artilleryEntity?.let {
                    removeArtillery(it, plugin)
                }
            }
        }
    }

    private fun removeArtillery(artilleryEntity: Entity, plugin: JavaPlugin) {
        val entities = artilleryEntity.world.getNearbyEntities(artilleryEntity.location, 3.0, 3.0, 3.0)
        val uniqueId = artilleryEntity.uniqueId.toString()
        entities.forEach {
            if (it.persistentDataContainer.getNbt(plugin, ArtilleryNbtKey.ArtilleryUniqueId) == uniqueId) {
                it.remove()
            }
        }
        artilleryEntity.remove()
    }
}