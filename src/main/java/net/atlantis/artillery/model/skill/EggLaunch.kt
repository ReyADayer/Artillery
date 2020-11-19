package net.atlantis.artillery.model.skill

import net.atlantis.artillery.ext.getIntMetadata
import net.atlantis.artillery.ext.playSound
import net.atlantis.artillery.ext.setIntMetadata
import net.atlantis.artillery.ext.spawn
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.model.artillery.EggArtillery
import net.atlantis.artillery.range.SkillRectRange
import net.atlantis.artillery.util.EntityUtil
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

class EggLaunch(private val player: Player, private val artilleryEntity: Entity, private val plugin: JavaPlugin) : Skill(player, plugin) {
    override fun start() {
        val taskId = artilleryEntity.getIntMetadata(MetadataKey.TASK_ID.key)
        if (taskId == null) {
            run()
            player.sendMessage("砲台 ON")
            player.playSound(player.location, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.0f)
        } else {
            plugin.server.scheduler.cancelTask(taskId)
            artilleryEntity.removeMetadata(MetadataKey.TASK_ID.key, plugin)
            player.sendMessage("砲台 OFF")
            player.playSound(player.location, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1.0f, 1.0f)
        }
    }

    private fun run() {
        val period = plugin.config.getLong("egg_period")
        val task = object : BukkitRunnable() {
            override fun run() {
                val cannonPart = EntityUtil.getEntityFromNbt(artilleryEntity, EggArtillery.CANNON_3, plugin) as ArmorStand?
                        ?: return
                val cannonPart2 = EntityUtil.getEntityFromNbt(artilleryEntity, EggArtillery.CANNON_2, plugin) as ArmorStand?
                        ?: return
                val vector = cannonPart.location.subtract(cannonPart2.location).toVector().normalize()
                if (!consume()) {
                    artilleryEntity.getIntMetadata(MetadataKey.TASK_ID.key)?.let {
                        plugin.server.scheduler.cancelTask(it)
                        artilleryEntity.removeMetadata(MetadataKey.TASK_ID.key, plugin)
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
        }.runTaskTimer(plugin, 0, period)
        val taskId = task.taskId
        artilleryEntity.setIntMetadata(plugin, MetadataKey.TASK_ID.key, taskId)
    }

    private fun consume(): Boolean {
        val blocks = SkillRectRange(4.0, 2.0, 4.0).getBlocks(artilleryEntity.location)
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