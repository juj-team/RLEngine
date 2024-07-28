package listeners.depers

import listeners.depers.DepersGrowth.getDepersTimer
import listeners.depers.DepersUtils.canReplaceBlock
import listeners.depers.DepersUtils.isNotImmune
import listeners.depers.DepersUtils.replaceBlock
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import util.BlockUtils.iterateOverBlocks
import util.BlockUtils.iterateOverMaterialsInLine

object DepersEffectMediator {
    private val effects = mapOf(
        0..Int.MAX_VALUE to DepersEffectMediator::defaultEffect,
        8..10 to DepersEffectMediator::smallGrowth,
        5..8 to DepersEffectMediator::biggerGrowth,
        5..7 to DepersEffectMediator::potionEffect,
        1..2 to DepersEffectMediator::entityGrowth,
    )

    fun dispatch(entity: LivingEntity) {
        if (entity !is Player) return

        val depersTimer = getDepersTimer(entity)
        for ((range, effect) in effects) {
            if (depersTimer in range) effect(entity)
        }
    }

    private fun defaultEffect(entity: LivingEntity) {
        entity.addPotionEffect(
            PotionEffect(
                PotionEffectType.BLINDNESS,
                2 * 20,
                0,
                false,
                false,
            )
        )
    }

    private fun smallGrowth(entity: LivingEntity) {
        val blocksToGrow = iterateOverBlocks(
            entity,
            -1..1,
            -3..5,
            -1..1
        ).filter { canReplaceBlock(it) }

        for (block in blocksToGrow) replaceBlock(block, entity)
    }

    private fun biggerGrowth(entity: LivingEntity) {
        val blocksToGrow = iterateOverBlocks(
            entity,
            -2..2,
            -3..5,
            -2..2,
        ).filter { canReplaceBlock(it) }

        for (block in blocksToGrow) replaceBlock(block, entity)
    }

    private fun potionEffect(entity: LivingEntity) {
        entity.addPotionEffect(
            PotionEffect(
                PotionEffectType.HUNGER,
                10 * 20,
                2,
                false,
                false,
            )
        )
        entity.addPotionEffect(
            PotionEffect(
                PotionEffectType.WITHER,
                20,
                1,
                false,
                false,
            )
        )
    }

    private fun isInsulatorBetween(loc1: Location, loc2: Location): Boolean {
        val materials = iterateOverMaterialsInLine(loc1, loc2) ?: return false

        for (material in materials) {
            if (
                "quartz" in material.name.lowercase()
                || "glass" in material.name.lowercase()
            ) return true
        }

        return false
    }

    private fun entityGrowth(entity: LivingEntity) {
        val entitiesAround = entity.location.getNearbyLivingEntities(
            15.0,
            15.0,
            15.0,
        )

        for (entityAround in entitiesAround) {
            if (entityAround.type == EntityType.PLAYER) continue
            if (entityAround == entity) continue
            if (!isNotImmune(entityAround)) continue
            if (isInsulatorBetween(entity.location, entityAround.location)) continue

            val blocksAround = iterateOverBlocks(
                entityAround,
                0..0,
                0..4,
                0..0,
            )

            val blocksToGrow = sequence {
                yieldAll(blocksAround)

                val targetBlock = entityAround.getTargetBlock(null, 1)
                yield(targetBlock)
            }

            for (block in blocksToGrow) replaceBlock(block, entity)
        }
    }
}