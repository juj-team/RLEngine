package listeners.depers

import RadioLampEngine
import items.metaphysics.AntiDepersBootsItem
import items.metaphysics.AntiDepersChestplateItem
import items.metaphysics.AntiDepersHelmetItem
import items.metaphysics.AntiDepersLeggingsItem
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import util.BlockUtils.iterateOverMaterialsInLine

object DepersUtils {
    fun standingOnDepers(entity: LivingEntity) : Boolean {
        val standingBlock = entity.location.block.getRelative(BlockFace.DOWN)
        val standingOnDepers = standingBlock.type == Material.WARPED_WART_BLOCK
        val notImmune = isNotImmune(entity)

        if (!notImmune && standingOnDepers) {
            entity.world.spawnParticle(
                Particle.SQUID_INK,
                entity.location.x,
                entity.location.y - 0.2,
                entity.location.z,
                4,
                0.3,
                0.3,
                0.3,
            )
        }

        return standingOnDepers && notImmune
    }

    private fun isInAntiDepersArmor(entity: LivingEntity) : Boolean {
        val humanEntity = entity as? HumanEntity ?: return false
        val inventory = humanEntity.inventory

        val helmet = inventory.helmet ?: return false
        if (!AntiDepersHelmetItem.compare(helmet)) return false

        val chestplate = inventory.chestplate ?: return false
        if (!AntiDepersChestplateItem.compare(chestplate)) return false

        val leggings = inventory.leggings ?: return false
        if (!AntiDepersLeggingsItem.compare(leggings)) return false

        val boots = inventory.boots ?: return false
        return AntiDepersBootsItem.compare(boots)
    }

    fun isNotImmune(entity: LivingEntity) : Boolean {
        if (entity is Player) {
            if (entity.gameMode == GameMode.CREATIVE || entity.gameMode == GameMode.SPECTATOR) {
                return false
            }
        }
        return !isInAntiDepersArmor(entity)
    }

    fun canReplaceBlock(block: Block) : Boolean {
        val tags = listOf(
            Tag.LEAVES,
            Tag.SCULK_REPLACEABLE,
            Tag.LOGS_THAT_BURN,
            Tag.CRIMSON_STEMS,
            Tag.CORAL_BLOCKS,
            Tag.WOOL,
            Tag.TERRACOTTA,
            Tag.MINEABLE_SHOVEL,
        )

        val results = tags.map { it.isTagged(block.type) }
        return block.type == Material.BEDROCK || results.any { it }
    }

    fun replaceBlock(block: Block, entity: LivingEntity) {
        if (isInsulatorBetween(entity.location, block.location)) return

        val plugin = RadioLampEngine.instance as RadioLampEngine
        val cp = plugin.coreProtectAPI

        val username = entity.name
        cp?.logRemoval(username, block.location, block.type, block.blockData)

        block.type = Material.WARPED_WART_BLOCK

        cp?.logPlacement(username, block.location, Material.WARPED_WART_BLOCK, block.blockData)
    }

    fun isInsulatorBetween(loc1: Location, loc2: Location): Boolean {
        val materials = iterateOverMaterialsInLine(loc1, loc2) ?: return false

        for (material in materials) {
            if (
                "quartz" in material.name.lowercase()
                || "glass" in material.name.lowercase()
            ) return true
        }

        return false
    }
}