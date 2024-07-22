package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import kotlin.math.abs
import kotlin.math.sign

object HandDrill : AbstractRLItem {
    override val baseItem: Material = Material.DIAMOND_PICKAXE
    override val model: Int = 44403
    override val id: String = "hand_drill"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        val orange = TextColor.color(180, 100, 0)

        resultMeta.displayName(Component.text("Бур", orange))
        resultMeta.lore(
            listOf(
                Component.text("брбрбрбрбр", orange)
                    .decoration(TextDecoration.ITALIC, true)
            )
        )

        resultMeta.addEnchant(Enchantment.DIG_SPEED, 4, true)
        resultMeta.addEnchant(Enchantment.DURABILITY, 4, true)
        resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        resultMeta.setCustomModelData(model)

        result.setItemMeta(resultMeta)
        return result
    }

    private fun findMaxIndex(list: List<Double>): Int {
        var maxIndex = 0

        for (i in list.indices) {
            if (list[i] > list[maxIndex]) {
                maxIndex = i
            }
        }

        return maxIndex
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val activeItem = event.player.inventory.itemInMainHand
        if (!compare(activeItem)) return

        val positionDelta = event.block.location.subtract(event.player.location)
        val rawCoordinates = listOf(positionDelta.x, positionDelta.y, positionDelta.z)
        val signs = rawCoordinates.map { sign(it) }
        val absCoordinates = rawCoordinates.map { abs(it) }.toMutableList()
        val maxCoordinateIndex = findMaxIndex(absCoordinates)

        val direction = mutableListOf(0.0, 0.0, 0.0)
        direction[maxCoordinateIndex] = signs[maxCoordinateIndex]

        val mainBlockMimingSpeed = event.block.getBreakSpeed(event.player)

        event.player.world.spawnParticle(
            Particle.EXPLOSION_NORMAL,
            event.block.location,
            1,
        )
        event.player.world.playSound(
            event.block.location,
            Sound.ENTITY_GENERIC_EXPLODE,
            1.0f,
            1.0f,
        )

        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    val location = Location(
                        event.block.world,
                        event.block.location.x + dx + direction[0],
                        event.block.location.y + dy + direction[1],
                        event.block.location.z + dz + direction[2],
                    )

                    if (location == event.block.location) continue

                    val block = location.block
                    val blockBreakSpeed = block.getBreakSpeed(event.player)
                    if (blockBreakSpeed < mainBlockMimingSpeed) continue
                    if (blockBreakSpeed == Float.POSITIVE_INFINITY) continue

                    activeItem.damage(1, event.player)
                    location.block.breakNaturally(activeItem)
                }
            }
        }
    }

}