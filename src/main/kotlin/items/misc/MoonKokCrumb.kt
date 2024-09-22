package items.misc

import items.AbstractRLItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import kotlin.random.Random
import kotlin.random.nextInt

object MoonKokCrumb : AbstractRLItem {
    override val model = 44402
    override val baseItem = Material.WHITE_DYE
    override val id = "moon_kok_crumb"

    override fun getItem(result: ItemStack, resultMeta: ItemMeta, resultPDC: PersistentDataContainer): ItemStack {
        resultMeta.setCustomModelData(model)
        resultMeta.displayName(
            Component.text("Лунная крошка", TextColor.color(238, 235, 195))
                .decoration(TextDecoration.ITALIC, false)
        )

        result.setItemMeta(resultMeta)
        return result
    }

    @EventHandler
    private fun onEnderStoneBreak(event: BlockBreakEvent) {
        if (event.block.type != Material.END_STONE) return
        if (!event.isDropItems) return
        if (event.isCancelled) return

        if (Random.nextInt(0..99) >= 5) return

        event.block.location.world.dropItemNaturally(event.block.location.toCenterLocation(), getItem())
    }
}