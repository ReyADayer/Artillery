package net.atlantis.artillery.model.item

import net.atlantis.artillery.metadata.BasicNbtKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import setNbt

abstract class Item {
    abstract val name: String
    abstract val description: String
    abstract val itemStack: ItemStack

    abstract fun onClicked(player: Player, itemStack: ItemStack, clickedEntity: Entity, plugin: JavaPlugin)

    fun toItemStack(plugin: JavaPlugin): ItemStack {
        val resultItemStack = itemStack
        val itemMeta = resultItemStack.itemMeta
        itemMeta?.setDisplayName(name)
        itemMeta?.lore = listOf(description)
        itemMeta?.persistentDataContainer?.setNbt(plugin, BasicNbtKey.Name, this::class.simpleName)
        resultItemStack.itemMeta = itemMeta
        return resultItemStack
    }

    fun toItemStack(amount: Int, plugin: JavaPlugin): ItemStack {
        val itemStack = toItemStack(plugin)
        itemStack.amount = amount
        return itemStack
    }
}