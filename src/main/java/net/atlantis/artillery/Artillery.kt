package net.atlantis.artillery

import net.atlantis.artillery.command.ArtilleryCommand
import net.atlantis.artillery.ext.initCommand
import net.atlantis.artillery.ext.registerListener
import net.atlantis.artillery.listener.ItemListener
import net.atlantis.artillery.listener.PlayerListener
import org.bukkit.plugin.java.JavaPlugin

class Artillery : JavaPlugin() {
    override fun onEnable() {
        registerListener(PlayerListener(this))
        registerListener(ItemListener(this))

        initCommand("artillery", ArtilleryCommand(this))

        saveDefaultConfig()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}