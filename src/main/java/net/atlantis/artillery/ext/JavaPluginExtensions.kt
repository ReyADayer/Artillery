package net.atlantis.artillery.ext

import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.initCommand(command: String, executor: CommandExecutor) {
    getCommand(command)?.setExecutor(executor)
}

fun JavaPlugin.registerListener(listener: Listener) {
    server.pluginManager.registerEvents(listener, this)
}
