package net.atlantis.artillery.model.artillery

import net.atlantis.artillery.ext.getEntityMetadata
import net.atlantis.artillery.model.skill.EggLaunch
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class EggArtilleryEntity : Artillery() {
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

    override val name = "卵砲台"

    override fun onCreate(armorStand: ArmorStand, plugin: JavaPlugin) {
        setArmorStands(armorStand, plugin)
    }

    private fun setArmorStands(entity: ArmorStand, plugin: JavaPlugin) {
        createCannonPartArmorStand(cannon1Location(entity, entity), entity, CANNON_1, plugin) {
            it.setHelmet(ItemStack(Material.HAY_BLOCK))
        }
        createCannonPartArmorStand(cannon2Location(entity, entity), entity, CANNON_2, plugin) {
            it.setHelmet(ItemStack(Material.HAY_BLOCK))
        }
        createCannonPartArmorStand(cannon3Location(entity, entity), entity, CANNON_3, plugin) {
            it.setHelmet(ItemStack(Material.HAY_BLOCK))
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
            it.customName = "${ChatColor.YELLOW}右クリックで砲台を使う"
        }
    }

    override fun onRide(player: Player, entity: Entity, plugin: JavaPlugin) {
    }

    override fun onFire(player: Player, entity: Entity, plugin: JavaPlugin) {
        EggLaunch(player, plugin).execute()
    }

    override fun setLocation(entity: Entity, passenger: LivingEntity) {
        entity.getEntityMetadata(CANNON_1)?.teleport(cannon1Location(entity, passenger))
        entity.getEntityMetadata(CANNON_2)?.teleport(cannon2Location(entity, passenger))
        entity.getEntityMetadata(CANNON_3)?.teleport(cannon3Location(entity, passenger))
        entity.getEntityMetadata(BASE_1)?.teleport(base1Location(entity, passenger))
        entity.getEntityMetadata(BASE_2)?.teleport(base2Location(entity, passenger))
        entity.getEntityMetadata(BASE_3)?.teleport(base3Location(entity, passenger))
        entity.getEntityMetadata(BASE_4)?.teleport(base4Location(entity, passenger))
        entity.getEntityMetadata(BASE_5)?.teleport(base5Location(entity, passenger))
        entity.getEntityMetadata(BASE_6)?.teleport(base6Location(entity, passenger))
        entity.getEntityMetadata(BASE_7)?.teleport(base7Location(entity, passenger))
        entity.getEntityMetadata(BASE_8)?.teleport(base8Location(entity, passenger))
        entity.getEntityMetadata(BASE_9)?.teleport(base9Location(entity, passenger))
        entity.getEntityMetadata(HINGE_1)?.teleport(hinge1Location(entity, passenger))
        entity.getEntityMetadata(HINGE_2)?.teleport(hinge2Location(entity, passenger))
        entity.getEntityMetadata(TEXT)?.teleport(textLocation(entity, passenger))
    }
}