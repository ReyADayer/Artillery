package net.atlantis.artillery.model.artillery

import net.atlantis.artillery.model.skill.ArrowLaunch
import net.atlantis.artillery.util.EntityUtil
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ArrowArtillery : Artillery() {
    companion object {
        const val CANNON_1 = "cannon_1"
        const val CANNON_2 = "cannon_2"
        const val CANNON_3 = "cannon_3"
        const val BASE_1 = "base_1"
        const val BASE_2 = "base_2"
        const val BASE_3 = "base_3"
        const val BASE_4 = "base_4"
        const val BASE_5 = "base_5"
        const val BASE_6 = "base_6"
        const val BASE_7 = "base_7"
        const val BASE_8 = "base_8"
        const val BASE_9 = "base_9"
        const val HINGE_1 = "hinge_1"
        const val HINGE_2 = "hinge_2"
        const val TEXT = "text"

        fun cannon1Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            if (vector.y < 0.0) {
                vector.y = 0.0
            }
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 0.6
            }
        }

        fun cannon2Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            if (vector.y < 0.0) {
                vector.y = 0.0
            }
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(1.5)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 0.6
            }
        }

        fun cannon3Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            if (vector.y < 0.0) {
                vector.y = 0.0
            }
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(2)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 0.6
            }
        }

        fun base1Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw += 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.0
            }
        }

        fun base2Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw -= 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.0
            }
        }

        fun base3Location(entity: Entity, passenger: LivingEntity): Location {
            return entity.location.clone().apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.2
            }
        }

        fun base4Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw += 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(0.5)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun base5Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw -= 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(0.5)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun base6Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(0.5)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun base7Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector.multiply(-0.5)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun base8Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw += 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun base9Location(entity: Entity, passenger: LivingEntity): Location {
            val location = passenger.eyeLocation.clone().apply {
                yaw -= 90f
            }
            var vector = location.direction
            vector.y = 0.0
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.5
            }
        }

        fun hinge1Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            if (vector.y < 0.0) {
                vector.y = 0.0
            }
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).add(vector.multiply(0.3).rotateAroundY(90.0)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.0
            }
        }

        fun hinge2Location(entity: Entity, passenger: LivingEntity): Location {
            var vector = passenger.eyeLocation.direction
            if (vector.y < 0.0) {
                vector.y = 0.0
            }
            val length = vector.length()
            if (length < 1.0) {
                vector = vector.multiply(1.0 / length)
            }
            return entity.location.clone().add(vector).add(vector.multiply(0.3).rotateAroundY(-90.0)).apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.0
            }
        }

        fun textLocation(entity: Entity, passenger: LivingEntity): Location {
            return entity.location.clone().apply {
                yaw = passenger.eyeLocation.yaw
                y -= 1.2
            }
        }
    }

    override val name = "バリスタ"

    override fun onCreate(armorStand: ArmorStand, plugin: JavaPlugin) {
        setArmorStands(armorStand, plugin)
    }

    private fun setArmorStands(entity: ArmorStand, plugin: JavaPlugin) {
        createCannonPartArmorStand(cannon1Location(entity, entity), entity, CANNON_1, plugin) {
            it.setHelmet(ItemStack(Material.OAK_PLANKS))
        }
        createCannonPartArmorStand(cannon2Location(entity, entity), entity, CANNON_2, plugin) {
            it.setHelmet(ItemStack(Material.OAK_PLANKS))
        }
        createCannonPartArmorStand(cannon3Location(entity, entity), entity, CANNON_3, plugin) {
            it.setHelmet(ItemStack(Material.OAK_PLANKS))
        }
        createCannonPartArmorStand(base1Location(entity, entity), entity, BASE_1, plugin) {
            it.setHelmet(ItemStack(Material.CHISELED_STONE_BRICKS))
        }
        createCannonPartArmorStand(base2Location(entity, entity), entity, BASE_2, plugin) {
            it.setHelmet(ItemStack(Material.CHISELED_STONE_BRICKS))
        }
        createCannonPartArmorStand(base3Location(entity, entity), entity, BASE_3, plugin) {
            it.setHelmet(ItemStack(Material.STONE_BRICKS))
        }
        createCannonPartArmorStand(base4Location(entity, entity), entity, BASE_4, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(base5Location(entity, entity), entity, BASE_5, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(base6Location(entity, entity), entity, BASE_6, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(base7Location(entity, entity), entity, BASE_7, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(base8Location(entity, entity), entity, BASE_8, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(base9Location(entity, entity), entity, BASE_9, plugin) {
            it.setHelmet(ItemStack(Material.IRON_BLOCK))
        }
        createCannonPartArmorStand(hinge1Location(entity, entity), entity, HINGE_1, plugin) {
            it.setHelmet(ItemStack(Material.COBBLESTONE_WALL))
        }
        createCannonPartArmorStand(hinge2Location(entity, entity), entity, HINGE_2, plugin) {
            it.setHelmet(ItemStack(Material.COBBLESTONE_WALL))
        }
        createCannonPartArmorStand(textLocation(entity, entity), entity, TEXT, plugin) {
            it.isCustomNameVisible = true
            it.customName = "${ChatColor.YELLOW}右クリックでバリスタを使う"
        }
    }

    override fun onRide(player: Player, entity: Entity, plugin: JavaPlugin) {
    }

    override fun onFire(player: Player, entity: Entity, plugin: JavaPlugin) {
        ArrowLaunch(player, entity, plugin).execute()
    }

    override fun setLocation(entity: Entity, passenger: LivingEntity, plugin: JavaPlugin) {
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.CANNON_1, plugin)?.teleport(BasicArtillery.cannon1Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.CANNON_2, plugin)?.teleport(BasicArtillery.cannon2Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.CANNON_3, plugin)?.teleport(BasicArtillery.cannon3Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_1, plugin)?.teleport(BasicArtillery.base1Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_2, plugin)?.teleport(BasicArtillery.base2Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_3, plugin)?.teleport(BasicArtillery.base3Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_4, plugin)?.teleport(BasicArtillery.base4Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_5, plugin)?.teleport(BasicArtillery.base5Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_6, plugin)?.teleport(BasicArtillery.base6Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_7, plugin)?.teleport(BasicArtillery.base7Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_8, plugin)?.teleport(BasicArtillery.base8Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.BASE_9, plugin)?.teleport(BasicArtillery.base9Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.HINGE_1, plugin)?.teleport(BasicArtillery.hinge1Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.HINGE_2, plugin)?.teleport(BasicArtillery.hinge2Location(entity, passenger))
        EntityUtil.getEntityFromNbt(entity, BasicArtillery.TEXT, plugin)?.teleport(BasicArtillery.textLocation(entity, passenger))
    }
}