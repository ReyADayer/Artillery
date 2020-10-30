package net.atlantis.artillery.model.skill

import net.atlantis.artillery.ext.getEntityMetadata
import net.atlantis.artillery.ext.getIntMetadata
import net.atlantis.artillery.ext.playSound
import net.atlantis.artillery.ext.setIntMetadata
import net.atlantis.artillery.ext.spawn
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.model.artillery.EggArtilleryEntity
import net.atlantis.artillery.range.SkillRectRange
import net.atlantis.artillery.util.LocationUtil
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Container
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Egg
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class EggLaunch(private val player: Player, private val plugin: JavaPlugin) : Skill(player, plugin) {
    override fun start() {
        val cannonEntity = player.getEntityMetadata(MetadataKey.ARTILLERY_ENTITY.key) ?: return
        val taskId = cannonEntity.getIntMetadata(MetadataKey.TASK_ID.key)
        if (taskId == null) {
            run(cannonEntity)
        } else {
            plugin.server.scheduler.cancelTask(taskId)
            cannonEntity.removeMetadata(MetadataKey.TASK_ID.key, plugin)
        }
    }

    private fun run(cannonEntity: Entity) {
        val task = object : BukkitRunnable() {
            override fun run() {
                val cannonPart = cannonEntity.getEntityMetadata(EggArtilleryEntity.CANNON_3) as ArmorStand? ?: return
                val cannonPart2 = cannonEntity.getEntityMetadata(EggArtilleryEntity.CANNON_2) as ArmorStand? ?: return
                val vector = cannonPart.location.subtract(cannonPart2.location).toVector().normalize()
                if (!consume()) {
                    cannonEntity.getIntMetadata(MetadataKey.TASK_ID.key)?.let {
                        plugin.server.scheduler.cancelTask(it)
                        cannonEntity.removeMetadata(MetadataKey.TASK_ID.key, plugin)
                    }
                    return
                }

                val initialLocation = cannonPart.eyeLocation
                initialLocation.add(vector.x * 1.0, vector.y * 1.0, vector.z * 1.0)
                val x = 0.0
                val y = 0.0
                val z = 0.0
                val currentLocation = initialLocation.let { LocationUtil.transform(it, x, y, z) }

                currentLocation.playSound(Sound.ENTITY_EGG_THROW, 1.0f, 1.3f)
                currentLocation.spawn<Egg> {
                    it.velocity = vector
                }
            }
        }.runTaskTimer(plugin, 0, 40)
        val taskId = task.taskId
        cannonEntity.setIntMetadata(plugin, MetadataKey.TASK_ID.key, taskId)
    }

    private fun consume(): Boolean {
        val blocks = SkillRectRange(4.0, 2.0, 4.0).getBlocks(player.location)
        blocks.forEach { block ->
            val state = block.state
            if (state is Container) {
                state.inventory.contents.filterNotNull().forEach {
                    if (it.type == Material.EGG) {
                        it.amount -= 1
                        return true
                    }
                }
            }
        }
        return false
    }
}