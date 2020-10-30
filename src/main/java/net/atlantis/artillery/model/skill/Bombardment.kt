package net.atlantis.artillery.model.skill

import net.atlantis.artillery.ext.getIntMetadata
import net.atlantis.artillery.ext.playSound
import net.atlantis.artillery.ext.random
import net.atlantis.artillery.ext.runTaskTimerAsynchronously
import net.atlantis.artillery.ext.setIntMetadata
import net.atlantis.artillery.ext.spawnParticle
import net.atlantis.artillery.metadata.MetadataKey
import net.atlantis.artillery.model.artillery.BasicArtillery
import net.atlantis.artillery.range.SkillRange
import net.atlantis.artillery.range.SkillRectRange
import net.atlantis.artillery.util.EntityUtil
import net.atlantis.artillery.util.LocationUtil
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.Container
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class Bombardment(private val player: Player, private val artilleryEntity: Entity, private val plugin: JavaPlugin) : Skill(player, plugin) {
    private val explodeEntities = mutableListOf<Entity>()

    private val range = SkillRectRange(0.5, 0.5, 0.5)

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
        val task = object : BukkitRunnable() {
            override fun run() {

                val cannonPart = EntityUtil.getEntityFromNbt(artilleryEntity, BasicArtillery.CANNON_3, plugin) as ArmorStand?
                        ?: return
                val cannonPart2 = EntityUtil.getEntityFromNbt(artilleryEntity, BasicArtillery.CANNON_2, plugin) as ArmorStand?
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

                plugin.runTaskTimerAsynchronously(0.02, {
                    currentLocation.block.type != Material.AIR || it >= 120L
                }, {
                    effect(currentLocation, range)
                    drawEffect(currentLocation, it, vector)
                }, {
                    explode(currentLocation)
                })
            }
        }.runTaskTimer(plugin, 0, 40)
        val taskId = task.taskId
        artilleryEntity.setIntMetadata(plugin, MetadataKey.TASK_ID.key, taskId)
    }

    private fun consume(): Boolean {
        val blocks = SkillRectRange(4.0, 2.0, 4.0).getBlocks(artilleryEntity.location)
        blocks.forEach { block ->
            val state = block.state
            if (state is Container) {
                state.inventory.contents.filterNotNull().forEach {
                    if (it.type == Material.GUNPOWDER) {
                        it.amount -= 1
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun effect(location: Location, range: SkillRange) {
        object : BukkitRunnable() {
            override fun run() {
                val entities = range.getEntities(location).filterNot { it is ArmorStand }
                if (entities.isNotEmpty()) {
                    entities.forEach {
                        explode(location)
                    }
                }
            }
        }.runTaskLater(plugin, 0)
    }

    /**
     * エフェクトを描く

     * @param data data
     */
    private fun drawEffect(currentLocation: Location, data: Long, vector: Vector) {
        if (data == 1.toLong()) {
            currentLocation.playSound(Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.933f)
        }
        val drawVector = vector.clone().multiply(0.4)
        currentLocation.spawnParticle(Particle.CLOUD, 1)
        currentLocation.spawnParticle(Particle.END_ROD, 3)
        if (data % 10 == 0.toLong()) {
            currentLocation.playSound(Sound.ENTITY_CREEPER_PRIMED, 3.0f, 0.933f)
        }
        effect(currentLocation, range)
        currentLocation.add(drawVector)
        currentLocation.add(0.0, -0.00004 * data * data, 0.0)
    }

    private fun explode(location: Location) {
        val currentLocation = location.clone()
        plugin.runTaskTimerAsynchronously(0.02, {
            it >= 30L
        }, {
            if (it % 5 == 0.toLong()) {
                drawExplode(currentLocation, it)
            }
        }, {
        })
    }

    private fun drawExplode(location: Location, data: Long) {
        val radius = data / 5
        if (data == 5.toLong()) {
            location.playSound(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.933f)
        }
        for (i in 0..10) {
            for (j in 0..10) {
                val angle1 = 2.0 * Math.PI * i.toDouble() / 10
                val angle2 = 2.0 * Math.PI * j.toDouble() / 10
                val x = radius * Math.sin(angle1) * Math.cos(angle2)
                val y = radius * Math.sin(angle1) * Math.sin(angle2)
                val z = radius * Math.cos(angle1)
                val currentLocation = location.clone().add(x, y, z)
                if (data % 3 == 0L) {
                    currentLocation.random(0.1, 0.1, 0.1).spawnParticle(Particle.EXPLOSION_LARGE, 1)
                }
                currentLocation.random(0.1, 0.1, 0.1).spawnParticle(Particle.FLAME, 2)
                explodeEffect(currentLocation, range)
            }
        }
    }

    private fun explodeEffect(location: Location, range: SkillRange) {
        object : BukkitRunnable() {
            override fun run() {
                val entities = range.getEntities(location)
                if (entities.isNotEmpty()) {
                    entities.forEach {
                        if (!explodeEntities.contains(it)) {
                            enemyEffect(it)
                            explodeEntities.add(it)
                        }
                    }
                }
            }
        }.runTaskLater(plugin, 0)
    }

    private fun enemyEffect(entity: Entity) {
        if (entity is LivingEntity) {
            entity.damage(17.0)
        }
    }
}