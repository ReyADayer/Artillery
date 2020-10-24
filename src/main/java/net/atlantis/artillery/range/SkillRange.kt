package net.atlantis.artillery.range

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity

abstract class SkillRange {
    abstract fun getEntities(location: Location): List<Entity>

    abstract fun getBlocks(location: Location): List<Block>

    abstract fun randomLocation(baseLocation: Location): Location
}