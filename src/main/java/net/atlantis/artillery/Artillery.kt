package net.atlantis.artillery

import net.atlantis.artillery.command.ArtilleryCommand
import net.atlantis.artillery.ext.initCommand
import net.atlantis.artillery.ext.registerListener
import net.atlantis.artillery.listener.PlayerListener
import org.bukkit.plugin.java.JavaPlugin

class Artillery : JavaPlugin() {
    override fun onEnable() {
        registerListener(PlayerListener(this))

        initCommand("artillery", ArtilleryCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}