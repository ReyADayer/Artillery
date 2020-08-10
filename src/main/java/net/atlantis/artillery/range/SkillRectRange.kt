package net.atlantis.artillery.range

import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.*

class SkillRectRange(private val x: Double, private val y: Double, private val z: Double) : SkillRange() {
    override fun getEntities(location: Location): List<Entity> {
        return location.world?.getNearbyEntities(location, x, y, z)?.toList() ?: emptyList()
    }

    override fun randomLocation(baseLocation: Location): Location {
        val i = (Random().nextDouble() * 2 - 1) * x
        val j = (Random().nextDouble() * 2 - 1) * y
        val k = (Random().nextDouble() * 2 - 1) * z
        return baseLocation.clone().add(i, j, k)
    }
}