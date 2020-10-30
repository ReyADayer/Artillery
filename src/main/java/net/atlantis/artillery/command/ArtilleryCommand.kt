package net.atlantis.artillery.command

import net.atlantis.artillery.model.artillery.ArtilleryService
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ArtilleryCommand(private val plugin: JavaPlugin) : BaseCommand(plugin) {
    override fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean {
        return when (args[0]) {
            "set" -> {
                args[1]?.let {
                    ArtilleryService.create(it, player.location, plugin)
                }
                true
            }
            "remove" -> {
                when (args[1]) {
                    "all" -> {
                        ArtilleryService.removeAll(player, plugin)
                        true
                    }
                    else -> false
                }
            }
            else -> false
        }
    }

    override fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean {
        sender.sendMessage("You must be a player!")
        return false
    }
}