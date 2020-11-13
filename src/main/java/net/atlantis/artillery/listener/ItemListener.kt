package net.atlantis.artillery.listener

import getNbt
import net.atlantis.artillery.metadata.BasicNbtKey
import net.atlantis.artillery.model.item.ItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ItemListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onClickSpawnEgg(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val itemStack: ItemStack = player.inventory.itemInMainHand
        val name = itemStack.itemMeta?.persistentDataContainer?.getNbt(plugin, BasicNbtKey.Name)
        val item = ItemType.findByKey(name)?.getItem()
        item?.let {
            it.onClicked(player, itemStack, event.rightClicked, plugin)
            event.isCancelled = true
        }
    }
}