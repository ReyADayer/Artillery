package net.atlantis.artillery.command

import net.atlantis.artillery.model.artillery.ArtilleryService
import net.atlantis.artillery.model.artillery.ArtilleryType
import net.atlantis.artillery.model.item.ItemType
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ArtilleryCommand(private val plugin: JavaPlugin) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        return when (Action.find(args[0])) {
            Action.SET -> {
                args[1]?.let {
                    ArtilleryService.create(it, player.location, plugin)
                }
                true
            }
            Action.REMOVE -> {
                when (args[1]) {
                    "all" -> {
                        ArtilleryService.removeAll(player, plugin)
                        true
                    }
                    else -> false
                }
            }
            Action.ITEM -> {
                args[1]?.let {
                    val itemStack = ItemType.findByKey(it)?.getItem()?.toItemStack(plugin) ?: return true
                    player.inventory.addItem(itemStack)
                }
                true
            }
            else -> false
        }
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        sender.sendMessage("You must be a player!")
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        when (args.size) {
            1 -> {
                val keys = Action.values().map { it.value }
                return keys.filter { it.startsWith(args[0]) }
            }
            2 -> {
                return when (Action.find(args[0])) {
                    Action.SET -> {
                        val keys = ArtilleryType.values().map { it.key }
                        keys.filter { it.startsWith(args[1]) }
                    }
                    Action.REMOVE -> {
                        val keys = listOf("all")
                        keys.filter { it.startsWith(args[1]) }
                    }
                    Action.ITEM -> {
                        val keys = ItemType.values().map { it.key }
                        keys.filter { it.startsWith(args[1]) }
                    }
                    else -> {
                        emptyList()
                    }
                }
            }
            else -> {
                return emptyList()
            }
        }
    }

    private enum class Action(val value: String) {
        ITEM("item"),
        SET("set"),
        REMOVE("remove");

        companion object {
            fun find(value: String?): Action? {
                return values().firstOrNull { it.value == value }
            }
        }
    }
}