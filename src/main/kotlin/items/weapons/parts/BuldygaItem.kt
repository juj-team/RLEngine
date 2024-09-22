package items.weapons.parts

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Vex
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

object BuldygaItem : WeaponPart {
    override val id: String = "weapon_part_buldyga"
    override val model = 44435
    override val name = Component.text("Булдыга").decoration(TextDecoration.ITALIC, false)

    private val ownerKey = NamespacedKey("rle", "owner")
    private val targetKey = NamespacedKey("rle", "target")

    @EventHandler
    private fun onTarget(event: EntityTargetLivingEntityEvent) {
        if (event.entity.type != EntityType.VEX) return

        val vex = event.entity

        if (!vex.persistentDataContainer.has(ownerKey)) return

        val ownerId = UUID.fromString(vex.persistentDataContainer.get(ownerKey, PersistentDataType.STRING))
        val targetId = UUID.fromString(vex.persistentDataContainer.get(targetKey, PersistentDataType.STRING))

        val target = Bukkit.getEntity(targetId) as? LivingEntity

        if (target != null && event.target != target) {
            event.target = target
            return
        }

        if (event.target == target) return

        val owner = Bukkit.getPlayer(ownerId)
        if (event.target == owner) event.target = null
    }

    fun spawnVex(owner: Player, target: LivingEntity) : Vex {
        val vex = target.location.world.spawnEntity(target.location, org.bukkit.entity.EntityType.VEX) as Vex

        vex.persistentDataContainer.set(
            ownerKey,
            PersistentDataType.STRING,
            owner.identity().uuid().toString(),
        )
        vex.persistentDataContainer.set(
            targetKey,
            PersistentDataType.STRING,
            target.uniqueId.toString(),
        )

        vex.attack(target)
        vex.target = target

        vex.setLimitedLifetime(true)
        vex.limitedLifetimeTicks = 30 * 20

        return vex
    }
}