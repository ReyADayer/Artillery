package net.atlantis.artillery.range

import org.bukkit.Location
import org.bukkit.entity.Entity

abstract class SkillRange {
    abstract fun getEntities(location: Location): List<Entity>

    abstract fun randomLocation(baseLocation: Location): Location
}