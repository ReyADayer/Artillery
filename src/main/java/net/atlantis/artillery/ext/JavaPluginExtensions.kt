package net.atlantis.artillery.ext

import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

fun JavaPlugin.initCommand(command: String, executor: CommandExecutor) {
    getCommand(command)?.setExecutor(executor)
}

fun JavaPlugin.registerListener(listener: Listener) {
    server.pluginManager.registerEvents(listener, this)
}

fun JavaPlugin.runTaskTimerAsynchronously(periodSecond: Double,
                                          completeCondition: (data: Long) -> Boolean,
                                          function: (data: Long) -> Unit,
                                          onComplete: (data: Long) -> Unit) {
    object : BukkitRunnable() {
        var data: Long = 0L
        override fun run() {
            if (completeCondition.invoke(data)) {
                this@runTaskTimerAsynchronously.server.scheduler.cancelTask(taskId)
                onComplete.invoke(data)
            }
            function.invoke(data)
            data += 1
        }
    }.runTaskTimerAsynchronously(this, 0, (periodSecond * 20.0).toLong())
}