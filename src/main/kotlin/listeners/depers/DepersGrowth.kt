package listeners.depers

import items.metaphysics.AntiDepersBootsItem
import items.metaphysics.AntiDepersChestplateItem
import items.metaphysics.AntiDepersHelmetItem
import items.metaphysics.AntiDepersLeggingsItem
import listeners.depers.DepersUtils.standingOnDepers
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import util.RLEngineTaskManager


object DepersGrowth {
    init {
        RLEngineTaskManager.runTask({ depersTick() }, 1L, 5L)
    }

    private fun depersTick() {
        val worlds = Bukkit.getWorlds()
        val entities = mutableListOf<LivingEntity>()
        for (world in worlds) {
            entities += world.livingEntities
        }

        for (entity in entities) checkDepersGrowth(entity)
    }

    fun setDepersTimer(entity: LivingEntity, value: Int) {
        entity.persistentDataContainer.set(
            NamespacedKey("rle", "depers_timer"),
            PersistentDataType.INTEGER,
            value,
        )
    }

    fun getDepersTimer(entity: LivingEntity) : Int {
        val depersTimer = entity.persistentDataContainer.get(
            NamespacedKey("rle", "depers_timer"),
            PersistentDataType.INTEGER,
        )

        if (depersTimer == null) {
            setDepersTimer(entity, -1)
            return -1
        }

        return depersTimer
    }

    fun resetDepersTimer(entity: LivingEntity) {
        setDepersTimer(entity, 12)
    }

    private fun decrementDepersTimer(entity: LivingEntity) {
        val depersTimer = getDepersTimer(entity)

        if (depersTimer >= 0) setDepersTimer(entity, depersTimer - 1)
    }

    private fun proceedDepers(entity: LivingEntity) {
        val depersTimer = getDepersTimer(entity)
        val justStepped = depersTimer == -1

        if (standingOnDepers(entity) && justStepped) {
            resetDepersTimer(entity)
        }

        DepersEffectMediator.dispatch(entity)
    }

    private fun checkDepersGrowth(entity: LivingEntity) {
        if (getDepersTimer(entity) >= 0 || standingOnDepers(entity)) {
            proceedDepers(entity)
        }

        decrementDepersTimer(entity)
    }
}